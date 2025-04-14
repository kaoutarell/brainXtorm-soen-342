package SystemLogic;

import DataManagement.ExpertRepository;
import DataManagement.ServiceRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Expert extends User {
    private String name;
    private int licenseNumber;
    private String contactInfo;
    private List<AreaOfExpertise> expertise;
    private ArrayList<Availability> availabilities;
    private ArrayList<Service> services;

    public ArrayList<Service> getServices() {
        return services;
    }

    public void setServices(ArrayList<Service> services){
        this.services = services;
    }

    public void setAvailabilities(ArrayList<Availability> availabilities) {
        this.availabilities = availabilities;
    }

    public ArrayList<Availability> getAvailabilities() {
        return availabilities;
    }



    private UUID id;

    public Expert(String username, String password, String name, int licenseNumber, String contactInfo, List<AreaOfExpertise> expertise, ArrayList<Availability> availabilities) {
        this.id = UUID.randomUUID(); // generate unique ID
        this.name = name;
        this.licenseNumber = licenseNumber;
        this.contactInfo = contactInfo;
        this.expertise = expertise;
        this.username = username; //email
        this.password = password;
        this.availabilities = availabilities;
        this.services = new ArrayList<>();
    }

    // Constructor with UUID (used when reading from CSV) -- for expert and client
    public Expert(UUID id, String username, String password, String name, int licenseNumber, String contactInfo, List<AreaOfExpertise> expertise, ArrayList<Availability> availabilities) {
        this.id = id;
        this.name = name;
        this.licenseNumber = licenseNumber;
        this.contactInfo = contactInfo;
        this.expertise = expertise;
        this.username = username;
        this.password = password;
        this.availabilities = availabilities;
        this.services = ServiceRepository.getServiceByExpert(id);

    }

    public void displayExpertInfo() {
        System.out.println("\nExpert: " + name);
        System.out.println("License Number: " + licenseNumber);
        System.out.println("Contact Info: " + contactInfo);
        System.out.print("Expertise: ");
        for (AreaOfExpertise area : expertise) {
            System.out.println("- " + area.getName() + " (" + area.getDescription() + ")");
        }
    }

    public boolean add(){
        return ExpertRepository.createExpert(this);
    }

    public boolean delete(){
        return ExpertRepository.deleteExpert(this.id);
    }

    public boolean update(String username, String password, String name, int licenseNumber, String contactInfo, List<AreaOfExpertise> expertise){
        this.name = name;
        this.licenseNumber = licenseNumber;
        this.contactInfo = contactInfo;
        this.expertise = expertise;
        this.username=username;
        this.password=password;
        return ExpertRepository.updateExpert(this);
    }


    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    // Getters and Setters
    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getLicenseNumber() { return licenseNumber; }

    public void setLicenseNumber(int licenseNumber) { this.licenseNumber = licenseNumber; }

    public String getContactInfo() { return contactInfo; }

    public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }

    public List<AreaOfExpertise> getExpertise() {
        return expertise;
    }

    public void setExpertise(List<AreaOfExpertise> expertise) {
        this.expertise = expertise;
    }

    public UUID getId() { return id; }

    public void setId(UUID id) { this.id = id; }

    @Override
    public String toString() {
        String expertiseStr = expertise.stream()
                .map(AreaOfExpertise::toString)
                .collect(Collectors.joining(";"));
        String availabilityStr = availabilities.stream()
                .map(Availability::toString)
                .collect(Collectors.joining(";"));
        return id + "," + username + "," + password + "," + name + "," + licenseNumber + "," + contactInfo + "," + expertiseStr + "," + availabilityStr;
    }
    
    public static List<AreaOfExpertise> parseExpertise(String expertiseStr) {
        return List.of(expertiseStr.split(";")).stream()
                .map(AreaOfExpertise::fromString)
                .collect(Collectors.toList());
    }

    public static ArrayList<Availability> parseAvailabilities(String availabilities){
        return new ArrayList<>(List.of(availabilities.split(";")).stream()
                .map(Availability::fromString)
                .collect(Collectors.toList()));
    }

    public boolean checkAvailability(Date start, Date end){
        for (Availability availability: availabilities) {
            if((availability.getStart().before(start)||availability.getStart().equals(start)) && (end.before(availability.getEnd())||availability.getEnd().equals(end))){
                availabilities.remove(availability);
                ExpertRepository.updateExpert(this);
                return true;
            }
        }
        return false;
    }

    public void addAvailability(Availability a){
        this.availabilities.add(a);
    }
}
