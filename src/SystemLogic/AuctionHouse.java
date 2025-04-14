package SystemLogic;

import DataManagement.AuctionHouseRepository;

import java.util.ArrayList;

public class AuctionHouse {
    private String location;
    private String description;
    private ArrayList<Auction> auctionList = new ArrayList<>();

    public AuctionHouse(String location, String description){
        this.location = location;
        this.description = description;
    }

    public boolean add(){
        return AuctionHouseRepository.addAuctionHouse(this);
    }

    public boolean delete(){
        return AuctionHouseRepository.deleteAuctionHouse(this);
    }

    public String getDescription(){
        return ("location: "+this.location+", description: "+this.description);
    }
}
