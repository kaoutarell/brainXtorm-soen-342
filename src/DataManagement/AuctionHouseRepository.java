package DataManagement;

import SystemLogic.AuctionHouse;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AuctionHouseRepository {
    private static final String HOUSE_FILE = "src/DataManagement/data/auction_houses.csv";

    public static void saveAuctionHouse(AuctionHouse house) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(HOUSE_FILE, true))) {
            String line = String.join(",",
                    house.getLocation(),
                    house.getDescription()
            );
            writer.write(line);
            writer.newLine(); // Adds a line break
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<AuctionHouse> getAllHouses() {
        List<AuctionHouse> houses = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(HOUSE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 2);
                if (parts.length >= 2) {
                    AuctionHouse house = new AuctionHouse();
                    house.setLocation(parts[0]);
                    house.setDescription(parts[1]);
                    houses.add(house);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return houses;
    }

    public static void deleteAllAuctionHouses() {
        try {
            new PrintWriter(HOUSE_FILE).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

