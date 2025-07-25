package com.RESTAPI.ArtGalleryProject.service.PaymentService;

import com.RESTAPI.ArtGalleryProject.DTO.Payment.InitiatePaymentRequest;

public interface PaymentService {
    public String initiatePayment(InitiatePaymentRequest request, long userId);
    public void updateTransactionStatus(String paymentGatewayTxnId, boolean success); // ADD THIS
}
