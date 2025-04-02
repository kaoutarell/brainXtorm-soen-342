package Interface;
import java.util.Scanner;

import SystemLogic.Admin;
import SystemLogic.Client;
import SystemLogic.Expert;
import SystemLogic.User;



public class InstitutionConsole{

    private static Menu menu = new ClientMenu();

    public static void main(String[] args) {
        while (true){
            displayMainMenu();
        }
    }

    private static User currentUser;

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