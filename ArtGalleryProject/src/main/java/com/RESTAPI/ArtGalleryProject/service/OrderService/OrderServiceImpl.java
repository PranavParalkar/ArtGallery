package com.RESTAPI.ArtGalleryProject.service.OrderService;

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
import com.RESTAPI.ArtGalleryProject.Entity.User;
import com.RESTAPI.ArtGalleryProject.controller.PaintingController.UploadPaintingController;
import com.RESTAPI.ArtGalleryProject.repository.*;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import jakarta.annotation.PostConstruct;
import com.RESTAPI.ArtGalleryProject.service.WalletService.WalletService;

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

	private RazorpayClient razorpayCLient;

	@PostConstruct
	public void init() throws RazorpayException {
		logger.info("upload started.");
		this.razorpayCLient = new RazorpayClient(razorpayId, razorpaySecret);
	}

	@Override
	public Orders createOrder(OrderRequest request) throws RazorpayException {
		logger.info("createOrder started.");
		JSONObject options = new JSONObject();
		options.put("amount", request.amount() * 100); // amount in paise
		options.put("currency", "INR");
		options.put("receipt", request.email());
		Order razorpayOrder = razorpayCLient.orders.create(options);
		Orders order = new Orders();
		order.setAmount(request.amount());
		order.setEmail(request.email());
		order.setName(request.name());
		if (razorpayOrder != null) {
			order.setRazorpayOrderId(razorpayOrder.get("id"));
			order.setOrderStatus(razorpayOrder.get("status"));
		}
		logger.info("createOrder finished.");
		return ordersRepository.save(order);
	}

	@Override
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
	public String updateStatusCOD(String email, long userId, double amount, long paintingId) {
		logger.info("updateStatusCOD started.");
		Painting painting = paintingRepo.findById(paintingId).orElse(null);
	    User user = userRepo.findById(userId).orElse(null);

	    if (painting != null && user != null) {
	        // Prepare email details
	        String to = email;
	        String subject = "Order Confirmation - Cash on Delivery";
	        String body = "Dear " + user.getName() + ",\n\n"
	            + "Thank you for your order from the Art Gallery.\n\n"
	            + "Here are your order details:\n"
	            + "-------------------------------------\n"
	            + "Painting Title   : " + painting.getTitle() + "\n"
	            + "Description      : " + painting.getDescription() + "\n"
	            + "Price            : ₹" + amount + "\n"
	            + "Order Type       : Cash on Delivery\n"
	            + "Delivery Address : " + user.getAddress().toString() + "\n"
	            + "-------------------------------------\n\n"
	            + "Your order will be processed shortly and shipped to your provided address.\n"
	            + "You can track the status of your order in your profile.\n\n"
	            + "If you have any questions, feel free to contact our support team.\n\n"
	            + "Warm regards,\n"
	            + "Art Gallery Team";

	        // Send email
	        emailService.sendOrderConfirmationEmail(to, subject, body);
//	        painting.setSold(true);
//	        painting.setBuyer(user);
//	        paintingRepo.save(painting);
	        logger.info("updateStatusCOD finished.");
	        return "Order confirmation email sent successfully.";
	    }
	    logger.info("updateStatusCOD finished.");
	    return "Failed to send email. User or painting not found.";
	}
}