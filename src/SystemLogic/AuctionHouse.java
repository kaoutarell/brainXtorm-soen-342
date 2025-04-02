package SystemLogic;

import java.util.ArrayList;

public class AuctionHouse {
    private String location;
    private String description;
    private ArrayList<Auction> auctionList = new ArrayList<>();

    public String getDescription(){
        return ("location: "+this.location+", description: "+this.description);
    }
}
