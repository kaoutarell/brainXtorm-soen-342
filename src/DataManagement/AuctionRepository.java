package DataManagement;

import SystemLogic.Auction;
import SystemLogic.AuctionHouse;

import java.sql.Date;
import java.util.ArrayList;

public class AuctionRepository {
    public static ArrayList<Auction> getAllAuctions(){
        //TODO
        ArrayList<Auction> auctions = new ArrayList<>();
        auctions.add(new Auction("The big Auction", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), "everything", "in person", new AuctionHouse()));
        auctions.add(new Auction("The mid Auction", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), "everything", "in person", new AuctionHouse()));
        auctions.add(new Auction("The small Auction", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), "everything", "in person", new AuctionHouse()));
        return auctions;
    }
}
