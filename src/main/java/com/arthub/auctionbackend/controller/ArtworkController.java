package com.arthub.auctionbackend.controller;

import com.arthub.auctionbackend.model.Artwork;
import com.arthub.auctionbackend.model.User;
import com.arthub.auctionbackend.repository.ArtworkRepository;
import com.arthub.auctionbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/artworks")
@CrossOrigin(origins = "*")
public class ArtworkController {

    @Autowired
    private ArtworkRepository artworkRepository;

    @Autowired
    private UserRepository userRepository;

    // Upload a new painting
    @PostMapping("/upload/{sellerId}")
    public String uploadArtwork(@PathVariable Long sellerId, @RequestBody Artwork artwork) {
        Optional<User> seller = userRepository.findById(sellerId);
        if (seller.isEmpty()) {
            return "Seller not found!";
        }
        artwork.setSeller(seller.get());
        artworkRepository.save(artwork);
        return "Artwork uploaded successfully!";
    }

    // View all artworks by seller
    @GetMapping("/seller/{sellerId}")
    public List<Artwork> getArtworksBySeller(@PathVariable Long sellerId) {
        Optional<User> seller = userRepository.findById(sellerId);
        return seller.map(artworkRepository::findBySeller).orElse(null);
    }

    // Get single artwork by ID
    @GetMapping("/{artworkId}")
    public Artwork getArtworkById(@PathVariable Long artworkId) {
        return artworkRepository.findById(artworkId).orElse(null);
    }
}
