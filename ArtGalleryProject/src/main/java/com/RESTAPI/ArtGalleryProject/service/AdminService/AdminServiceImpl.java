package com.RESTAPI.ArtGalleryProject.service.AdminService;

import com.RESTAPI.ArtGalleryProject.Entity.Painting;
import com.RESTAPI.ArtGalleryProject.Entity.UnverifiedPainting;
import com.RESTAPI.ArtGalleryProject.Enum.PaintingStatus;
import com.RESTAPI.ArtGalleryProject.repository.PaintingRepo;
import com.RESTAPI.ArtGalleryProject.repository.UnverifiedPaintingRepo;
import com.RESTAPI.ArtGalleryProject.repository.UserRepo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Autowired
    private UnverifiedPaintingRepo unverifiedRepo;

    @Autowired
    private PaintingRepo paintingRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public List<UnverifiedPainting> getPendingPaintings() {
        logger.info("getPendingPaintings started.");
        List<UnverifiedPainting> pendingList = unverifiedRepo.findByStatus(PaintingStatus.PENDING);
        logger.info("getPendingPaintings finished. {} paintings found.", pendingList.size());
        return pendingList;
    }

    @Override
    public String approvePainting(Long id) {
        logger.info("approvePainting started for painting ID: {}", id);

        var optional = unverifiedRepo.findById(id);
        if (optional.isEmpty()) {
            logger.warn("Painting with ID {} not found in unverified repository.", id);
            return "Painting not found";
        }

        var unverified = optional.get();
        var seller = userRepo.findById(unverified.getSellerId()).orElse(null);
        if (seller == null) {
            logger.warn("Seller with ID {} not found for painting ID {}.", unverified.getSellerId(), id);
            return "Seller not found";
        }

        var painting = new Painting();
        painting.setTitle(unverified.getTitle());
        painting.setDescription(unverified.getDescription());
        painting.setLength(unverified.getLength());
        painting.setBreadth(unverified.getBreadth());
        painting.setStartingPrice(unverified.getStartingPrice());
        painting.setImageUrl(unverified.getImageUrl());
        painting.setForAuction(unverified.isForAuction());
        painting.setSeller(seller);
        painting.setSold(false);

        paintingRepo.save(painting);
        unverifiedRepo.deleteById(id);

        logger.info("Painting with ID {} approved and moved to verified painting table.", id);
        String response = painting.isForAuction() ? 
        		"Painting approved and moved to auction.": 
        		"Painting approved and moved to Shop.";
        return response;
    }

    @Override
    public String rejectPainting(Long id) {
        logger.info("rejectPainting started for painting ID: {}", id);

        if (!unverifiedRepo.existsById(id)) {
            logger.warn("Painting with ID {} not found during rejection.", id);
            return "Painting not found";
        }

        unverifiedRepo.deleteById(id);
        logger.info("Painting with ID {} successfully rejected and deleted.", id);
        return "Painting rejected and deleted.";
    }
}
