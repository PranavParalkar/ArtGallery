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

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrdersRepo ordersRepository;
	@Autowired
	private EmailService emailService;

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
	    double amountInPaise = request.amount() * 100;
	    options.put("amount", amountInPaise);
	    options.put("currency", "INR");
	    options.put("receipt", request.email());

	    Order razorpayOrder = razorpayCLient.orders.create(options);

	    Orders order = new Orders();
	    order.setAmount(amountInPaise); // ✅ now in paise
	    order.setEmail(request.email());
	    order.setName(request.name());

	    if (razorpayOrder != null) {
	        order.setRazorpayOrderId(razorpayOrder.get("id"));
	        order.setOrderStatus(razorpayOrder.get("status"));
	    }
	    return ordersRepository.save(order);
	}


	@Override
	public Orders updateStatus(Map<String, String> map) {
		String razorpayId = map.get("razorpay_order_id");
		Orders order = ordersRepository.findByRazorpayOrderId(razorpayId);
		order.setOrderStatus("PAYMENT DONE");
		Orders orders = ordersRepository.save(order);
		if (order.getEmail() != null) {
			emailService.sendOrderConfirmationEmail(order.getEmail(), "Payment Successful - Art Gallery",
					"Hi " + order.getName() + ",\n\nYour payment has been received successfully for Order ID: "
							+ order.getOrderId() + ".\n\nThanks for shopping with us!");
		}
		return orders;
	}
}