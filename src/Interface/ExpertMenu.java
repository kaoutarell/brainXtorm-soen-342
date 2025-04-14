package Interface;

import DataManagement.ClientRepository;
import DataManagement.ExpertRepository;
import DataManagement.ServiceRepository;
import SystemLogic.Availability;
import SystemLogic.Client;
import SystemLogic.Expert;
import SystemLogic.Service;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class ExpertMenu extends Menu{

    @Override
    public void displayMenuOptions() {
        System.out.println("1. see objects of interest");
        System.out.println("2. see auctions");
        System.out.println("3. see clients");
        System.out.println("4. see services");
        System.out.println("5. see availabilities");
        System.out.println("6. add availability");
        System.out.println("7. remove availability");
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
                seeClients();
                return 0;
            case "4":
                seeServices();
                return 0;
            case "5":
                seeAvailabilities();
                return 0;
            case "6":
                addAvailability();
                return 0;
            case "7":
                deleteAvailability();
                return 0;
            case "0":
                super.logout();
                return 0;
            default:
                break;
        }
        System.out.println("Invalid input");
        return 1;
    }

    private void seeClients(){
        ArrayList<Client> clients = ClientRepository.getAllClients();
        for (Client client:clients){
            client.displayClientInfo();
        }
    }

    private void seeServices(){
        ArrayList<Service> services = ServiceRepository.getServiceByExpert(((Expert)InstitutionConsole.getCurrentUser()).getId());
        for(Service service:services){
            service.displayServiceDetails();
        }
    }

    private void seeAvailabilities(){
        ArrayList<Availability> availabilities = ((Expert)InstitutionConsole.getCurrentUser()).getAvailabilities();
        for (Availability availability: availabilities){
            System.out.println(availability.toString());
        }
    }

    private void addAvailability(){
        Date startDate;
        Date endDate;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            System.out.println("Enter the start time (yyyy-MM-dd HH:mm:ss)");
            startDate = formatter.parse(scan.nextLine());
            System.out.println("Enter the end time (yyyy-MM-dd HH:mm:ss)");
            endDate = formatter.parse(scan.nextLine());
            ((Expert)InstitutionConsole.getCurrentUser()).addAvailability(new Availability(startDate, endDate));
            ExpertRepository.updateExpert((Expert)InstitutionConsole.getCurrentUser());
        }catch(Exception e){
            System.out.println("Invalid input");
            return;
        }
    }

    private void deleteAvailability(){
        ArrayList<Availability> availabilities = ((Expert)InstitutionConsole.getCurrentUser()).getAvailabilities();
        Scanner scan = new Scanner(System.in);
        for (int i=0;i< availabilities.size();i++){
            System.out.println(i+": "+availabilities.get(i));
        }
        try{
            System.out.println("Enter the availability to delete");
            availabilities.remove(availabilities.get(Integer.parseInt(scan.nextLine())));
        }catch (Exception e){
            System.out.println("Invalid input");
        }
    }

}
