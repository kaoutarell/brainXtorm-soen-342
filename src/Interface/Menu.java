package Interface;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import DataManagement.AuctionHouseRepository;
import DataManagement.AuctionRepository;
import DataManagement.ClientRepository;
import DataManagement.ExpertRepository;
import DataManagement.ObjectOfInterestRepository;
import SystemLogic.Admin;
import SystemLogic.Auction;
import SystemLogic.AuctionHouse;
import SystemLogic.Client;
import SystemLogic.Expert;
import SystemLogic.ObjectOfInterest;
import SystemLogic.User;

public abstract class Menu {
    Scanner scan = new Scanner(System.in);

    public abstract void displayMenuOptions();
    public abstract int handleMenuInput(String input);

    protected void seeObjects(){
        ArrayList<ObjectOfInterest> objects = new ArrayList<>(ObjectOfInterestRepository.getAllObjects());
        System.out.println("----------------------------Objects----------------------------");
        for (ObjectOfInterest objectOfInterest : objects) {
            System.out.println(objectOfInterest);
        }
        System.out.println("---------------------------------------------------------------");
    }

    protected void seeAuctions(){
        List<AuctionHouse> houses = AuctionHouseRepository.getAllHouses();
        ArrayList<Auction> auctions = new ArrayList<>(AuctionRepository.getAllAuctions(houses));
        //ArrayList<Auction> auctions = new ArrayList<>(AuctionRepository.getAllAuctions()); -- doesn't work anymore since an auction belongs to an auction house
        System.out.println("----------------------------Auctions----------------------------");
        for (Auction auction : auctions) {
            System.out.println(auction);
        }
        System.out.println("----------------------------------------------------------------");
    }

    protected void login(){
        String username="";
        String password="";
        User currentUser=null;

        ArrayList<Client> clients = new ArrayList<>(ClientRepository.getAllClients());

        ArrayList<Expert> experts = ExpertRepository.getAllExperts();

        while(currentUser==null){
            System.out.println("Enter your username: ");
            username = scan.nextLine();

            Admin admin = new Admin();

            if(Objects.equals(admin.getUsername(), username)){
                currentUser=admin;
                break;
            }

            for (Expert expert : experts) {
                if (Objects.equals(expert.getUsername(), username)){
                    currentUser=expert;
                    break;
                }
            }

            for (Client client : clients) {
                if (Objects.equals(client.getUsername(), username)){
                    if(Objects.equals(client.getStatus(), "Approved")){
                        currentUser=client;
                    }

                    break;
                }
            }
            if (currentUser==null){
                System.out.println("invalid username");
            }

        }

        boolean authenticated = false;

        while(!authenticated){
            System.out.println("Enter your password: ");
            password = scan.nextLine();

            if(Objects.equals(currentUser.getPassword(), password)){
                InstitutionConsole.setCurrentUser(currentUser);
                authenticated = true;
            }
        }
        
        

    }

    protected void logout(){
        InstitutionConsole.setCurrentUser(null);
    }

    protected void signUp(){
        System.out.println("Enter username: ");
        String username = scan.nextLine();
        System.out.println("Enter email: ");
        String email = scan.nextLine();
        System.out.println("Enter password: ");
        String password = scan.nextLine();
        System.out.println("Enter affiliation: ");
        String affiliation = scan.nextLine();
        System.out.println("Enter contact info: ");
        String contactInfo = scan.nextLine();
        System.out.println("Enter intent: ");
        String intent = scan.nextLine();

        Client client = new Client(username, password, email, affiliation, contactInfo, intent);

        client.signUp();
    }
}
