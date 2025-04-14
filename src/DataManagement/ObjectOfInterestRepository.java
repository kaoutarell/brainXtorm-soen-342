package DataManagement;

import SystemLogic.ObjectOfInterest;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ObjectOfInterestRepository {

    private static final String FILE_PATH = "src/DataManagement/data/objects.csv";

    // hardcoded for the tests only
    public static ArrayList<ObjectOfInterest> getListOfObjects(){
        //TODO
        ArrayList<ObjectOfInterest> objects = new ArrayList<>();
        objects.add(new ObjectOfInterest(false, "really cool vase"));
        objects.add(new ObjectOfInterest(true, "an old yoyo"));
        objects.add(new ObjectOfInterest(true, "Toyota AE86"));
        objects.add(new ObjectOfInterest(false, "statue of a man"));
        return objects;
    }

    //CREATE
    public static void saveObject(ObjectOfInterest object) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(object.toCSV());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving object: " + e.getMessage());
        }
    }

    //READ
    public static List<ObjectOfInterest> getAllObjects() {
        List<ObjectOfInterest> objects = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) return objects;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                objects.add(ObjectOfInterest.fromCSV(line));
            }
        } catch (IOException e) {
            System.out.println("Error reading objects: " + e.getMessage());
        }

        return objects;
    }

    //GET BY ID
    public static ObjectOfInterest getObjectById(UUID id) {
        for (ObjectOfInterest obj : getAllObjects()) {
            if (obj.getId().equals(id)) return obj;
        }
        return null;
    }

    //DELETE
    public static void deleteObject(ObjectOfInterest object) {
        List<ObjectOfInterest> objects = getAllObjects();
        objects.removeIf(obj -> obj.getId().equals(object.getId()));
        overwriteFile(objects);
    }

    //UPDATE
    public static void updateObject(ObjectOfInterest updatedObject) {
        List<ObjectOfInterest> objects = getAllObjects();
        for (int i = 0; i < objects.size(); i++) {
            if (objects.get(i).getId().equals(updatedObject.getId())) {
                objects.set(i, updatedObject);
                break;
            }
        }
        overwriteFile(objects);
    }

    private static void overwriteFile(List<ObjectOfInterest> objects) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (ObjectOfInterest obj : objects) {
                writer.write(obj.toCSV());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing objects: " + e.getMessage());
        }
    }

}
