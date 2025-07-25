package com.RESTAPI.ArtGalleryProject.service.OrderService;

import java.util.Map;

import com.RESTAPI.ArtGalleryProject.DTO.Order.OrderRequest;
import com.RESTAPI.ArtGalleryProject.Entity.Orders;
import com.razorpay.RazorpayException;

public interface OrderService {
	public Orders updateStatus(Map<String, String> map);
	public Orders createOrder(OrderRequest request) throws RazorpayException;
	public String updateStatusCOD(String email, long userId, double amount, long paintingId);
}
