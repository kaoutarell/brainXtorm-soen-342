package DataManagement;

import SystemLogic.Client;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClientRepository {
    private static final String FILE_PATH = "src/DataManagement/data/clients.csv";

    // Read all clients from the CSV
    public static ArrayList<Client> getAllClients() {
        ArrayList<Client> clients = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 8) {  // Ensure it matches the number of fields in Client (including UUID)
                    Client client = new Client(UUID.fromString(data[0]),data[1], data[2], data[3], data[4], data[5], data[6]);
                    client.setStatus(data[7]);  // Status wasn't accessible, so added setter
                    clients.add(client);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clients;
    }

    // Get clients by status
    public static ArrayList<Client> getClientByStatus(String status) {
        ArrayList<Client> clients = getAllClients();
        ArrayList<Client> filteredClients = new ArrayList<>();
        for (Client client : clients) {
            if (client.getStatus().equalsIgnoreCase(status)) {
                filteredClients.add(client);
            }
        }
        return filteredClients;
    }

    // Add a new client
    public static boolean createClient(Client client) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(client.toString());  // UUID
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Update an existing client
    public static boolean updateClient(Client updatedClient) {
        List<Client> clients = getAllClients(); // Get all clients
        boolean updated = false;

        // Find and update the client
        for (int i = 0; i < clients.size(); i++) {
            Client client = clients.get(i);
            if (client.getEmail().equals(updatedClient.getEmail())) {
                clients.set(i, updatedClient); // Update client in the list
                updated = true;
                break; // Exit loop once client is updated
            }
        }

        // Only rewrite the file if the client was updated
        if (updated) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
                for (Client client : clients) {
                    writer.write(client.toString()); // Write each client in the list to the file
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return updated; // update was successful
    }

    // Delete a client
    public static boolean deleteClient(Client client) {
        List<Client> clients = getAllClients();
        boolean deleted = false;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Client currentClient : clients) {
                if (!currentClient.getEmail().equals(client.getEmail())) {
                    writer.write(currentClient.toString());
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
}
