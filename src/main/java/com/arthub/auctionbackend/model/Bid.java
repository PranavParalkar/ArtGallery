package com.arthub.auctionbackend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "bids")
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "artwork_id")
    private Artwork artwork;

    @ManyToOne
    @JoinColumn(name = "bidder_id")
    private User bidder;
}
