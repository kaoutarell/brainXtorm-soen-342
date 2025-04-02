package Interface;

import SystemLogic.Client;
import SystemLogic.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

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
            System.out.println("3. Visit assistance service");
            String input = scan.nextLine();
            switch (input) {
                case "1" -> type = Service.ServiceType.CONSULTATION;
                case "2" -> type = Service.ServiceType.AUCTION_ASSISTANCE;
                case "3" -> type = Service.ServiceType.VISIT_ASSISTANCE;
                default -> System.out.println("Invalid input");
            }
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate=null;

        while (startDate == null) {
            System.out.println("Please enter a date and time (format: yyyy-MM-dd HH:mm:ss): ");
            String input = scan.nextLine();
            try {
                startDate = dateFormat.parse(input);
            } catch (Exception e) {
                System.out.println("Invalid date/time format. Please try again.");
            }
        }

        System.out.println("Enter any additional details you like");
        String details = scan.nextLine();

        Service service = new Service(type, startDate, Service.ServiceStatus.PENDING, details);

        ((Client) InstitutionConsole.getCurrentUser()).requestService(service);
    }

}
