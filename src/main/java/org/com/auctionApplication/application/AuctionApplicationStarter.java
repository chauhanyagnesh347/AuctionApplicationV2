package org.com.auctionApplication.application;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.com.auctionApplication.dao.AuctionDao;
import org.com.auctionApplication.dao.BidDao;
import org.com.auctionApplication.dao.UserDao;
import org.com.auctionApplication.model.Auction;
import org.com.auctionApplication.service.AuctionService;
import org.com.auctionApplication.service.AuctionWinningStretagyHighestUniqueBid;
import org.com.auctionApplication.service.BidService;
import org.com.auctionApplication.service.UserService;

import java.util.Scanner;

import static org.com.auctionApplication.enums.UserRole.BUYER;
import static org.com.auctionApplication.enums.UserRole.SELLER;

@Slf4j
public class AuctionApplicationStarter {

    private static final Logger log = LogManager.getLogger(AuctionApplicationStarter.class);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        UserDao userDao = new UserDao();
        userDao.initUserData();
        BidDao bidDao = new BidDao();
        bidDao.initBidData();
        AuctionDao auctionDao = new AuctionDao();
        auctionDao.initAuctionData();
        UserService userService = new UserService(userDao);
        BidService bidService = new BidService(bidDao, auctionDao, userService);
        AuctionService auctionService = new AuctionService(auctionDao, bidService, userService);

        while (true) {
            String commandLine = scanner.nextLine();
            String[] parsedCommandLine = commandLine.split(" ");
            String operation = parsedCommandLine[0];
            try {
                switch (operation) {
                    case "ADD_SELLER":
                        userService.createUser(parsedCommandLine[1], SELLER);
                        break;
                    case "ADD_BUYER":
                        if(parsedCommandLine.length==3) {
                            userService.createUser(parsedCommandLine[1], Double.valueOf(parsedCommandLine[2]), BUYER);
                        } else {
                            userService.createUser(parsedCommandLine[1], BUYER);
                        }
                        break;
                    case "CREATE_AUCTION":
                        Auction auction = auctionService.createAuction(parsedCommandLine[1],
                                Double.valueOf(parsedCommandLine[2]),
                                Double.valueOf(parsedCommandLine[3]),
                                parsedCommandLine[4]);
//                        auction.setAuctionWinningStretagy(new AuctionWinningStretagyHighestUniqueBid());
                        break;
                    case "CREATE_BID":
                        bidService.createBid(parsedCommandLine[1],
                                parsedCommandLine[2],
                                Double.valueOf(parsedCommandLine[3]));
                        break;
                    case "UPDATE_BID":
                        bidService.updateBid(parsedCommandLine[1],
                                parsedCommandLine[2],
                                Double.valueOf(parsedCommandLine[3]));
                        break;
                    case "WITHDRAW_BID":
                        bidService.withdrawBid(parsedCommandLine[1],
                                parsedCommandLine[2]);
                        break;
                    case "CLOSE_AUCTION":
                        auctionService.closeAuction(parsedCommandLine[1]);
                        break;
                    default:
                        break;

                }
            } catch (Exception e) {
                log.info("Exception Occured! {}: {}", e.getClass(), e.getMessage());
            }
        }
    }

}
