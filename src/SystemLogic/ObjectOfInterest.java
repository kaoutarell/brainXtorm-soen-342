package SystemLogic;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ObjectOfInterest {
    private UUID id;
    private boolean owned;
    private String description;
    private List<Auction> auctions;

    public ObjectOfInterest(boolean owned, String description) {
        this.id = UUID.randomUUID();
        this.owned = owned;
        this.description = description;
        this.auctions = new ArrayList<>();
    }

    public ObjectOfInterest(UUID id, boolean owned, String description) {
        this.id = id;
        this.owned = owned;
        this.description = description;
        this.auctions = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public boolean isOwned() {
        return owned;
    }

    public String getDescription() {
        return description;
    }

    public void setOwned(boolean owned) {
        this.owned = owned;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void buyObject(){
        this.owned=true;
    }

    public List<Auction> getAuctions() {
        return auctions;
    }

    public void addAuction(Auction auction) {
        if (!auctions.contains(auction)) {
            auctions.add(auction);
            auction.addObject(this); // maintain bidirectional consistency
        }
    }

    @Override
    public String toString() {
        return "Object: " + description + " | Owned: " + (owned ? "Yes" : "No");
    }

    public String toCSV() {
        return id.toString() + "," + owned + "," + description;
    }

    public static ObjectOfInterest fromCSV(String line) {
        String[] parts = line.split(",", 3);
        UUID id = UUID.fromString(parts[0].trim());
        boolean owned = Boolean.parseBoolean(parts[1].trim());
        String name = parts[2].trim();
        return new ObjectOfInterest(id, owned, name);
    }
    
    
}
