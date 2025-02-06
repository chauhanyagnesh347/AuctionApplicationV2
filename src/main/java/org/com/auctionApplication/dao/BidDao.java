package org.com.auctionApplication.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.com.auctionApplication.exception.DataAccessException;
import org.com.auctionApplication.model.Bid;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class BidDao {

    private static final Logger log = LogManager.getLogger(BidDao.class);
    List<Bid> bidData = new ArrayList<>();
    Map<String, List<Bid>> userIndexedBidData = new HashMap<>();

    public void initBidData() {
        bidData.clear();
        userIndexedBidData.clear();
    }

    public void insertBid(Bid bid) {
        bidData.add(bid);
        userIndexedBidData.computeIfAbsent(bid.getUserName(), v -> new ArrayList<>()).add(bid);
    }

    public void deleteBid(Bid bid) {
        bidData.remove(bid);
        userIndexedBidData.remove(bid.getUserName());
    }

    public void deleteBid(String userName, String auctionName) {
        if (userIndexedBidData.containsKey(userName) || userIndexedBidData.get(userName).isEmpty()) {
            List<Bid> bids = findBidByUserAndAuction(userName, auctionName);
            bids.forEach(bid -> {
                userIndexedBidData.get(userName).remove(bid);
                bidData.remove(bid);
            });
        } else {
            throw new DataAccessException(format("Bid for user: %s and auction: %s not found.", userName, auctionName));
        }
    }

    public List<Bid> findBidByUser(String userName) {
        if (userIndexedBidData.containsKey(userName)) {
            return userIndexedBidData.get(userName);
        } else {
            throw new DataAccessException(format("Bid for user: %s not found.", userName));
        }
    }

    public List<Bid> findBidByAuction(String auctionName) {
        List<Bid> bids = bidData.stream().filter(bid -> bid.getAuctionName().equals(auctionName)).collect(Collectors.toList());
        if (!bids.isEmpty()) {
            return bids;
        } else {
            log.info("No bids for Auction: {} found.", auctionName);
            return bids;
        }
    }

    public List<Bid> findBidByUserAndAuction(String userName, String auctionName) {
        List<Bid> userBids = findBidByUser(userName);
        List<Bid> userAuctionBids = userBids.stream().filter(bid -> bid.getAuctionName().equals(auctionName)).collect(Collectors.toList());
        if (!userAuctionBids.isEmpty()) {
            return userAuctionBids;
        } else {
            throw new DataAccessException(format("No bids for User: %s and Auction: %s found.", userName, auctionName));
        }
    }

}
