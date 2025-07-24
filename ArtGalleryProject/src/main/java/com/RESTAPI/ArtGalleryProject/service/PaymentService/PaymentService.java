package com.RESTAPI.ArtGalleryProject.service.PaymentService;

import com.RESTAPI.ArtGalleryProject.DTO.Payment.InitiatePaymentRequest;

public interface PaymentService {
    String initiatePayment(InitiatePaymentRequest request);
    void updateTransactionStatus(String paymentGatewayTxnId, boolean success); // ADD THIS
    String confirmPayment(String paymentGatewayTxnId);
}
