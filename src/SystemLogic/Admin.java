package SystemLogic;

import DataManagement.ClientRepository;

import java.util.ArrayList;
import java.util.Scanner;

public class Admin extends User {

    public Admin(){
        this.username="admin";
        this.password="admin";
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public void approveSignUp(){
        ArrayList<Client> pendingClients = ClientRepository.getClientByStatus("pending");
        Client chosenClient=null;
        int input;
        String choice="";

        System.out.println("Select a client");
        int i=0;
        for (Client client: pendingClients) {
            System.out.println(i+": "+client.getUsername());
        }
        Scanner scan = new Scanner(System.in);
        try{
            input = Integer.parseInt(scan.nextLine());
            if (input>=pendingClients.size() || input<0){
                System.out.println("Invalid input");
                scan.close();
                approveSignUp();
                return;
            }else{
                pendingClients.get(input).displayClientInfo();
            }
        }catch(Exception e){
            System.out.println("Invalid input");
            scan.close();
            approveSignUp();
            return;
        }
        chosenClient=pendingClients.get(input);

        while(choice==""){
            System.out.println("1. Approve");
            System.out.println("2. Deny");
            System.out.println("3. Go Back");
            choice = scan.nextLine();

            switch (choice){
                case "1":
                    chosenClient.approve();
                    break;
                case "2":
                    ClientRepository.deleteClient(chosenClient);
                    break;
                case "3":
                    approveSignUp();
                    return;
                default:
                    choice="";
                    System.out.println("Invalid Input");
            }
        }

    }
    
}
