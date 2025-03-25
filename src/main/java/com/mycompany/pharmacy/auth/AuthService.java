package com.mycompany.pharmacy.auth;

import com.mycompany.pharmacy.model.User;
import java.io.*;
import java.util.HashMap;

public class AuthService {

    public String login(String email, String password) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/mycompany/pharmacy/database/users.txt"));
        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            if (data.length >= 3) {
                String emailf = data[0];
                String passwordf = data[1];
                String rolef = data[2];

                if (emailf.equals(email)) {
                    if (passwordf.equals(password)) {
                        br.close();
                        System.out.println("Welcome " + rolef);
                        return rolef;
                    } else {
                        br.close();
                        System.out.println("Incorrect password");
                        return null;
                    }
                }
            }
        }
        br.close();
        System.out.println("This user does not exist");
        return null;
    }

    public void register(User user, String role) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/java/com/mycompany/pharmacy/database/users.txt", true)); // Append mode
        bw.write(user.getEmail() + "," + user.getPassword() + "," + role);
        bw.newLine();
        bw.close();
        System.out.println("User registered successfully as " + role);
    }

    public void logout(User user) {
        System.out.println(user.getEmail() + " logged out successfully");
    }
    
    public void resetPassword(String email)
    {
        
    }
}
