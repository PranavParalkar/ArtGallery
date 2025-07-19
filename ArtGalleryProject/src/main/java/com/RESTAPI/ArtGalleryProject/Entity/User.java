package com.RESTAPI.ArtGalleryProject.Entity;

import java.time.LocalDate;
import java.util.List;

import com.RESTAPI.ArtGalleryProject.Embeddable.Address;
import com.RESTAPI.ArtGalleryProject.Enum.LicenseStatus;

import ch.qos.logback.classic.spi.STEUtil;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

	@Embedded
	private Address address;
	private String name;
	private String phoneNumber;

	private boolean authorizedSeller;
	private LocalDate createdAt;

	  // ✅ New fields for license authentication
    private String licenseFilePath;

    @Enumerated(EnumType.STRING)
    private LicenseStatus licenseStatus = LicenseStatus.PENDING;

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
	
	// Setter for licenseStatus
	public void setLicenseStatus(LicenseStatus licenseStatus) {
	    this.licenseStatus = licenseStatus;
	}

	// Setter for authorizedSeller
	public void setAuthorizedSeller(boolean authorizedSeller) {
	    this.authorizedSeller = authorizedSeller;
	}

}

