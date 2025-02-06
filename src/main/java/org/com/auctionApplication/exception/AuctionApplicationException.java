package org.com.auctionApplication.exception;

public class AuctionApplicationException extends RuntimeException {

    public AuctionApplicationException(String exceptionMessage) {
        super(exceptionMessage);
    }

    public AuctionApplicationException(String exceptionMessage, Throwable t) {
        super(exceptionMessage, t);
    }

    public AuctionApplicationException(Throwable t) {
        super(t);
    }

    public AuctionApplicationException(Exception e) {
        super(e);
    }

}
