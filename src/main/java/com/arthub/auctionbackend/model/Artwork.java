package com.arthub.auctionbackend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "artworks")
public class Artwork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private Double basePrice;

    @Column(unique = true)
    private String trademarkId;

    private String imageUrl; // Optional: store painting image path

    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;

    public enum Status {
        ACTIVE,
        SOLD,
        INACTIVE
    }
}
