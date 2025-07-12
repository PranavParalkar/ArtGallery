package com.RESTAPI.ArtGalleryProject.Entity;

import jakarta.persistence.*;

@Entity
public class Buyer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long buyerId;

	private String buyerUsername;
	private String buyerPassword;

	private String name;
	private String email;
	private String phoneNumber;

	private double walletBalance = 0.0;

	public Buyer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Buyer(Long buyerId, String buyerUsername, String buyerPassword, String name, String email,
			String phoneNumber, double walletBalance) {
		super();
		this.buyerId = buyerId;
		this.buyerUsername = buyerUsername;
		this.buyerPassword = buyerPassword;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.walletBalance = walletBalance;
	}

	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	public String getBuyerUsername() {
		return buyerUsername;
	}

	public void setBuyerUsername(String buyerUsername) {
		this.buyerUsername = buyerUsername;
	}

	public String getBuyerPassword() {
		return buyerPassword;
	}

	public void setBuyerPassword(String buyerPassword) {
		this.buyerPassword = buyerPassword;
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

	public double getWalletBalance() {
		return walletBalance;
	}

	public void setWalletBalance(double walletBalance) {
		this.walletBalance = walletBalance;
	}

	@Override
	public String toString() {
		return "Buyer [buyerId=" + buyerId + ", buyerUsername=" + buyerUsername + ", buyerPassword=" + buyerPassword
				+ ", name=" + name + ", email=" + email + ", phoneNumber=" + phoneNumber + ", walletBalance="
				+ walletBalance + "]";
	}

}
