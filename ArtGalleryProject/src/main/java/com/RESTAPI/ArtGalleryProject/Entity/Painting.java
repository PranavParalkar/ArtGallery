package com.RESTAPI.ArtGalleryProject.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Painting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paintingId;
    private String name;
    private String description;
    private String trademarkNumber;
    private LocalDateTime biddingEndTime;
    private double currentHighestBid;
    private boolean isSold = false;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

    public Painting() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Painting(String name, String description, double startingBid, String trademarkNumber, LocalDateTime biddingEndTime, Seller seller) {
        this.name = name;
        this.description = description;
        this.trademarkNumber = trademarkNumber;
        this.seller = seller;
        this.currentHighestBid = startingBid;
        this.isSold = false;
    }

	public Long getPaintingId() {
		return paintingId;
	}

	public void setPaintingId(Long paintingId) {
		this.paintingId = paintingId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTrademarkNumber() {
		return trademarkNumber;
	}

	public void setTrademarkNumber(String trademarkNumber) {
		this.trademarkNumber = trademarkNumber;
	}

	public LocalDateTime getBiddingEndTime() {
		return biddingEndTime;
	}

	public void setBiddingEndTime(LocalDateTime biddingEndTime) {
		this.biddingEndTime = biddingEndTime;
	}

	public double getCurrentHighestBid() {
		return currentHighestBid;
	}

	public void setCurrentHighestBid(double currentHighestBid) {
		this.currentHighestBid = currentHighestBid;
	}

	public boolean isSold() {
		return isSold;
	}

	public void setSold(boolean isSold) {
		this.isSold = isSold;
	}

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	@Override
	public String toString() {
		return "Painting [paintingId=" + paintingId + ", name=" + name + ", description=" + description
				+ ", trademarkNumber=" + trademarkNumber + ", biddingEndTime=" + biddingEndTime + ", currentHighestBid="
				+ currentHighestBid + ", isSold=" + isSold + ", seller=" + seller + "]";
	}
	
}