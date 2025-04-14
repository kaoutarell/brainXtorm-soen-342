package SystemLogic;


import DataManagement.ExpertRepository;

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

    @Override
    public String toString() {
        return("Expert: " + name +"\nLicense Number: " + licenseNumber+"\nContact Info: " + contactInfo+"\nExpertise: " + String.join(", ", expertise));
    }

    public void offerAvailability(){
    }

    public void deleteAvailability(){
    }

    public boolean add(){
        return ExpertRepository.addExpert(this);
    }

    public boolean delete(){
        return ExpertRepository.deleteExpert(this);
    }

    public boolean update(String username, String password, String name, int licenseNumber, String contactInfo, List<String> expertise){
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
}
