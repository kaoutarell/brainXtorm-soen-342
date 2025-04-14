package DataManagement;

import SystemLogic.Service;
import SystemLogic.Client;

import java.io.*;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.UUID;

public class ServiceRepository {
    private static final String FILE_PATH = "src/DataManagement/data/services.csv";

    // Read all services from the CSV
    public static ArrayList<Service> getAllServices() {
        ArrayList<Service> services = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 7) { //id added
                    Service service = new Service(
                        Service.ServiceType.valueOf(data[1].toUpperCase()),
                        new java.util.Date(Long.parseLong(data[2])),
                        new java.util.Date(Long.parseLong(data[3])),
                        data[4],
                            UUID.fromString(data[5]),
                            UUID.fromString(data[6])
                    );
                    // Set the id manually 
                    service.setId(data[0]);
                    services.add(service);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return services;
    }

    public static ArrayList<Service> getServiceByExpert(UUID id){
        ArrayList<Service> services = getAllServices();
        ArrayList<Service> selected = new ArrayList<>();
        for (Service service:services) {
            if (service.getExpertId().equals(id)){
                selected.add(service);
            }
        }
        return selected;
    }

    public static ArrayList<Service> getServiceByClient(UUID id){
        ArrayList<Service> services = getAllServices();
        ArrayList<Service> selected = new ArrayList<>();
        for (Service service:services) {
            if (service.getClientId().equals(id)){
                selected.add(service);
            }
        }
        return selected;
    }

    // Add a new service
    public static boolean createService(Service service) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(service.getId() + "," + service.getServiceType() + "," + service.getStartTime().getTime() + "," + service.getEndTime().getTime() + "," + service.getAddDetails() + "," + service.getClientId() + "," + service.getExpertId());
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
}
