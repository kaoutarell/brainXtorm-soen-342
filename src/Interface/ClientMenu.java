package Interface;

import DataManagement.AuctionHouseRepository;
import DataManagement.AuctionRepository;
import DataManagement.ExpertRepository;
import DataManagement.ServiceRepository;
import SystemLogic.Auction;
import SystemLogic.Client;
import SystemLogic.Expert;
import SystemLogic.Service;

import java.text.SimpleDateFormat;
import java.util.*;

public class ClientMenu extends Menu{

    @Override
    public void displayMenuOptions() {
        if (InstitutionConsole.getCurrentUser()!=null){
            System.out.println("1. see objects of interest");
            System.out.println("2. see auctions");
            System.out.println("3. request service");
            System.out.println("0. logout");
        }else{
            System.out.println("1. login");
            System.out.println("2. sign up");
        }
    }

    @Override
    public int handleMenuInput(String input) {
        switch (input) {
            case "1":
                if(InstitutionConsole.getCurrentUser()!=null){
                    super.seeObjects();
                }else{
                    super.login();
                }
                return 0;

            case "2":
                if(InstitutionConsole.getCurrentUser()!=null){
                    super.seeAuctions();
                }else{
                    super.signUp();
                }
                return 0;

            case "3":
                if(InstitutionConsole.getCurrentUser()!=null){
                    this.requestService();
                    return 0;
                };
                break;

            case "0":
                if(InstitutionConsole.getCurrentUser()!=null){
                    super.logout();
                    return 0;
                }
                break;
            default:
                break;
        }
        System.out.println("Invalid input");
        return 1;
    }

    private void requestService(){
        Scanner scan = new Scanner(System.in);
        Service.ServiceType type = null;
        while (type==null){
            System.out.println("Enter service type: ");
            System.out.println("1. Consultation Service");
            System.out.println("2. Auction assistance service");
            String input = scan.nextLine();
            switch (input) {
                case "1":
                    type = Service.ServiceType.CONSULTATION;
                    break;
                case "2":
                    type = Service.ServiceType.AUCTION_ASSISTANCE;
                    break;
                default:
                    System.out.println("Invalid input");
                    return;
            }
        }

        Date start;
        Date end;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String details;

        if(type== Service.ServiceType.CONSULTATION){
            try{
                System.out.println("Enter the start time (yyyy-MM-dd HH:mm:ss)");
                start = dateFormat.parse(scan.nextLine());
                System.out.println("Enter the end time (yyyy-MM-dd HH:mm:ss)");
                end = dateFormat.parse(scan.nextLine());
                System.out.println("Enter any additional details you like");
                details = scan.nextLine();
            }catch (Exception e){
                System.out.println("Invalid input");
                return;
            }
        }else{
            System.out.println("Select an auction");
            List<Auction> auctions = AuctionRepository.getAllAuctions(AuctionHouseRepository.getAllHouses());
            for (int i=0;i< auctions.size();i++){
                System.out.println(i+": "+auctions.get(i).toString());
            }
            try{
                Auction currentAuction = auctions.get(Integer.parseInt(scan.nextLine()));
                start = currentAuction.getStartTime();
                end = currentAuction.getEndTime();
                details = currentAuction.getName();
            }catch (Exception e){
                System.out.println("Invalid input");
                return;
            }
        }

        boolean check = false;
        UUID expertID=UUID.randomUUID();

        ArrayList<Expert> experts = ExpertRepository.getAllExperts();
        for (Expert expert:experts) {
            if(expert.checkAvailability(start, end)){
                check = true;
                expertID = expert.getId();
                break;
            }
        }

        if (!check) {
            System.out.println("No available experts at that time");
            return;
        }

        check = ((Client)InstitutionConsole.getCurrentUser()).checkDoubleBooking(start, end);

        if (check) {
            System.out.println("Cannot perform double booking");
            return;
        }

        Service service = new Service(type, start, end, details, ((Client) InstitutionConsole.getCurrentUser()).getId(), expertID);
        ServiceRepository.createService(service);
        System.out.println("Service created");
    }

}
