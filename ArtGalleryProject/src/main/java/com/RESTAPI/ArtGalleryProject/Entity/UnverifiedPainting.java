package com.RESTAPI.ArtGalleryProject.Entity;

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

    private Long sellerId;
    private boolean approved = false;
}
