package Interface;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import DataManagement.ExpertRepository;
import SystemLogic.Admin;
import SystemLogic.AreaOfExpertise;
import SystemLogic.Client;
import SystemLogic.Expert;
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