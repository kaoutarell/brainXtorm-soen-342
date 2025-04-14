package SystemLogic;

import DataManagement.ExpertRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Expert extends User {
    private String name;
    private int licenseNumber;
    private String contactInfo;
    private List<AreaOfExpertise> expertise;
    private UUID id;

    public Expert(String username, String password, String name, int licenseNumber, String contactInfo, List<AreaOfExpertise> expertise) {
        this.id = UUID.randomUUID(); // generate unique ID
        this.name = name;
        this.licenseNumber = licenseNumber;
        this.contactInfo = contactInfo;
        this.expertise = expertise;
        this.username = username; //email
        this.password = password;
    }

    // Constructor with UUID (used when reading from CSV) -- for expert and client
    public Expert(UUID id, String username, String password, String name, int licenseNumber, String contactInfo, List<AreaOfExpertise> expertise) {
        this.id = id;
        this.name = name;
        this.licenseNumber = licenseNumber;
        this.contactInfo = contactInfo;
        this.expertise = expertise;
        this.username = username;
        this.password = password;
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

    public void offerAvailability() {
        
    }

    public void deleteAvailability() {
        
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
        return id + "," + username + "," + password + "," + name + "," + licenseNumber + "," + contactInfo + "," + expertiseStr;
    }
    
    public static List<AreaOfExpertise> parseExpertise(String expertiseStr) {
        return List.of(expertiseStr.split(";")).stream()
                .map(AreaOfExpertise::fromString)
                .collect(Collectors.toList());
    }
}
