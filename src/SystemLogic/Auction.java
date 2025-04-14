package SystemLogic;

import DataManagement.AuctionHouseRepository;
import DataManagement.AuctionRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Auction {
    private String name;
    private Timestamp startTime; //timestamp and not date only -> fixed
    private Timestamp endTime;
    private String specialty;
    private AuctionType type; // "online" or "normal"
    private AuctionHouse house;
    private List<ObjectOfInterest> objects;

    public enum AuctionType { //changed to limit input and reduce conditions
    ONLINE, NORMAL, IN_PERSON
}

    public Auction(String name, Timestamp startTime, Timestamp endTime, String specialty, AuctionType type, AuctionHouse house) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.specialty = specialty;
        this.type = type;
        this.house = house;
        this.objects = new ArrayList<>();
    }

    public Auction(String name, Timestamp startTime, Timestamp endTime, String specialty, AuctionType type,
                   AuctionHouse house, List<ObjectOfInterest> associatedObjects) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.specialty = specialty;
        this.type = type;
        this.house = house;
        this.objects = new ArrayList<>();

        if (associatedObjects != null) {
            for (ObjectOfInterest obj : associatedObjects) {
                addObject(obj);  // bidirectional relationship
            }
        }
    }
    
    // ensure objects list is initialized correctly  -- handles null lists 
    public Auction(String name, Timestamp startTime, Timestamp endTime, String specialty, AuctionType type,
                   AuctionHouse house, ObjectOfInterest obj) {
        this(name, startTime, endTime, specialty, type, house, obj != null ? new ArrayList<>(List.of(obj)) : new ArrayList<>());
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public AuctionType getType() {
        return type;
    }

    public void setType(AuctionType type) {
        this.type = type;
    }

    public AuctionHouse getHouse() {
        return house;
    }

    public void setHouse(AuctionHouse house) {
        this.house = house;
    }

    //relationship with objects
    public List<ObjectOfInterest> getObjects() {
        return objects;
    }

    public void add(){
        AuctionRepository.saveAuction(this);
    }

    public void delete(){
        AuctionRepository.deleteAuctionByName(this.name, AuctionHouseRepository.getAllHouses());
    }

    public void addObject(ObjectOfInterest object) {
        if (!objects.contains(object)) {
            objects.add(object);
            object.addAuction(this); // maintain bidirectional consistency
        }
    }

    @Override
    public String toString() {
        return "🎨 Auction Info:\n" +
                "📛 Name: " + name + "\n" +
                "📅 Date: " + startTime.toLocalDateTime().toLocalDate() + 
                " from " + startTime.toLocalDateTime().toLocalTime() +
                " to " + endTime.toLocalDateTime().toLocalTime() + "\n" +
                "📍 Location: " + house.getLocation() + "\n" +
                "🧭 Specialty: " + specialty + "\n" +
                "🔌 Type: " + type + "\n";
    }
}
