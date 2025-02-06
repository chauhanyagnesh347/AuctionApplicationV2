package org.com.auctionApplication.service;

import org.com.auctionApplication.model.Bid;

import java.util.List;

public interface AuctionWinningStretagy {


    Bid getWinnerOfAuction(List<Bid> Bids);
}
