package org.com.auctionApplication.dao;

import org.com.auctionApplication.exception.DataAccessException;
import org.com.auctionApplication.model.Auction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

public class AuctionDao {

    private final List<Auction> auctionData = new ArrayList<>();
    private final Map<String, Auction> auctionIndex = new HashMap<>();


    public void initAuctionData() {
        auctionData.clear();
        auctionIndex.clear();
    }

    public void insertAuction(Auction auction) {
        auctionData.add(auction);
        auctionIndex.put(auction.getAuctionName(), auction);
    }

    public Auction findAuction(String auctionName) {
        if (auctionIndex.containsKey(auctionName)) {
            return auctionIndex.get(auctionName);
        } else {
            throw new DataAccessException(format("Auction: %s not found!", auctionName));
        }

    }
}
