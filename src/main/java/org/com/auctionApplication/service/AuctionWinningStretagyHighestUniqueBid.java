package org.com.auctionApplication.service;

import org.com.auctionApplication.model.Bid;

import java.util.Comparator;
import java.util.List;

import static java.util.Objects.nonNull;

public class AuctionWinningStretagyHighestUniqueBid implements AuctionWinningStretagy {


    @Override
    public Bid getWinnerOfAuction(List<Bid> bids) {
        Bid winningBid=null;
        int bidSize = bids.size();
        bids.sort(Comparator.comparing(Bid::getBidAmount));
        int winningBidIndex = bidSize - 1;
        for (int i = bidSize - 1; i >= 0; i--) {
            winningBid = bids.get(i);
            winningBidIndex = i;
            while (i - 1 >= 0 && bids.get(i - 1).getBidAmount().equals(winningBid.getBidAmount())) {
                i--;
            }
            if (winningBidIndex == i) {
                break;
            }
        }
        return nonNull(winningBid)?winningBid:null;
    }
}
