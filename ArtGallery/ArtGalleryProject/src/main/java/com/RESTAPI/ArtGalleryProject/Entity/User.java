package com.RESTAPI.ArtGalleryProject.Entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userId;

	@OneToOne
	@JoinColumn(name = "user_email", referencedColumnName = "email")
	private LoginCredentials userEmail;
	@Column(name = "user_type", nullable = false)
	private String userType = "buyer"; // default in Java side too
	private String name;
	private String phoneNumber;
	private boolean authorizedSeller;
	private LocalDate createdAt;

	// relational

	// Wallet
	@OneToOne(mappedBy = "user")
	private Wallet wallet;

	// paintings uploaded as seller
	@OneToMany(mappedBy = "seller")
	private List<Painting> paintingsSold;

	// paintings bought as buyer
	@OneToMany(mappedBy = "buyer")
	private List<Painting> paintingsBought;

	// bidding of user (many to many)
	@OneToMany(mappedBy = "buyer")
	private List<Bid> bids;
}
