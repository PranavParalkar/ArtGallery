package com.RESTAPI.ArtGalleryProject.service.OrderService;

import com.RESTAPI.ArtGalleryProject.Entity.Orders;
import com.RESTAPI.ArtGalleryProject.Entity.Painting;
import com.RESTAPI.ArtGalleryProject.Entity.User;
import com.lowagie.text.DocumentException;

import io.jsonwebtoken.io.IOException;

public interface PdfService {
	public byte[] generateReceiptPdf(Orders order, User user, Painting painting, String imageDirectory) throws DocumentException, IOException, java.io.IOException;
}
