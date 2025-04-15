package DataManagement;

import SystemLogic.Auction;
import SystemLogic.Auction.AuctionType;
import SystemLogic.AuctionHouse;
import SystemLogic.ObjectOfInterest;

import java.sql.Timestamp;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class AuctionRepository {
    private static final String AUCTION_FILE = "src/DataManagement/data/auctions.csv";

    // Save new auction to the CSV file
    public static void saveAuction(Auction auction) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(AUCTION_FILE, true))) {
            String objectIds = auction.getObjects() != null && !auction.getObjects().isEmpty() 
                               ? String.join(";", auction.getObjects().stream()
                                                          .map(obj -> obj.getId().toString())
                                                          .collect(Collectors.toList())) 
                               : "";

            String line = String.join(",",
                    UUID.randomUUID().toString(),
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

    // Fetch all auctions from the CSV file
    public static List<Auction> getAllAuctions(List<AuctionHouse> houses) {
        List<Auction> auctions = new ArrayList<>();
        List<ObjectOfInterest> allObjects = ObjectOfInterestRepository.getAllObjects();

        try (BufferedReader reader = new BufferedReader(new FileReader(AUCTION_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;  // Skip empty lines
                
                String[] parts = line.split(",", -1);  // handle all commas even in quoted fields
                
                if (parts.length >= 7) {  // basic 7 fields are present
                    UUID id = UUID.fromString(parts[0]);
                    String name = parts[1];
                    Timestamp start = Timestamp.valueOf(parts[2]);
                    Timestamp end = Timestamp.valueOf(parts[3]);
                    String specialty = parts[4];
                    AuctionType type = AuctionType.valueOf(parts[5].toUpperCase());
                    String location = parts[6];
                    String objectIdsPart = (parts.length > 7) ? parts[7] : "";

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
                                    // Ignore invalid object IDs
                                }
                            }
                        }
                        auctions.add(new Auction(id, name, start, end, specialty, type, house, associatedObjects));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return auctions;
    }

    // Overwrite the file with the updated list of auctions
    private static void overwriteFile(List<Auction> auctions) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(AUCTION_FILE))) {
            for (Auction auction : auctions) {
                String objectIds = auction.getObjects() != null && !auction.getObjects().isEmpty()
                                   ? String.join(";", auction.getObjects().stream()
                                                          .map(obj -> obj.getId().toString())
                                                          .collect(Collectors.toList()))
                                   : "";

                String line = String.join(",",
                        auction.getId().toString(),
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

    // Delete all auctions
    public static void deleteAllAuctions() {
        try {
            new PrintWriter(AUCTION_FILE).close(); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Delete auction by name
    public static void deleteAuctionByName(String nameToDelete, List<AuctionHouse> houses) {
        List<Auction> auctions = getAllAuctions(houses);
        auctions.removeIf(auction -> auction.getName().equalsIgnoreCase(nameToDelete));
        overwriteFile(auctions);
    }

    // Update an auction by name
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
}
