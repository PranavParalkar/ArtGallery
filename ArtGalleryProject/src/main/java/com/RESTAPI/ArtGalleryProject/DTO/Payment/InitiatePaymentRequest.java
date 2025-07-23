package com.RESTAPI.ArtGalleryProject.DTO.Payment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InitiatePaymentRequest {
    private Long userId;
    private double amount;
}
