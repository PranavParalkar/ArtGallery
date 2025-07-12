package com.RESTAPI.ArtGalleryProject.Entity;

import jakarta.persistence.*;

@Entity
public class PaintingsBought {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long buyerId;
	private Long paintingId;

	public PaintingsBought() {
	}

	public PaintingsBought(Long buyerId, Long paintingId) {
		this.buyerId = buyerId;
		this.paintingId = paintingId;
	}

	public Long getId() {
		return id;
	}

	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	public Long getPaintingId() {
		return paintingId;
	}

	public void setPaintingId(Long paintingId) {
		this.paintingId = paintingId;
	}

	@Override
	public String toString() {
		return "PaintingsBought [id=" + id + ", buyerId=" + buyerId + ", paintingId=" + paintingId + "]";
	}
}
