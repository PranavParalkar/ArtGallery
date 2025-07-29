package com.RESTAPI.ArtGalleryProject.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawalRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String userEmail;
    private Double amount;
    private String bankAccount;
    private String ifscCode;
    private String accountHolderName;
    private String status = "PENDING"; // PENDING, APPROVED, REJECTED
    private LocalDateTime requestDate = LocalDateTime.now();
    private LocalDateTime processedDate;
    private String adminNotes;
} 