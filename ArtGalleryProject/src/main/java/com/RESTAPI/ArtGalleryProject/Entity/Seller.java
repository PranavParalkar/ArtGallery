package com.RESTAPI.ArtGalleryProject.Entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seller_id") // ensures Hibernate uses correct column name for FK references
    private Long sellerId;

    private String name;
    private String email;
    private String phoneNumber;

	private String sellerUsername;
	private String sellerPassword;
	
	private double walletBalance = 0.0;

	@OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Painting> paintings;

	public Seller() {
	}

	public Seller(String name, String email, String phoneNumber, String sellerUsername, String password) {
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.sellerUsername = sellerUsername;
		this.sellerPassword = password;
		this.walletBalance = 0.0;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getSellerUsername() {
		return sellerUsername;
	}

	public void setSellerUsername(String sellerUsername) {
		this.sellerUsername = sellerUsername;
	}

	public String getSellerPassword() {
		return sellerPassword;
	}

	public void setSellerPassword(String password) {
		this.sellerPassword = password;
	}

	public double getWalletBalance() {
		return walletBalance;
	}

	public void setWalletBalance(double walletBalance) {
		this.walletBalance = walletBalance;
	}

	public List<Painting> getPaintings() {
		return paintings;
	}

	public void setPaintings(List<Painting> paintings) {
		this.paintings = paintings;
	}

	@Override
	public String toString() {
		return "Seller [sellerId=" + sellerId + ", name=" + name + ", email=" + email + ", phoneNumber=" + phoneNumber
				+ ", sellerUsername=" + sellerUsername + ", sellerPassword=" + sellerPassword + ", walletBalance="
				+ walletBalance + ", paintings=" + paintings + "]";
	}

}