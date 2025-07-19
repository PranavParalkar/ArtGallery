package com.RESTAPI.ArtGalleryProject.DTO.DashBoard;

public class TopBidDTO {
    private int rank;
    private String name;
    private double bid;

    public TopBidDTO(int rank, String name, double bid) {
        this.rank = rank;
        this.name = name;
        this.bid = bid;
    }

    public int getRank() {
        return rank;
    }

    public String getName() {
        return name;
    }

    public double getBid() {
        return bid;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBid(double bid) {
        this.bid = bid;
    }
}
