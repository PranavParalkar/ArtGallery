package com.RESTAPI.ArtGalleryProject.service.UploadPainting;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.RESTAPI.ArtGalleryProject.DTO.UploadPainting.UploadPaintingRequest;
import com.RESTAPI.ArtGalleryProject.Entity.Painting;
import com.RESTAPI.ArtGalleryProject.repository.PaintingRepo;
import com.RESTAPI.ArtGalleryProject.repository.UserRepo;

@Service
public class UploadServiceImpl implements UploadService {

	private static final Logger logger = LoggerFactory.getLogger(UploadServiceImpl.class);

	@Autowired
	private PaintingRepo paintingrepo;
	@Autowired
	private UserRepo userrepo;

	@Override
	public String uploadPainting(String path, UploadPaintingRequest request) throws IOException {
		logger.info("uploadPainting started.");
		MultipartFile file = request.getFile();

		if (file.getSize() > 5 * 1024 * 1024) {
			logger.info("uploadPainting finished.");
			return "File too large. Max size is 5MB.";
		}

		// file name
		String name = file.getOriginalFilename();
		String randomUID = UUID.randomUUID().toString();
		name = randomUID.concat(name.substring(name.lastIndexOf(".")));

		// file path
		String filepath = path + File.separator + name;

		// make a folder if doesn't exist
		File f = new File(path);
		if (!f.exists()) {
			f.mkdir();
		}

		// copy the file
		Files.copy(file.getInputStream(), Paths.get(filepath));

		var painting = new Painting();
		painting.setTitle(request.getTitle());
		painting.setDescription(request.getDescription());
		painting.setLength(request.getLength());
		painting.setBreadth(request.getBreadth());
		painting.setStartingPrice(request.getStartingPrice());
		painting.setSeller(userrepo.findById(request.getUserId()).orElse(null));
		painting.setImageUrl(filepath);
		paintingrepo.save(painting);
		logger.info("uploadPainting finished.");
		return "painting saved successfully";

	}

}
