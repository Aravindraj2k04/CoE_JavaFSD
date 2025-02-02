import java.io.*;
import java.util.*;

class User {
    private String name;
    private String email;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    @Override
    public String toString() {
        return name + "," + email;
    }

    public static User fromString(String data) {
        String[] parts = data.split(",");
        if (parts.length == 2) {
            return new User(parts[0], parts[1]);
        }
        return null;
    }
}

public class UserManager {
    private List<User> users = new ArrayList<>();

    public void addUser(String name, String email) {
        users.add(new User(name, email));
    }

    public void saveUsersToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (User user : users) {
                writer.write(user.toString());
                writer.newLine();
            }
            System.out.println("Users saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving users to file: " + e.getMessage());
        }
    }

    public void loadUsersFromFile(String filename) {
        users.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = User.fromString(line);
                if (user != null) {
                    users.add(user);
                }
            }
            System.out.println("Users loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading users from file: " + e.getMessage());
        }
    }

    public void displayUsers() {
        for (User user : users) {
            System.out.println("Name: " + user.toString().split(",")[0] + ", Email: " + user.toString().split(",")[1]);
        }
    }

    public static void main(String[] args) {
        UserManager userManager = new UserManager();
        userManager.addUser("Alice", "alice@example.com");
        userManager.addUser("Bob", "bob@example.com");
        
        String filename = "users.txt";
        userManager.saveUsersToFile(filename);
        
        userManager.loadUsersFromFile(filename);
        userManager.displayUsers();
    }
}
