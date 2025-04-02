package SystemLogic;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Expert extends User{
    private String name;
    private int licenseNumber;
    private String contactInfo;
    private List<String> expertise;


    public Expert(String username, String password, String name, int licenseNumber, String contactInfo, List<String> expertise) {
        this.name = name;
        this.licenseNumber = licenseNumber;
        this.contactInfo = contactInfo;
        this.expertise = expertise;
        this.username=username;
        this.password=password;
    }

    public void displayExpertInfo() {
        System.out.println("\nExpert: " + name);
        System.out.println("License Number: " + licenseNumber);
        System.out.println("Contact Info: " + contactInfo);
        System.out.println("Expertise: " + String.join(", ", expertise));
    }

    public void offerAvailability(){
    }

    public void deleteAvailability(){
    }


    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }
}
