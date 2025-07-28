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
import com.RESTAPI.ArtGalleryProject.Entity.Orders;
import com.RESTAPI.ArtGalleryProject.Entity.Painting;
import com.RESTAPI.ArtGalleryProject.Entity.PaintingOrders;
import com.RESTAPI.ArtGalleryProject.Entity.User;
import com.RESTAPI.ArtGalleryProject.repository.OrdersRepo;
import com.RESTAPI.ArtGalleryProject.repository.PaintingRepo;
import com.RESTAPI.ArtGalleryProject.repository.UserRepo;
import com.RESTAPI.ArtGalleryProject.service.WalletService.WalletService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

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
	public PaintingOrders createOrder(OrderRequest request, long userId) throws RazorpayException {
	    logger.info("createOrder started for User ID: {}", userId);

	    User user = userRepo.findById(userId)
	            .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

	    Painting painting = paintingRepo.findById(request.paintingId())
	            .orElseThrow(() -> new EntityNotFoundException("Painting not found with id: " + request.paintingId()));

	    if (painting.isSold()) {
	        throw new IllegalStateException("Painting with id: " + request.paintingId() + " is already sold.");
	    }

	    JSONObject options = new JSONObject();
	    options.put("amount", request.amount() * 100);
	    options.put("currency", "INR");
	    options.put("receipt", request.email());
	    Order razorpayOrder = razorpayCLient.orders.create(options);

	    PaintingOrders order = new PaintingOrders();
	    order.setName(request.name());
	    order.setEmail(request.email());
	    order.setAmount(request.amount());
	    order.setUser(user);
	    order.setPainting(painting);
	    order.setAddress(user.getAddress());

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
		logger.info("updateStatus started.");
		String razorpayId = map.get("razorpay_order_id");
		Orders order = ordersRepository.findByRazorpayOrderId(razorpayId);
		order.setOrderStatus("PAYMENT DONE");
		Orders savedOrder = ordersRepository.save(order);

		// Increment wallet balance
		if (order.getEmail() != null) {
			walletService.incrementBalanceByEmail(order.getEmail(), order.getAmount());
		}

		if (order.getEmail() != null) {
			emailService.sendOrderConfirmationEmail(order.getEmail(), "Payment Successful - Art Gallery",
					"Hi " + order.getName() + ",\n\nYour payment has been received successfully for Order ID: "
							+ order.getOrderId() + ".\n\nThanks for shopping with us!");
		}
		return savedOrder;
	}

	@Override
	@Transactional
	public String updateStatusCOD(String email, long userId, double amount, long paintingId) {
	    logger.info("updateStatusCOD started for User ID: {} and Painting ID: {}", userId, paintingId);

	    Painting painting = paintingRepo.findById(paintingId)
	            .orElseThrow(() -> new EntityNotFoundException("Painting not found with id: " + paintingId));
	    User user = userRepo.findById(userId)
	            .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

	    PaintingOrders order = new PaintingOrders();
	    order.setUser(user);
	    order.setName(user.getName());
	    order.setPainting(painting);
	    order.setAmount(amount);
	    order.setEmail(email);
	    order.setAddress(user.getAddress());
	    order.setOrderStatus("PENDING_COD");
	    PaintingOrders savedOrder = ordersRepository.save(order);

	    painting.setSold(true);
	    painting.setBuyer(user);
	    paintingRepo.save(painting);

	    String subject = "🎨 Your Fusion Art Order Confirmation (#" + savedOrder.getOrderId() + ")";
	    String imageAbsolutePath = Paths.get(imageDirectory, painting.getImageUrl()).toString();
	    String formattedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"));

	    String htmlContent = """
	            <!DOCTYPE html>
	            <html lang="en">
	            <head>
	                <meta charset="UTF-8">
	                <meta name="viewport" content="width=device-width, initial-scale=1.0">
	                <style>
	                    body { font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; background-color: #f4f7f6; margin: 0; padding: 15px; }
	                    .email-container { max-width: 680px; margin: 0 auto; background-color: #ffffff; border-radius: 12px; box-shadow: 0 8px 24px rgba(0,0,0,0.1); overflow: hidden; }
	                    .header { background-color: #2c3e50; color: #ffffff; padding: 25px 40px; text-align: center; }
	                    .header h1 { margin: 0; font-size: 28px; font-weight: 600; }
	                    .content { padding: 30px 40px; color: #333; }
	                    .order-info { display: flex; justify-content: space-between; margin-bottom: 30px; padding-bottom: 20px; border-bottom: 1px solid #dee2e6; }
	                    .order-info div { line-height: 1.5; }
	                    .order-info strong { color: #555; }
	                    .item-table { width: 100%; border-collapse: collapse; margin-bottom: 30px; }
	                    .item-table th, .item-table td { padding: 15px; text-align: left; border-bottom: 1px solid #dee2e6; }
	                    .item-table th { background-color: #f8f9fa; font-weight: 600; color: #555; }
	                    .item-table .item-image { width: 80px; height: 80px; object-fit: cover; border-radius: 8px; margin-right: 15px; vertical-align: middle;}
	                    .item-table .item-title { font-weight: bold; }
	                    .shipping-info { padding: 20px; margin-top: 20px; background-color: #f8f9fa; border-radius: 8px; }
	                    .footer { text-align: center; padding: 25px 40px; font-size: 13px; color: #888; background-color: #f4f7f6;}
	                    .footer a { color: #2c3e50; text-decoration: none; }
	                </style>
	            </head>
	            <body>
	                <div class="email-container">
	                    <div class="header"><h1>Thank You For Your Order!</h1></div>
	                    <div class="content">
	                        <div class="order-info">
	                            <div><strong>Order #:</strong> %d<br><strong>Date:</strong> %s</div>
	                            <div><strong>Billed To:</strong><br>%s</div>
	                        </div>
	                        <table class="item-table">
	                            <thead>
	                                <tr>
	                                    <th colspan="2">Item</th>
	                                    <th>Price</th>
	                                </tr>
	                            </thead>
	                            <tbody>
	                                <tr>
	                                    <td><img src="cid:paintingImage" alt="%s" class="item-image"/></td>
	                                    <td class="item-title">%s</td>
	                                    <td>₹%,.2f</td>
	                                </tr>
	                            </tbody>
	                        </table>
	                        <div class="shipping-info">
	                            <strong>Payment Method:</strong> %s<br>
	                            <strong>Shipping Address:</strong> %s
	                        </div>
	                        <p style="margin-top:30px;">We've received your order and will begin processing it right away. If you have any questions, please don't hesitate to contact our support team.</p>
	                    </div>
	                    <div class="footer">
	                        <p>&copy; %d Fusion Art. All Rights Reserved.<br>
	                        <a href="#">Visit Our Gallery</a> | <a href="#">Contact Us</a></p>
	                    </div>
	                </div>
	            </body>
	            </html>
	            """.formatted(
	                savedOrder.getOrderId(),
	                formattedDate,
	                user.getName(),
	                painting.getTitle(),
	                painting.getTitle(),
	                amount,
	                "Cash on Delivery",
	                user.getAddress().toString(),
	                Year.now().getValue()
	            );

	    try {
	        emailService.sendOrderConfirmationEmailCOD(email, subject, htmlContent, imageAbsolutePath);
	        logger.info("updateStatusCOD finished successfully for Order ID: {}", savedOrder.getOrderId());
	        return "Order confirmation email sent successfully.";
	    } catch (MessagingException e) {
	        logger.error("Failed to send confirmation email for Order ID: {}", savedOrder.getOrderId(), e);
	        return "Order placed, but failed to send confirmation email.";
	    }
	}

}