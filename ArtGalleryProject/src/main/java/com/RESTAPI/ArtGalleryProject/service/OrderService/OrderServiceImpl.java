package com.RESTAPI.ArtGalleryProject.service.OrderService;

import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.RESTAPI.ArtGalleryProject.DTO.Order.OrderRequest;
import com.RESTAPI.ArtGalleryProject.DTO.Order.WalletPaymentRequest;
import com.RESTAPI.ArtGalleryProject.Entity.Orders;
import com.RESTAPI.ArtGalleryProject.Entity.Painting;
import com.RESTAPI.ArtGalleryProject.Entity.User;
import com.RESTAPI.ArtGalleryProject.repository.LoginCredRepo;
import com.RESTAPI.ArtGalleryProject.repository.OrdersRepo;
import com.RESTAPI.ArtGalleryProject.repository.PaintingRepo;
import com.RESTAPI.ArtGalleryProject.repository.UserRepo;
import com.RESTAPI.ArtGalleryProject.service.WalletService.WalletService;
import com.lowagie.text.DocumentException;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import io.jsonwebtoken.io.IOException;
import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

	private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Autowired
	private OrdersRepo ordersRepository;
	@Autowired
	private EmailService emailService;
	@Autowired
	private PaintingRepo paintingRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private LoginCredRepo loginCredRepo;
	@Autowired
	private PdfService pdfService;
	@Autowired
	private WalletService walletService;

	@Value("${razorpay.key.id}")
	private String razorpayId;
	@Value("${razorpay.key.secret}")
	private String razorpaySecret;
	private String imageDirectory = "C:/Users/varad/OneDrive/Desktop/projects/Super30SpringProject/ArtGalleryProject";

	private RazorpayClient razorpayCLient;

	@PostConstruct
	public void init() throws RazorpayException {
		logger.info("upload started.");
		this.razorpayCLient = new RazorpayClient(razorpayId, razorpaySecret);
	}

	@Override
	@Transactional
	public Orders createOrder(OrderRequest request) throws RazorpayException {
		logger.info("createOrder started for User email: {}", request.email());

		JSONObject options = new JSONObject();
		options.put("amount", request.amount() * 100);
		options.put("currency", "INR");
		options.put("receipt", request.email());
		Order razorpayOrder = razorpayCLient.orders.create(options);

		Orders order = new Orders();
		order.setName(request.name());
		order.setEmail(request.email());
		order.setAmount(request.amount());
		if (razorpayOrder != null) {
			order.setRazorpayOrderId(razorpayOrder.get("id"));
			order.setOrderStatus(razorpayOrder.get("status"));
		}

		logger.info("createOrder finished for Razorpay Order ID: {}", order.getRazorpayOrderId());
		return ordersRepository.save(order);
	}

	@Override
	@Transactional
	public Orders updateStatus(Map<String, String> map) {
		String razorpayId = map.get("razorpay_order_id");
		
		if (razorpayId == null) {
			logger.error("razorpay_order_id is null in payment callback");
			throw new RuntimeException("Invalid payment callback - missing order ID");
		}
		
		Orders order = ordersRepository.findByRazorpayOrderId(razorpayId);
		if (order == null) {
			logger.error("Order not found for razorpay ID: {}", razorpayId);
			throw new RuntimeException("Order not found for payment callback");
		}
		
		order.setOrderStatus("PAYMENT DONE");
		Orders savedOrder = ordersRepository.save(order);

		// Increment wallet balance
		if (order.getEmail() != null) {
			try {
				walletService.incrementBalanceByEmail(order.getEmail(), order.getAmount());
				logger.info("Wallet balance incremented for email: {}", order.getEmail());
			} catch (Exception e) {
				logger.error("Error incrementing wallet balance: {}", e.getMessage());
			}
		}

		// Send email confirmation
		if (order.getEmail() != null) {
			try {
				emailService.sendOrderConfirmationEmail(order.getEmail(), "Payment Successful - Art Gallery",
						"Hi " + order.getName() + ",\n\nYour payment has been received successfully for Order ID: "
								+ order.getOrderId() + ".\n\nThanks for shopping with us!");
				logger.info("Order confirmation email sent to: {}", order.getEmail());
			} catch (Exception e) {
				logger.error("Error sending email confirmation: {}", e.getMessage());
			}
		}
		
		logger.info("updateStatus finished successfully for order ID: {}", savedOrder.getOrderId());
		return savedOrder;
	}

	@Override
	@Transactional
	public String updateStatusCOD(String email, long userId, double amount, long paintingId,
			String mobileNumber, String address, String paymentMethod, String name) throws java.io.IOException {
		logger.info("updateStatusCOD started for User ID: {} and Painting ID: {}", userId, paintingId);

		Painting painting = paintingRepo.findById(paintingId)
				.orElseThrow(() -> new EntityNotFoundException("Painting not found with id: " + paintingId));
		
		// Check if painting is already sold
		if (painting.isSold()) {
			logger.warn("Painting {} is already sold", paintingId);
			return "Painting is already sold";
		}
		
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

		Orders order = new Orders();
		order.setName(user.getName());
		order.setAmount(amount);
		order.setEmail(email);
		order.setOrderStatus("PENDING_COD");
		Orders savedOrder = ordersRepository.save(order);

		// Mark painting as sold and set buyer
//		painting.setSold(true);
//		painting.setBuyer(user);
//		paintingRepo.save(painting);

		String subject = "🎨 Your Fusion Art Order Confirmation (#" + savedOrder.getOrderId() + ")";
		String imageAbsolutePath = Paths.get(imageDirectory, painting.getImageUrl()).toString();
		String formattedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"));

		String htmlContent = """
				    <!DOCTYPE html>
				    <html lang="en">
				    <head>
				        <meta charset="UTF-8" />
				        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
				        <style>
				            body {
				                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
				                background-color: #f1f5f9;
				                margin: 0;
				                padding: 0;
				                color: #333;
				            }

				            .email-container {
				                max-width: 720px;
				                margin: 40px auto;
				                background-color: #ffffff;
				                border-radius: 16px;
				                box-shadow: 0 8px 20px rgba(0, 0, 0, 0.08);
				                overflow: hidden;
				            }

				            .header {
				                background: linear-gradient(90deg, #2c3e50, #34495e);
				                color: #fff;
				                padding: 30px 40px;
				                text-align: center;
				            }

				            .header h1 {
				                margin: 0;
				                font-size: 30px;
				                font-weight: bold;
				            }

				            .content {
				                padding: 40px 50px;
				                line-height: 1.7;
				                font-size: 16px;
				            }

				            .order-info {
				                display: flex;
				                justify-content: space-between;
				                margin-bottom: 35px;
				                padding-bottom: 25px;
				                border-bottom: 1px solid #e0e0e0;
				            }

				            .order-info div {
				                width: 48%%;
				            }

				            .order-info strong {
				                display: block;
				                color: #555;
				                font-weight: 600;
				                margin-bottom: 5px;
				            }

				            .item-table {
								width: 100%%;
								border-collapse: collapse;
								margin-bottom: 35px;
							}

							.item-table th,
							.item-table td {
								padding: 16px;
								border-bottom: 1px solid #e6e6e6;
								text-align: center;
								vertical-align: middle;
							}

							.item-table th {
								background-color: #f0f4f8;
								color: #444;
								font-size: 15px;
								font-weight: 600;
							}

							.item-image {
								width: 200px;
								border-radius: 10px;
								box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
							}

							.item-title {
								font-weight: 600;
								font-size: 16px;
								color: #333;
							}

							.item-price {
								font-size: 15px;
								font-weight: 500;
								color: #222;
							}


				            .shipping-info {
				                background-color: #f9fafb;
				                padding: 25px;
				                border-radius: 10px;
				                margin-top: 30px;
				                font-size: 15px;
				            }

				            .footer {
				                text-align: center;
				                padding: 30px 40px;
				                background-color: #f1f5f9;
				                color: #888;
				                font-size: 13px;
				                border-top: 1px solid #ddd;
				            }

				            .footer a {
				                color: #2c3e50;
				                text-decoration: none;
				                margin: 0 10px;
				            }

				            .footer a:hover {
				                text-decoration: underline;
				            }
				        </style>
				    </head>
				    <body>
				        <div class="email-container">
				            <div class="header">
				                <h1>Thank You for Your Purchase!</h1>
				            </div>
				            <div class="content">
				                <div class="order-info">
				                    <div>
				                        <strong>Order ID:</strong> #%d
				                        <strong>Date:</strong> %s
				                    </div>
				                    <div>
				                        <strong>Billed To:</strong> %s
				                    	<strong>Contact Number:</strong> %s
				                    </div>
				                </div>

				                <table class="item-table">
									<thead>
										<tr>
											<th>Item</th>
											<th>Title</th>
											<th>Price</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td><img src="cid:paintingImage" alt="%s" class="item-image" /></td>
											<td class="item-title">%s</td>
											<td class="item-price">₹%.2f</td>
										</tr>
									</tbody>
								</table>

				                <div class="shipping-info">
				                    <p><strong>Payment Method:</strong> %s</p>
				                    <p><strong>Shipping Address:</strong><br>%s</p>
				                </div>

				                <p style="margin-top: 30px;">
				                    We have received your order and will begin processing it shortly. If you have any questions, feel free to contact our support team.
				                </p>
				            </div>

				            <div class="footer">
				                <p>&copy; %d Fusion Art. All Rights Reserved.<br/>
				                <a href="#">Visit Our Gallery</a> | <a href="#">Contact Us</a></p>
				            </div>
				        </div>
				    </body>
				    </html>
				"""
				.formatted(
					savedOrder.getOrderId(),
					formattedDate,
					name,
					mobileNumber,
					painting.getTitle(),
					painting.getTitle(),
					amount,
					paymentMethod,
					address,
					Year.now().getValue()
				);

		try {
		    logger.info("Generating PDF receipt for Order ID: {}", savedOrder.getOrderId());

		    byte[] pdfReceipt = pdfService.generateReceiptPdf(savedOrder, user, painting, imageDirectory, paymentMethod, name, mobileNumber, address);
		    String pdfFilename = "FusionArt-Receipt-" + savedOrder.getOrderId() + ".pdf";

		    logger.info("Sending confirmation email with PDF attachment to: {}", email);

		    emailService.sendOrderConfirmationEmailCOD(
		        email,
		        subject,
		        htmlContent,
		        imageAbsolutePath,
		        pdfReceipt,
		        pdfFilename
		    );

		    logger.info("Order confirmation email sent successfully for Order ID: {}", savedOrder.getOrderId());
		    return "Order confirmation email with PDF receipt sent successfully.";

		} catch (DocumentException | IOException e) {
		    logger.error("PDF generation failed for Order ID: {}", savedOrder.getOrderId(), e);

		    // Optionally still try to send the email without attachment
		    try {
		        emailService.sendOrderConfirmationEmailCOD(
		            email,
		            subject,
		            htmlContent,
		            imageAbsolutePath,
		            null,
		            null
		        );
		        logger.warn("PDF was not attached, but email sent without PDF for Order ID: {}", savedOrder.getOrderId());
		        return "Order placed. PDF receipt failed, but confirmation email sent without attachment.";
		    } catch (MessagingException ex) {
		        logger.error("Failed to send confirmation email without PDF for Order ID: {}", savedOrder.getOrderId(), ex);
		        return "Order placed, but failed to generate PDF and send confirmation email.";
		    }

		} catch (MessagingException e) {
		    logger.error("Email sending failed for Order ID: {}", savedOrder.getOrderId(), e);
		    return "Order placed, but failed to send confirmation email with PDF receipt.";
		}

	}

	@Override
	@Transactional
	public String processWalletPayment(WalletPaymentRequest request, String email) {
		logger.info("processWalletPayment started for Painting ID: {} and User Email: {}", request.paintingId(), email);

		try {
			// Find the painting
			Painting painting = paintingRepo.findById(request.paintingId()).orElseThrow(
					() -> new EntityNotFoundException("Painting not found with id: " + request.paintingId()));

			// Check if painting is already sold
			if (painting.isSold()) {
				logger.warn("Painting {} is already sold", request.paintingId());
				return "Painting is already sold";
			}

			// Find the user by email through LoginCredentials
			User user = loginCredRepo.findById(email)
					.orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email)).getUser();

			// Check wallet balance
			double paintingPrice = painting.getStartingPrice();
			double currentBalance = user.getWallet().getBalance();

			if (currentBalance < paintingPrice) {
				logger.warn("Insufficient wallet balance for user {}. Required: {}, Available: {}", email,
						paintingPrice, currentBalance);
				return "Insufficient wallet balance";
			}

			// Decrement wallet balance
			walletService.decrementBalanceByEmail(email, paintingPrice);

			// Mark painting as sold and set buyer
//			painting.setSold(true);
//			painting.setBuyer(user);
//			paintingRepo.save(painting);

			// Create order record
			Orders order = new Orders();
			order.setName(user.getName());
			order.setEmail(email);
			order.setAmount(paintingPrice);
			order.setOrderStatus("PAID");
			ordersRepository.save(order);

			// Send confirmation email
			try {
				emailService.sendOrderConfirmationEmail(email, "Payment Successful - Art Gallery", "Hi "
						+ user.getName() + ",\n\nYour wallet payment has been processed successfully for painting: "
						+ painting.getTitle() + ".\n\nAmount: ₹" + paintingPrice + "\n\nThanks for shopping with us!");
				logger.info("Order confirmation email sent to: {}", email);
			} catch (Exception e) {
				logger.error("Error sending email confirmation: {}", e.getMessage());
			}

			logger.info("processWalletPayment finished successfully for Painting ID: {}", request.paintingId());
			return "Payment successful";

		} catch (Exception e) {
			logger.error("Error in processWalletPayment: {}", e.getMessage(), e);
			return "Payment failed: " + e.getMessage();
		}
	}

}