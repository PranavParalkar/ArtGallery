package com.RESTAPI.ArtGalleryProject.Entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Painting {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long paintingId;

	@Embedded
	private Dimensions dimensions;

	@Column(length = 2048, nullable = false)
	private String imageUrl;

	private String title;
	private String description;
	private int startingPrice;
	private int finalPrice;
	private boolean isSold;

	// --Relation tables

	// user sold painting
	@ManyToOne
	@JoinColumn(name = "seller_id", nullable = false)
	private User seller;

	// user bought painting
	@ManyToOne
	@JoinColumn(name = "buyer_id")
	private User buyer;

	// bid on painting
	@OneToMany(mappedBy = "painting")
	private List<Bid> bids;
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
class Dimensions {
	private int length;
	private int breadth;
	
	@Override
	public String toString() {
		return length + " x " + breadth;
	}
	
}
