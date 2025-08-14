package com.RESTAPI.ArtGalleryProject.DTO.Order;

import java.time.LocalDateTime;

import com.RESTAPI.ArtGalleryProject.Enum.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse{
	private String userName;
	private TransactionType type;
	private double amount;
	private String PaintingImageUrl;
	private String PaintingSellerName;
	private LocalDateTime timeStamp;
}