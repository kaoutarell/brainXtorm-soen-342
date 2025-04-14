package Interface;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.sql.Timestamp;

import DataManagement.AuctionHouseRepository;
import DataManagement.AuctionRepository;
import DataManagement.ExpertRepository;
import DataManagement.ObjectOfInterestRepository;
import SystemLogic.Admin;
import SystemLogic.AreaOfExpertise;
import SystemLogic.Auction;
import SystemLogic.AuctionHouse;
import SystemLogic.Client;
import SystemLogic.Expert;
import SystemLogic.ObjectOfInterest;
import SystemLogic.User;



public class InstitutionConsole{

    private static Menu menu = new ClientMenu();
    private static User currentUser;

    public static void main(String[] args) {
        
        //tests
        testExpertCreationAndSaving();
        testLoadAllExperts();
        testGetExpertById();
        testAdminLoginSimulation();
        testAuctionInsertions();
        /*while (true){
            displayMainMenu();
        }*/ //temporary comment to test the new code
    }

    
    // TEST #1: Create an expert and add area of expertise
    private static void testExpertCreationAndSaving() {
        List<AreaOfExpertise> areas = new ArrayList<>();
        areas.add(new AreaOfExpertise("Paintings", "Artistic works using oil or acrylic"));
        areas.add(new AreaOfExpertise("Latin Manuscripts", "Historic handwritten Latin documents"));

        Expert expert = new Expert("expert1", "pass123", "Sophia Turner", 456789, "sophia@example.com", areas);
        ExpertRepository.createExpert(expert);
        System.out.println("Expert created and saved successfully.");
    }

    // TEST #2: Load all experts from CSV
    private static void testLoadAllExperts() {
        List<Expert> experts = ExpertRepository.getAllExperts();
        System.out.println("\nExperts loaded from file:");
        for (Expert exp : experts) {
            exp.displayExpertInfo();
            System.out.println("------");
        }
    }

    // TEST #3: Get Expert by ID
    private static void testGetExpertById() {
        List<Expert> allExperts = ExpertRepository.getAllExperts();
        if (allExperts.isEmpty()) {
            System.out.println("No experts found for ID test.");
            return;
        }

        UUID testId = allExperts.get(0).getId();
        Expert retrieved = ExpertRepository.getExpertById(testId);
        if (retrieved != null) {
            System.out.println("Expert found by ID: " + retrieved.getUsername());
        } else {
            System.out.println("Expert not found.");
        }
    }

    // TEST #4: Admin login simulation --> getting the admin credentials from admin.csv
    private static void testAdminLoginSimulation() {
        Admin admin = new Admin();
        System.out.println("\nAdmin login simulation:");
        System.out.println("Username: " + admin.getUsername());
        System.out.println("Password: " + admin.getPassword());
    }

    //TEST #5 -- auctions - auction houses - objects of interest
    public static void testAuctionInsertions() {
    // Step 1: Create an Auction House
    AuctionHouse house = new AuctionHouse("Big City Auction House", "New York");
    AuctionHouseRepository.saveAuctionHouse(house); // you must implement this or store manually

    // Step 2: Create some Objects
    ObjectOfInterest obj1 = new ObjectOfInterest(true, "Ancient Vase");
    ObjectOfInterest obj2 = new ObjectOfInterest(false, "Old Book");

    ObjectOfInterestRepository.saveObject(obj1);
    ObjectOfInterestRepository.saveObject(obj2);

    // Step 3: Create Auctions (with and without objects)
    Auction auction1 = new Auction(
            "Antique Auction",
            Timestamp.valueOf("2025-05-01 10:00:00"),
            Timestamp.valueOf("2025-05-01 18:00:00"),
            "Antiques",
            Auction.AuctionType.ONLINE,
            house,
            obj1 // with object
    );

    Auction auction2 = new Auction(
        "Antique Auction",
        Timestamp.valueOf("2025-05-01 10:00:00"),
        Timestamp.valueOf("2025-05-01 18:00:00"),
        "Antiques",
        Auction.AuctionType.ONLINE,
        house,
        new ArrayList<ObjectOfInterest>() // empty list
    );




    AuctionRepository.saveAuction(auction1);
    AuctionRepository.saveAuction(auction2);

    // display
    List<Auction> auctions = AuctionRepository.getAllAuctions_(
        AuctionHouseRepository.getAllHouses(), 
        ObjectOfInterestRepository.getAllObjects() //passing all objects auctioned ..
    );
    for (Auction a : auctions) {
        System.out.print("Auction: " + a.getName() + " | Objects: ");
        if (a.getObjects() != null && !a.getObjects().isEmpty()) {
            // all objects associated with the auction
            for (ObjectOfInterest obj : a.getObjects()) {
                System.out.print(obj.getDescription() + ", ");
            }
        } else {
            System.out.print("None");
        }
        System.out.println(); // each auction in a line
    }

}


    private static void displayMainMenu(){
        menu.displayMenuOptions();

        selectMainMenuOption();
        
    }

    private static void selectMainMenuOption(){
        Scanner scanner = new Scanner(System.in);
        String choice;
        
        int response=1;

        while(response==1){
            System.out.println("Enter your choice: ");
            choice=scanner.nextLine();
            response=menu.handleMenuInput(choice);
        }


    }

    public static void setCurrentUser(User user){
        currentUser = user;
        if (user == null){
         menu=new ClientMenu();
        }else if(user instanceof Client){
            menu=new ClientMenu();
        }else if(user instanceof Expert){
            menu=new ExpertMenu();
        }else if(user instanceof Admin){
            menu=new AdminMenu();
        }
    }

    public static User getCurrentUser(){
        return currentUser;
    }

}