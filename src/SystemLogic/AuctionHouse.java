package SystemLogic;

import DataManagement.AuctionHouseRepository;

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

    public void add(){
        AuctionHouseRepository.saveAuctionHouse(this);
    }

    public boolean delete(){
        return AuctionHouseRepository.deleteAuctionHouse(this);
    }

    @Override
    public String toString(){
        return ("Location: "+this.getLocation()+" / Description: "+this.getDescription());
    }

}
