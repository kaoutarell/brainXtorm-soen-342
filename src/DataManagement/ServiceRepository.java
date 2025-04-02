package DataManagement;

import SystemLogic.Service;
import SystemLogic.Client;

import java.io.*;
import java.util.ArrayList;
import java.text.SimpleDateFormat;

public class ServiceRepository {
    private static final String FILE_PATH = "src/DataManagement/data/services.csv";
    private static final String REQUESTS_FILE_PATH = "src/DataManagement/data/requests.csv";

    // Read all services from the CSV
    public static ArrayList<Service> getAllServices() {
        ArrayList<Service> services = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 5) { //id added 
                    Service service = new Service(
                        Service.ServiceType.valueOf(data[0].toUpperCase()),
                        new java.util.Date(Long.parseLong(data[1])),
                        Service.ServiceStatus.valueOf(data[2].toUpperCase()),
                        data[3]
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

    // Add a new service
    public static boolean createService(Service service) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(service.getServiceType() + "," + service.getServiceDate().getTime() + "," + service.getServiceStatus() + "," + service.getAddDetails());
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean clientRequestService(Client client, Service service) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(REQUESTS_FILE_PATH, true))) {
            // Format the date to a readable string (e.g., "2025-03-28 14:30:00")
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = sdf.format(service.getServiceDate());
            
            writer.write(service.getId() + "," +
                        client.getEmail() + "," + 
                        service.getServiceType() + "," + 
                        formattedDate + "," +  // Use the formatted date - otherwise it prints : 1743191271971 
                        service.getServiceStatus() + "," + 
                        service.getAddDetails()); 
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
}
