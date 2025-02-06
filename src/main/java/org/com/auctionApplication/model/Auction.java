package org.com.auctionApplication.model;

import org.com.auctionApplication.service.AuctionWinningStretagy;
import org.com.auctionApplication.service.AuctionWinningStretagyHighestUniqueBid;

public class Auction {

    private String auctionName, sellerName;
    private Double bidLowerLimit, bidUpperLimit;
    private Boolean isActive;
    private AuctionWinningStretagy auctionWinningStretagy;

    public Auction(String auctionName, String sellerName, Double bidLowerLimit, Double bidUpperLimit) {
        this.auctionName = auctionName;
        this.sellerName = sellerName;
        this.bidUpperLimit = bidUpperLimit;
        this.bidLowerLimit = bidLowerLimit;
        this.isActive = true;
        this.auctionWinningStretagy = new AuctionWinningStretagyHighestUniqueBid();
    }

    public Auction(String auctionName, String sellerName, Double bidLowerLimit, Double bidUpperLimit, AuctionWinningStretagy auctionWinningStretagy) {
        this.auctionName = auctionName;
        this.sellerName = sellerName;
        this.bidUpperLimit = bidUpperLimit;
        this.bidLowerLimit = bidLowerLimit;
        this.isActive = true;
        this.auctionWinningStretagy = auctionWinningStretagy;
    }


    public String getAuctionName() {
        return auctionName;
    }

    public String getSellerName() {
        return sellerName;
    }

    public Double getBidUpperLimit() {
        return bidUpperLimit;
    }

    public Double getBidLowerLimit() {
        return bidLowerLimit;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public void setAuctionWinningStretagy(AuctionWinningStretagy auctionWinningStretagy) {
        this.auctionWinningStretagy = auctionWinningStretagy;
    }

    public AuctionWinningStretagy getAuctionWinningStretagy() {
        return auctionWinningStretagy;
    }
}
