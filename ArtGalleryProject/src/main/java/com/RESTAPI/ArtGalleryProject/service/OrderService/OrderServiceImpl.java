package com.RESTAPI.ArtGalleryProject.service.OrderService;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.RESTAPI.ArtGalleryProject.DTO.Order.OrderRequest;
import com.RESTAPI.ArtGalleryProject.Entity.Orders;
import com.RESTAPI.ArtGalleryProject.repository.*;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import jakarta.annotation.PostConstruct;
import com.RESTAPI.ArtGalleryProject.service.WalletService.WalletService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrdersRepo ordersRepository;
	@Autowired
	private EmailService emailService;
	@Autowired
	private WalletService walletService;

	@Value("${razorpay.key.id}")
	private String razorpayId;
	@Value("${razorpay.key.secret}")
	private String razorpaySecret;

	private RazorpayClient razorpayCLient;

	@PostConstruct
	public void init() throws RazorpayException {
		this.razorpayCLient = new RazorpayClient(razorpayId, razorpaySecret);
	}

	@Override
	public Orders createOrder(OrderRequest request) throws RazorpayException {
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
		emailService.sendOrderConfirmationEmail(order.getEmail(), "Your Art Gallery Order is Placed",
				"Hi " + order.getName()
						+ ",\n\nYour order has been successfully placed. We'll deliver it soon!\n\nOrder ID: "
						+ order.getOrderId() + "\nAmount: ₹" + order.getAmount() + "\n\nThank you!");

		return ordersRepository.save(order);
	}

	@Override
	public Orders updateStatus(Map<String, String> map) {
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
}