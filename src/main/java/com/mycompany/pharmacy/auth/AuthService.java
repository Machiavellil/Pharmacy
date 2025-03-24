/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pharmacy.auth;
import com.mycompany.pharmacy.model.User;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author young
 */
public class AuthService {
    public void login(String email, String password) throws IOException
    {
        String role = "";
        
        HashMap<String, String> loginf = new HashMap<>();
        
        BufferedReader br = new BufferedReader(new FileReader("users.txt"));
        
        String line;
        
        while ((line = br.readLine()) != null) {
            
            String[] data = line.split(",");
            
            String emailf = data[0];
            
            String passwordf = data[1];
            
            String rolef = data[2];
            
            role = rolef;
            
            if (emailf.equals(email))
            {
                loginf.put(emailf, passwordf);
                
                break;
            }
            // Process the data
        }
        br.close();
        
        if (loginf.isEmpty())
        {
            System.out.println("This user does not exist");
        }
        else if (loginf.get(email).equals(password))
        {
            System.out.println("Welcome " + role);
        }
    }
    
    public void register(User user) throws IOException
    {
        BufferedWriter bw = new BufferedWriter(new FileWriter("users.txt"));
        
        bw.write(user.getEmail());
        
        bw.write(',');
        
        bw.write(user.getPassword());
        
        bw.close();
    }
    
    public void logout(User user)
    {
        System.out.println("Logged out successfully");
    }
    
    public void resetPassword(String email)
    {
        
    }
}
