package org.com.auctionApplication.model;

public class Bid {

    String userName;
    Double bidAmount;
    String auctionName;

    public Bid(String userName, Double bidAmount, String auctionName) {
        this.userName = userName;
        this.bidAmount = bidAmount;
        this.auctionName = auctionName;
    }

    public String getUserName() {
        return userName;
    }

    public Double getBidAmount() {
        return bidAmount;
    }

    public String getAuctionName() {
        return auctionName;
    }

    public void setBidAmount(Double bidAmount) {
        this.bidAmount = bidAmount;
    }
}
