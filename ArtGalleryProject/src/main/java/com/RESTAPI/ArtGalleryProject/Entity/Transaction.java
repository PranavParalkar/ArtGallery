package com.RESTAPI.ArtGalleryProject.Entity;

import com.RESTAPI.ArtGalleryProject.Enum.TransactionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder       // <--- Required for `.builder()` in your code
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private double amount;

    private String paymentGatewayTxnId;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    private LocalDateTime timestamp;

}
