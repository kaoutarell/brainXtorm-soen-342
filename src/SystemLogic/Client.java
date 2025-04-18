package SystemLogic;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import DataManagement.ClientRepository;
import DataManagement.ServiceRepository;

public class Client extends User {
    private UUID id;  // New field for UUID
    private String email;
    private String affiliation;
    private String contactInfo;
    private String intent;
    private String status;
    private ArrayList<Service> services;

    public Client(String username, String password, String email, String affiliation, String contactInfo, String intent) {
        this.id = UUID.randomUUID();  // id added
        this.email = email;
        this.affiliation = affiliation;
        this.contactInfo = contactInfo;
        this.intent = intent;
        this.status = "Pending"; 
        this.username = username;
        this.password = password;
        this.services=new ArrayList<>();
    }

    public Client(UUID id, String username, String password, String email, String affiliation, String contactInfo, String intent) {
        this.id = id;  // id added
        this.email = email;
        this.affiliation = affiliation;
        this.contactInfo = contactInfo;
        this.intent = intent;
        this.status = "Pending";
        this.username = username;
        this.password = password;
        this.services=ServiceRepository.getServiceByClient(id);
    }

    public void approve() {
        status = "Approved";
        ClientRepository.updateClient(this);
    }

    public void displayClientInfo() {
        System.out.println("\nClient ID: " + id);
        System.out.println("Client: " + email);
        System.out.println("Affiliation: " + affiliation);
        System.out.println("Contact Info: " + contactInfo);
        System.out.println("Intent: " + intent);
        System.out.println("Status: " + status);
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public String getEmail() {
        return email;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public String getIntent() {
        return intent;
    }

    public String getStatus() {
        return status;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return id + "," + username + "," + password + "," + email + "," + affiliation + "," + contactInfo + "," + intent + "," + status;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void signUp(){
        ClientRepository.createClient(this);
    }

    public boolean checkDoubleBooking(Date start, Date end){
        for(Service service:services){
            if(start.before(service.getStartTime())&&service.getStartTime().before(end)||service.getEndTime().before(end)&&start.before(service.getEndTime())){
                return true;
            }
        }
        return false;
    }
}
