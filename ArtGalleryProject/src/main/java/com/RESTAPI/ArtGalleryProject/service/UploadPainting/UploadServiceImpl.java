package com.RESTAPI.ArtGalleryProject.service.UploadPainting;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.RESTAPI.ArtGalleryProject.DTO.UploadPainting.UploadPaintingRequest;
import com.RESTAPI.ArtGalleryProject.Entity.Painting;
import com.RESTAPI.ArtGalleryProject.repository.PaintingRepo;

@Service
public class UploadServiceImpl implements UploadService{
	
	@Autowired
	private PaintingRepo paintingrepo;

	@Override
	public String uploadPainting(String path, UploadPaintingRequest request) throws IOException {
		MultipartFile file = request.file();
		
		if(file.getSize() > 2 * 1024 * 1024) {
			return "File too large. Max size is 5MB.";
		}
		
		//file name
		String name = file.getOriginalFilename();
		String randomUID = UUID.randomUUID().toString();
		name = randomUID.concat(name.substring(name.lastIndexOf(".")));
		
		//file path
		String filepath = path + File.separator + name;
		
		//make a folder if doesn't exist
		File f = new File(path);
		if(!f.exists()) {
			f.mkdir();
		}
		
		//copy the file 
		Files.copy(file.getInputStream(), Paths.get(filepath));
		
		var painting = new Painting();
		
		
		
		return name;
	}
	
}
