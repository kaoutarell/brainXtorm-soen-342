package SystemLogic;

public class ObjectOfInterest {
    private boolean owned;
    private String description;

    public ObjectOfInterest(boolean owned, String description) {
        this.owned = owned;
        this.description = description;
    }

    public boolean isOwned() {
        return owned;
    }

    public String getDescription() {
        return description;
    }

    public void buyObject(){
        this.owned=true;
    }

    @Override
    public String toString() {
        return ("Object: " + description + " | Owned: " + (owned ? "Yes" : "No"));
    }
}
