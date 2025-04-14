package SystemLogic;

import DataManagement.ObjectOfInterestRepository;

public class ObjectOfInterest {
    private boolean owned;
    private String description;

    public ObjectOfInterest(boolean owned, String description) {
        this.owned = owned;
        this.description = description;
    }

    public void setDescription(String description){
        this.description=description;
    }

    public boolean isOwned() {
        return owned;
    }

    public String getDescription() {
        return description;
    }

    public void switchOwnership(){
        this.owned=!this.owned;
    }

    public boolean add(){
       return ObjectOfInterestRepository.addObject(this);
    }

    public boolean delete(){
        return ObjectOfInterestRepository.deleteObject(this);
    }

    public boolean update(){
        return ObjectOfInterestRepository.updateObject(this);
    }

    @Override
    public String toString() {
        return ("Object: " + description + " | Owned: " + (owned ? "Yes" : "No"));
    }
}
