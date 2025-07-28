package com.RESTAPI.ArtGalleryProject.controller.OrdersController;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.RESTAPI.ArtGalleryProject.DTO.DashBoard.PaintingCodRequest;
import com.RESTAPI.ArtGalleryProject.DTO.Order.OrderRequest;
import com.RESTAPI.ArtGalleryProject.Entity.PaintingOrders;
import com.RESTAPI.ArtGalleryProject.security.AuthHelper;
import com.RESTAPI.ArtGalleryProject.service.OrderService.OrderService;
import com.razorpay.RazorpayException;

@Controller
public class OrdersController {
	
	private static final Logger logger = LoggerFactory.getLogger(OrdersController.class);
	
	@Autowired
	private AuthHelper authHelper;
	@Autowired
	private OrderService orderService;

	@GetMapping("/orders")
	public String ordersPage() {
		return "orders";
	}

	@PostMapping(value = "/createOrder", produces = "application/json")
	@ResponseBody
	public ResponseEntity<?> createOrder(@RequestBody OrderRequest request) throws RazorpayException {
		logger.info("createOrder started.");
		long userId = authHelper.getCurrentUserId();
		PaintingOrders razorpayOrder = orderService.createOrder(request, userId);
		logger.info("createOrder finished.");
		return new ResponseEntity<>(razorpayOrder, HttpStatus.CREATED);
	}

	@PostMapping("/paymentCallback")
	public String paymentCallback(@RequestParam Map<String, String> response) {
		logger.info("paymentCallback started.");
		orderService.updateStatus(response);
		logger.info("paymentCallback finished.");
		return "success";

	}
	
	@PostMapping("/paymentCallbackCOD")
	public ResponseEntity<?> paymentCallbackCOD(@RequestBody PaintingCodRequest request) {
		logger.info("paymentCallbackCOD started.");
		long userId = authHelper.getCurrentUserId();
		String email = authHelper.getCurrentEmail();
		String response = orderService.updateStatusCOD(email, userId, request.amount(), request.paintingId());
		logger.info("paymentCallbackCOD finished.");
		logger.info(response);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}