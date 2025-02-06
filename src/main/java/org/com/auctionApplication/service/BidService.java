package org.com.auctionApplication.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.com.auctionApplication.dao.AuctionDao;
import org.com.auctionApplication.dao.BidDao;
import org.com.auctionApplication.exception.AuctionApplicationException;
import org.com.auctionApplication.model.Auction;
import org.com.auctionApplication.model.Bid;
import org.com.auctionApplication.model.User;

import java.util.List;

import static java.lang.String.format;
import static org.com.auctionApplication.enums.UserRole.BUYER;

public class BidService {

    private static final Logger log = LogManager.getLogger(BidService.class);
    private final BidDao bidDao;
    private final UserService userService;
    private final AuctionDao auctionDao; //TODO: have to import dao instead of service to avoid cyclic dependency

    public BidService(BidDao bidDao, AuctionDao auctionDao, UserService userService) {
        this.bidDao = bidDao;
        this.auctionDao = auctionDao;
        this.userService = userService;

    }

    public void createBid(String userName, String auctionName, Double amount) {

        User user = userService.fetchUserDetails(userName);
        Auction auction = auctionDao.findAuction(auctionName);
        if (auction.getActive()) {
            if (validateBid(user, auction, 0D, amount)) {
                userService.debitBudget(user, amount);
                Bid bid = new Bid(userName, amount, auctionName);
                bidDao.insertBid(bid);
            } else {
                throw new AuctionApplicationException("Invalid Bid.");
            }
        } else {
            throw new AuctionApplicationException(format("Auction: %s is closed.", auctionName));
        }
    }

    public void updateBid(String userName, String auctionName, Double amount) {
        User user = userService.fetchUserDetails(userName);
        Auction auction = auctionDao.findAuction(auctionName);
        if (auction.getActive()) {
            List<Bid> bids = bidDao.findBidByUserAndAuction(userName, auctionName);
            Bid lastBid = bids.get(bids.size() - 1);
            if (validateBid(user, auction, lastBid.getBidAmount(), amount)) {
                lastBid.setBidAmount(amount);
            } else {
                throw new AuctionApplicationException("Invalid bid.");
            }
        } else {
            throw new AuctionApplicationException(format("Auction: %s is closed.", auctionName));
        }
    }

    public void withdrawBid(String userName, String auctionName) {
        User user = userService.fetchUserDetails(userName);
        Auction auction = auctionDao.findAuction(auctionName);
        if(auction.getActive() && validBuyerCheck(user)) {
            List<Bid> bidsToBeWithdrawn = bidDao.findBidByUserAndAuction(userName, auctionName);
            bidsToBeWithdrawn.forEach(bid -> {
                userService.creditBudget(user, bid.getBidAmount());
            });
            bidDao.deleteBid(userName, auctionName);
        } else {
            throw new AuctionApplicationException(format("Auction %s is closed. OR user %s is not a buyer", auctionName, userName));
        }
    }

    public List<Bid> getAllBidsForAuction(String auctionName) {
        return bidDao.findBidByAuction(auctionName);
    }

    private Boolean validateBid(User user, Auction auction, Double oldAmount, Double newAmount) {
        if(validBuyerCheck(user)) {
            return userBudgetClearanceCheck(user, newAmount-oldAmount) && auctionBidLimitCheck(auction, newAmount);
        } else {
            throw new AuctionApplicationException(format("User: %s is not a buyer.", user.getUserName()));
        }
    }

    private Boolean userBudgetClearanceCheck(User user, Double amount) {
        Boolean valid = validBuyerCheck(user) && user.getUserBudget().compareTo(amount) >= 0;
        if(!valid) {
            log.error("Bid exceeds budget.");
        }
        return valid;
    }

    private Boolean auctionBidLimitCheck(Auction auction, Double amount) {
        Boolean valid = (auction.getBidUpperLimit().compareTo(amount) >= 0) &&
                (auction.getBidLowerLimit().compareTo(amount) <= 0);
        if(!valid) {
            log.error("Bid exceeds auction bid limits.");
        }
        return valid;
    }

    private Boolean validBuyerCheck(User user) {
        return userService.authenticateUserForRole(user.getUserName(), BUYER);
    }
}
