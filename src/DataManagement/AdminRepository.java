package DataManagement;

import SystemLogic.Admin;

import java.io.*;

public class AdminRepository {
    private static final String FILE_PATH = "src/DataManagement/data/admin.csv";

    // Authenticate the admin
    public static Admin authenticate(String username, String password) {
        Admin storedAdmin = loadAdmin();
        if (storedAdmin != null &&
            storedAdmin.getUsername().equals(username) &&
            storedAdmin.getPassword().equals(password)) {
            return storedAdmin;
        }
        return null;
    }

    // Load the admin from CSV
    public static Admin loadAdmin() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line = reader.readLine();
            if (line != null) {
                String[] data = line.split(",");
                if (data.length == 2) {
                    Admin admin = new Admin();
                    admin.setUsername(data[0]);
                    admin.setPassword(data[1]);
                    return admin;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Initialize admin CSV with default admin if it doesn't exist -- requirements : there's only 1 admin created by the system
    public static void initializeAdminFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs(); // create directories if needed
                file.createNewFile();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
                    writer.write("admin,admin");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
