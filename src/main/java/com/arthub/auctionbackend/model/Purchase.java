package com.arthub.auctionbackend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "purchases")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "artwork_id")
    private Artwork artwork;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private User buyer;

    private Double finalPrice;

    private LocalDateTime purchaseDate;
}
