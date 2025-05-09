package Interface;

import DataManagement.AuctionHouseRepository;
import DataManagement.AuctionRepository;
import DataManagement.ExpertRepository;
import DataManagement.ObjectOfInterestRepository;
import SystemLogic.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

public class AdminMenu extends Menu{

    @Override
    public void displayMenuOptions() {
        System.out.println("1. see objects of interest");
        System.out.println("2. see auctions");
        System.out.println("3. manage objects");
        System.out.println("4. manage pending sign ups");
        System.out.println("5. manage experts");
        System.out.println("6. manage auctions");
        System.out.println("7. manage auction houses");
        System.out.println("0. logout");
    }

    @Override
    public int handleMenuInput(String input) {
        switch (input){
            case "1":
                super.seeObjects();
                return 0;
            case "2":
                super.seeAuctions();
                return 0;
            case "3":
                manageObjects();
                return 0;
            case "4":
                managePendingSignUps();
                return 0;
            case "5":
                manageExperts();
                return 0;
            case "6":
                manageAuctions();
                return 0;
            case "7":
                manageAuctionHouses();
                return 0;
            case "0":
                super.logout();
                return 0;
            default:
                return 1;
        }
    }

    private void manageObjects(){
        System.out.println("1. Create Object");
        System.out.println("2. Update Object");
        System.out.println("3. Delete Object");
        System.out.println("0. Return");
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        switch(input){
            case "1":
                addObject();
                break;
            case "2":
                updateObject();
                break;
            case "3":
                deleteObject();
                break;
            case "0":
                break;
            default:
                System.out.println("Invalid input");
                manageObjects();
                break;
        }
    }

    private void updateObject(){
        System.out.println("Select an object to update");
        List<ObjectOfInterest> objects = ObjectOfInterestRepository.getAllObjects();
        for (int i=0;i< objects.size();i++) {
            System.out.println(i+". "+objects.get(i).toString());
        }
        Scanner scan = new Scanner (System.in);
        String input = scan.nextLine();
        try{
            ObjectOfInterest selectedObject = objects.get(Integer.parseInt(input));
            System.out.println("What do you want to update?");
            System.out.println("1. Switch Ownership");
            System.out.println("2. Change Description");
            input = scan.nextLine();
            switch (input){
                case "1":
                    selectedObject.switchOwnership();
                    System.out.println("Ownership changed");
                    break;
                case "2":
                    System.out.println("Enter the new description:");
                    input = scan.nextLine();
                    selectedObject.setDescription(input);
                    System.out.println("Description changed");
                    break;
                default:
                    System.out.println("Invalid output");
                    return;
            }
            selectedObject.update();
            System.out.println("Object updated");

        }catch (Exception e){
            System.out.println("Invalid input");
        }

    }

    private void addObject(){
        System.out.println("Enter a description for the object");
        Scanner scan = new Scanner(System.in);
        String description=scan.nextLine();
        ObjectOfInterest object = new ObjectOfInterest(false, description);
        object.add();
        System.out.println("Object created");
    }

    private void deleteObject(){
        System.out.println("Select an object to delete:");
        List<ObjectOfInterest> objects = ObjectOfInterestRepository.getAllObjects();
        for (int i=0;i< objects.size();i++) {
            System.out.println(i+". "+objects.get(i).toString());
        }
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        try{
            objects.get(Integer.parseInt(input)).delete();
            System.out.println("Object deleted");
        }catch (Exception e){
            System.out.println("Invalid input");
        }
    }

    private void managePendingSignUps(){
        ((Admin)InstitutionConsole.getCurrentUser()).approveSignUp();
    }

    private void manageExperts(){
        System.out.println("1. Create Expert");
        System.out.println("2. Update Expert");
        System.out.println("3. Delete Expert");
        System.out.println("0. Return");
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        switch(input){
            case "1":
                addExpert();
                break;
            case "2":
                updateExpert();
                break;
            case "3":
                deleteExpert();
                break;
            case "0":
                break;
            default:
                System.out.println("Invalid input");
                manageObjects();
                break;
        }
    }

    private void addExpert(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter username:");
        String username=scan.nextLine();
        System.out.println("Enter password:");
        String password=scan.nextLine();
        System.out.println("Enter name:");
        String name=scan.nextLine();
        int licenceNumber;
        try{
            System.out.println("Enter licence number:");
            licenceNumber=Integer.parseInt(scan.nextLine());
        }catch (Exception e){
            System.out.println("Invalid input. Not a number");
            return;
        }
        System.out.println("Enter contact info:");
        String contactInfo=scan.nextLine();
        System.out.println("Enter list of expertise. Write \"exit\" when the list is complete.");
        ArrayList<AreaOfExpertise> expertise = new ArrayList<>();
        String input = "";
        while(!input.equalsIgnoreCase("exit")){
            input = scan.nextLine();
            if(!input.equalsIgnoreCase("exit")){
                expertise.add(new AreaOfExpertise("Expertise", input));
            }

        }
        Expert expert = new Expert(username, password, name, licenceNumber, contactInfo, expertise, new ArrayList<>());
        boolean response = expert.add();
        if(response){
            System.out.println("Expert created");
        }else{
            System.out.println("Failed to create expert");
        }
    }

    private void updateExpert(){
        System.out.println("Select an expert to update:");
        ArrayList<Expert> experts = ExpertRepository.getAllExperts();
        for (int i=0;i< experts.size();i++) {
            System.out.println(i+". "+experts.get(i).toString());
        }
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        try{
            Expert selectedExpert = experts.get(Integer.parseInt(input));
            System.out.println("Enter the new information:");
            System.out.println("Enter username:");
            String username=scan.nextLine();
            System.out.println("Enter password:");
            String password=scan.nextLine();
            System.out.println("Enter name:");
            String name=scan.nextLine();
            int licenceNumber;
            try{
                System.out.println("Enter licence number:");
                licenceNumber=Integer.parseInt(scan.nextLine());
            }catch (Exception e){
                System.out.println("Invalid input. Not a number");
                return;
            }
            System.out.println("Enter contact info:");
            String contactInfo=scan.nextLine();
            System.out.println("Enter list of expertise. Write \"exit\" when the list is complete.");
            ArrayList<AreaOfExpertise> expertise = new ArrayList<>();
            input = "";
            while(!input.equalsIgnoreCase("exit")){
                input = scan.nextLine();
                expertise.add(new AreaOfExpertise("Expertise", input));
            }
            boolean response = selectedExpert.update(username, password, name, licenceNumber, contactInfo, expertise);
            if (response){
                System.out.println("Expert updated");
            }else{
                System.out.println("Failed to update expert");
            }
        }catch (Exception e){
            System.out.println("Invalid input");
        }
    }

    private void deleteExpert(){
        System.out.println("Select an expert to update:");
        ArrayList<Expert> experts = ExpertRepository.getAllExperts();
        for (int i=0;i< experts.size();i++) {
            System.out.println(i+". "+experts.get(i).toString());
        }
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        try {
            Expert selectedExpert = experts.get(Integer.parseInt(input));
            boolean response = selectedExpert.delete();
            if (response){
                System.out.println("Expert deleted");
            }else{
                System.out.println("Failed to delete expert");
            }
        }catch (Exception e){
            System.out.println("Invalid input");
        }
    }

    private void manageAuctions(){
        System.out.println("1. Create Auction");
        System.out.println("2. Delete Auction");
        System.out.println("0. Return");
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        switch(input){
            case "1":
                addAuction();
                break;
            case "2":
                deleteAuction();
                break;
            case "0":
                break;
            default:
                System.out.println("Invalid input");
                manageObjects();
                break;
        }
    }

    private void addAuction(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter a name");
        String name = scan.nextLine();
        System.out.println("Enter the date of the auction (yyyy-MM-dd)");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp startDate;
        Timestamp endDate;
        try{
            String day = scan.nextLine();
            System.out.println("Enter the start time (HH:mm:ss)");
            startDate = Timestamp.valueOf(day+" "+scan.nextLine());
            System.out.println("Enter the end time (HH:mm:ss)");
            endDate = Timestamp.valueOf(day+" "+scan.nextLine());
        }catch(Exception e){
            System.out.println("Invalid input");
            return;
        }
        System.out.println("Enter the specialty");
        String specialty = scan.nextLine();
        System.out.println("Enter the type (online/in person)");
        String input = scan.nextLine();
        Auction.AuctionType type;
        if (input.equalsIgnoreCase("online")){
            type=Auction.AuctionType.ONLINE;
        } else if (input.equalsIgnoreCase("in person")) {
            type= Auction.AuctionType.IN_PERSON;
        }else{
            type= Auction.AuctionType.NORMAL;
        }

        List<AuctionHouse> houses = AuctionHouseRepository.getAllHouses();
        for (int i = 0; i< Objects.requireNonNull(houses).size(); i++){
            System.out.println(i+". "+houses.get(i).toString());
        }
        AuctionHouse auctionHouse;
        System.out.println("Select an auction house");
        try{
            auctionHouse=houses.get(Integer.parseInt(scan.nextLine()));
        }catch (Exception e){
            System.out.println("Invalid input");
            return;
        }
        Auction auction = new Auction(UUID.randomUUID(), name, startDate, endDate, specialty, type, auctionHouse);
        auction.add();
        System.out.println("Auction created");

    }

    private void deleteAuction(){
        List<Auction> auctions = AuctionRepository.getAllAuctions(AuctionHouseRepository.getAllHouses());
        Scanner scan = new Scanner(System.in);
        System.out.println("Select an auction to delete");
        for (int i=0;i<auctions.size();i++){
            System.out.println(i+". "+auctions.get(i).toString());
        }
        try{
            Auction selectedAuction=auctions.get(Integer.parseInt(scan.nextLine()));
            selectedAuction.delete();
            System.out.println("Auction deleted");
        }catch (Exception e){
            System.out.println("Invalid input");
        }
    }

    private void manageAuctionHouses(){
        System.out.println("1. Create Auction House");
        System.out.println("2. Delete Auction House");
        System.out.println("0. Return");
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        switch(input){
            case "1":
                addAuctionHouse();
                break;
            case "2":
                deleteAuctionHouse();
                break;
            case "0":
                break;
            default:
                System.out.println("Invalid input");
                manageObjects();
                break;
        }
    }

    private void addAuctionHouse(){
        System.out.println("Enter a location");
        Scanner scan = new Scanner(System.in);
        String location = scan.nextLine();
        System.out.println("Enter a description");
        String description = scan.nextLine();
        AuctionHouse auctionHouse = new AuctionHouse(location, description);
        auctionHouse.add();
    }

    private void deleteAuctionHouse(){
        List<AuctionHouse> houses = AuctionHouseRepository.getAllHouses();
        Scanner scan = new Scanner(System.in);
        for (int i = 0; i< Objects.requireNonNull(houses).size(); i++){
            System.out.println(i+". "+houses.get(i).toString());
        }
        try{
            AuctionHouse selected = houses.get(Integer.parseInt(scan.nextLine()));
            boolean response = selected.delete();
            if(response){
                System.out.println("Auction house deleted");
            }else{
                System.out.println("Failed to delete auction house");
            }
        }catch (Exception e){
            System.out.println("Invalid input");
        }

    }

}
