package com.RESTAPI.ArtGalleryProject.Embeddable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Dimensions {
	private double length;
	private double breadth;
	
	@Override
	public String toString() {
		return length + " x " + breadth;
	}
	
}