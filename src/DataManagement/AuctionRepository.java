package DataManagement;

import SystemLogic.Auction;
import SystemLogic.Auction.AuctionType;
import SystemLogic.AuctionHouse;
import SystemLogic.ObjectOfInterest;

import java.sql.Timestamp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AuctionRepository {
    // public static ArrayList<Auction> getAllAuctions(){ -- won't work anymore given the new definition of Auction class
    //     //TODO
    //     ArrayList<Auction> auctions = new ArrayList<>();
    //     auctions.add(new Auction("The big Auction", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), "everything", "in person", new AuctionHouse()));
    //     auctions.add(new Auction("The mid Auction", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), "everything", "in person", new AuctionHouse()));
    //     auctions.add(new Auction("The small Auction", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), "everything", "in person", new AuctionHouse()));
    //     return auctions;
    // }

    private static final String AUCTION_FILE = "src/DataManagement/data/auctions.csv";

    public static void saveAuction(Auction auction) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(AUCTION_FILE, true))) {
            String objectIds = "";
            if (auction.getObjects() != null && !auction.getObjects().isEmpty()) {
                objectIds = String.join(";", auction.getObjects().stream()
                        .map(obj -> obj.getId().toString())
                        .toList());
            }

            String line = String.join(",",
                    auction.getName(),
                    auction.getStartTime().toString(),
                    auction.getEndTime().toString(),
                    auction.getSpecialty(),
                    auction.getType().toString(),
                    auction.getHouse().getLocation(),
                    objectIds
            );
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Auction> getAllAuctions(List<AuctionHouse> houses) {
        List<Auction> auctions = new ArrayList<>();
        List<ObjectOfInterest> allObjects = ObjectOfInterestRepository.getAllObjects();

        try (BufferedReader reader = new BufferedReader(new FileReader(AUCTION_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 7); // 7 to capture optional objects field
                if (parts.length >= 6) {
                    String name = parts[0];
                    Timestamp start = Timestamp.valueOf(parts[1]);
                    Timestamp end = Timestamp.valueOf(parts[2]);
                    String specialty = parts[3];
                    AuctionType type = AuctionType.valueOf(parts[4].toUpperCase());
                    String location = parts[5];
                    String objectIdsPart = parts.length == 7 ? parts[6] : "";

                    AuctionHouse house = houses.stream()
                            .filter(h -> h.getLocation().equals(location))
                            .findFirst()
                            .orElse(null);

                    if (house != null) {
                        List<ObjectOfInterest> associatedObjects = new ArrayList<>();
                        if (!objectIdsPart.isEmpty()) {
                            String[] objectIds = objectIdsPart.split(";");
                            for (String idStr : objectIds) {
                                try {
                                    UUID uuid = UUID.fromString(idStr.trim());
                                    allObjects.stream()
                                            .filter(o -> o.getId().equals(uuid))
                                            .findFirst()
                                            .ifPresent(associatedObjects::add);
                                } catch (IllegalArgumentException ignored) {
                                }
                            }
                        }
                        auctions.add(new Auction(name, start, end, specialty, type, house, associatedObjects));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return auctions;
    }

    public static List<Auction> getAllAuctions_(List<AuctionHouse> houses, List<ObjectOfInterest> allObjects) {
        List<Auction> auctions = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(AUCTION_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue; // Skip empty lines
                }
        
                String[] parts = line.split(",", 7); // Split into 7 parts to handle the optional objects field
                if (parts.length >= 6) {
                    String name = parts[0];
                    Timestamp start = Timestamp.valueOf(parts[1]);
                    Timestamp end = Timestamp.valueOf(parts[2]);
                    String specialty = parts[3];
                    AuctionType type = AuctionType.valueOf(parts[4].toUpperCase());
                    String location = parts[5];
                    String objectIdsPart = (parts.length == 7) ? parts[6] : "";
        
                    // Find the AuctionHouse by location
                    AuctionHouse house = houses.stream()
                            .filter(h -> h.getLocation().equals(location))
                            .findFirst()
                            .orElse(null);
        
                    if (house != null) {
                        // Match ObjectOfInterest by IDs if available
                        List<ObjectOfInterest> associatedObjects = new ArrayList<>();
                        if (!objectIdsPart.isEmpty()) {
                            String[] objectIds = objectIdsPart.split(";");
                            for (String idStr : objectIds) {
                                try {
                                    UUID uuid = UUID.fromString(idStr.trim());
                                    allObjects.stream()
                                            .filter(o -> o.getId().equals(uuid))
                                            .findFirst()
                                            .ifPresent(associatedObjects::add);
                                } catch (IllegalArgumentException ignored) {
                                    // Ignore invalid UUIDs
                                }
                            }
                        }
                        // Create Auction object and add it to the list
                        auctions.add(new Auction(name, start, end, specialty, type, house, associatedObjects));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return auctions;
    }
    
    

    public static void deleteAllAuctions() {
        try {
            new PrintWriter(AUCTION_FILE).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteAuctionByName(String nameToDelete, List<AuctionHouse> houses) {
        List<Auction> auctions = getAllAuctions(houses);
        auctions.removeIf(auction -> auction.getName().equalsIgnoreCase(nameToDelete));
        overwriteFile(auctions);
    }

    public static void updateAuction(String nameToUpdate, Auction updatedAuction, List<AuctionHouse> houses) {
        List<Auction> auctions = getAllAuctions(houses);
        for (int i = 0; i < auctions.size(); i++) {
            if (auctions.get(i).getName().equalsIgnoreCase(nameToUpdate)) {
                auctions.set(i, updatedAuction);
                break;
            }
        }
        overwriteFile(auctions);
    }

    // optional : write entire file with a new list of auctions (bulk insertion)
    private static void overwriteFile(List<Auction> auctions) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(AUCTION_FILE))) {
            for (Auction auction : auctions) {
                String objectIds = "";
                if (auction.getObjects() != null && !auction.getObjects().isEmpty()) {
                    objectIds = String.join(";", auction.getObjects().stream()
                            .map(obj -> obj.getId().toString())
                            .toList());
                }

                String line = String.join(",",
                        auction.getName(),
                        auction.getStartTime().toString(),
                        auction.getEndTime().toString(),
                        auction.getSpecialty(),
                        auction.getType().toString(),
                        auction.getHouse().getLocation(),
                        objectIds
                );
                writer.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
