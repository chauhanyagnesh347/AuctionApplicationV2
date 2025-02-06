package org.com.auctionApplication.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.com.auctionApplication.dao.AuctionDao;
import org.com.auctionApplication.exception.AuctionApplicationException;
import org.com.auctionApplication.model.Auction;
import org.com.auctionApplication.model.Bid;

import java.util.List;

import static java.lang.String.format;

@Slf4j
public class AuctionService {

    private static final Logger log = LogManager.getLogger(AuctionService.class);
    private final AuctionDao auctionDao;
    private final BidService bidService;
    private final UserService userService;



    public AuctionService(AuctionDao auctionDao, BidService bidService, UserService userService) {
        this.auctionDao = auctionDao;
        this.bidService = bidService;
        this.userService = userService;
    }

    //TODO: User service needed here for user authentication
    public Auction createAuction(String auctionName, Double bidLowerLimit, Double bidUpperLimit, String sellerName) {
        Auction auction = new Auction(auctionName, sellerName, bidLowerLimit, bidUpperLimit);
        auctionDao.insertAuction(auction);
        return auction;
    }

    public Boolean isAuctionActive(String auctionName) {
        return auctionDao.findAuction(auctionName).getActive();
    }

    public void closeAuction(String auctionName) {
        Auction auction = auctionDao.findAuction(auctionName);
        if (auction.getActive()) {
            List<Bid> bids = bidService.getAllBidsForAuction(auctionName);
            Bid winnerBid = auction.getAuctionWinningStretagy().getWinnerOfAuction(bids);
            if (winnerBid != null) {
                withdrawBidsExceptWinningBid(winnerBid, bids);
                log.info("Winner of Auction: {} is {}", auctionName, winnerBid.getUserName());
            } else {
                log.info("No winner");
            }
            auction.setActive(false);
        } else {
            throw new AuctionApplicationException(format("Auction: %s is already closed.", auctionName));
        }

    }

    public Auction fetchAuctionDetails(String auctionName) {
        return auctionDao.findAuction(auctionName);
    }

    private void withdrawBidsExceptWinningBid(Bid winningBid, List<Bid> auctionBids) {
        auctionBids.stream().filter(bid -> !bid.equals(winningBid)).forEach(bid -> {
            bidService.withdrawBid(bid.getUserName(), bid.getAuctionName());
        });
    }


}
