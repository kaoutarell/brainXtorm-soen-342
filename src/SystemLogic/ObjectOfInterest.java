package SystemLogic;

import java.util.UUID;

public class ObjectOfInterest {
    private UUID id;
    private boolean owned;
    private String description;

    public ObjectOfInterest(boolean owned, String description) {
        this.id = UUID.randomUUID();
        this.owned = owned;
        this.description = description;
    }

    public ObjectOfInterest(UUID id, boolean owned, String description) {
        this.id = id;
        this.owned = owned;
        this.description = description;
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

    @Override
    public String toString() {
        return ("Object: " + description + " | Owned: " + (owned ? "Yes" : "No"));
    }

    public static ObjectOfInterest fromCSV(String csvLine) {
        String[] parts = csvLine.split(",", 3);
        UUID id = UUID.fromString(parts[0]);
        boolean owned = Boolean.parseBoolean(parts[1]);
        String description = parts[2];
        return new ObjectOfInterest(id, owned, description);
    }
    
}
