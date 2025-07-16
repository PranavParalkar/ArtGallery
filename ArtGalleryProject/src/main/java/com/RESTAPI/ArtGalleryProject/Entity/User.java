package com.RESTAPI.ArtGalleryProject.Entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
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

	@Embedded
	private Address address;
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

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
class Address {
	private String building;
	private String street;
	private String city;
	private String region;
	private String country;
	private int pinCode;
}
