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
        while (true){
            displayMainMenu();
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