package SystemLogic;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AuctionHouse {
    private String location;
    private String description;
    private List<Auction> auctionList = new ArrayList<>();

    public AuctionHouse(String location, String description) {
        this.location = location;
        this.description = description;
    }
    public AuctionHouse() { //default constructor
        this.location = "";
        this.description = "";
        this.auctionList = new ArrayList<>();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Auction> getAuctionList() {
        return auctionList;
    }

    public void addAuction(Auction auction) {
        // Ensure auctions are limited to one day only --> Bonus point (Iteration 3)
        LocalDate startDate = auction.getStartTime().toLocalDateTime().toLocalDate();
        LocalDate endDate = auction.getEndTime().toLocalDateTime().toLocalDate();

        if (!startDate.equals(endDate)) {
            System.out.println("Invalid Auction: Auctions must start and end on the same day.");
            return;
        }

        // Check for conflicts: 1. Same specialty 2. Same day 3.Same location | requirements
        for (Auction a : auctionList) {
            boolean sameSpecialty = a.getSpecialty().equalsIgnoreCase(auction.getSpecialty());
            boolean sameDay = a.getStartTime().toLocalDateTime().toLocalDate().equals(startDate);
            boolean timeOverlap = a.getEndTime().after(auction.getStartTime()) && a.getStartTime().before(auction.getEndTime());

            if (sameSpecialty && sameDay && timeOverlap) {
                System.out.println("Conflict: An auction of this specialty already exists at this location during this time.");
                return;
            }
        }

        auctionList.add(auction);
        System.out.println("Auction added successfully to " + this.location);
    }


    public String getAuctionHouseInfo() {
        return "Location: " + this.location + "\nDescription: " + this.description;
    }
}
