package DataManagement;

import SystemLogic.AreaOfExpertise;
import SystemLogic.Availability;
import SystemLogic.Expert;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ExpertRepository {
    private static final String FILE_PATH = "src/DataManagement/data/experts.csv";

    // READ all experts from CSV
    public static ArrayList<Expert> getAllExperts() {
        ArrayList<Expert> experts = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 7) {
                    UUID id = UUID.fromString(data[0]);
                    String username = data[1];
                    String password = data[2];
                    String name = data[3];
                    int license = Integer.parseInt(data[4]);
                    String contact = data[5];
                    String expertiseStr = data[6];
                    ArrayList<Availability> availabilities;
                    if(data.length==8){
                        String availabilityStr = data[7];
                        availabilities = Expert.parseAvailabilities(availabilityStr);
                    }else{
                        availabilities=new ArrayList<>();
                    }

                    List<AreaOfExpertise> expertise = Expert.parseExpertise(expertiseStr);

                    Expert expert = new Expert(id, username, password, name, license, contact, expertise, availabilities);
                    expert.setServices(ServiceRepository.getServiceByExpert(id));

                    experts.add(expert);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return experts;
    }

    // CREATE expert 
    public static boolean createExpert(Expert expert) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(expert.toString());
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // UPDATE expert
    public static boolean updateExpert(Expert updatedExpert) {
        List<Expert> experts = getAllExperts();
        boolean updated = false;

        for (int i = 0; i < experts.size(); i++) {
            if (experts.get(i).getId().equals(updatedExpert.getId())) {
                experts.set(i, updatedExpert);
                updated = true;
                break;
            }
        }

        if (updated) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
                for (Expert expert : experts) {
                    writer.write(expert.toString());
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return updated;
    }

    // DELETE expert
    public static boolean deleteExpert(UUID expertId) {
        List<Expert> experts = getAllExperts();
        boolean deleted = false;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Expert expert : experts) {
                if (!expert.getId().equals(expertId)) {
                    writer.write(expert.toString());
                    writer.newLine();
                } else {
                    deleted = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return deleted;
    }

    // GET expert by ID -- to associate an expert with service or area of expertise ..
    public static Expert getExpertById(UUID id) {
        List<Expert> experts = getAllExperts();
        for (Expert expert : experts) {
            if (expert.getId().equals(id)) {
                return expert;
            }
        }
        return null;
    }
}
