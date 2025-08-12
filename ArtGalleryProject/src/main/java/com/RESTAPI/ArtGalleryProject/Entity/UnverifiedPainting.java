package com.RESTAPI.ArtGalleryProject.Entity;

import com.RESTAPI.ArtGalleryProject.Enum.PaintingStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnverifiedPainting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 2048, nullable = false)
    private String imageUrl;

    private String title;
    private String description;
    private double length;
    private double breadth;
    private double startingPrice;
    private boolean forAuction;
    
    @ManyToOne
	@JoinColumn(name = "seller_id", nullable = false)
    @JsonBackReference(value = "user-unverified-seller")
	private User seller;
    
    @Enumerated(EnumType.STRING)
    private PaintingStatus status = PaintingStatus.PENDING;
}
