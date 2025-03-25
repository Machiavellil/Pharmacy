package com.mycompany.pharmacy.model;

import com.mycompany.pharmacy.auth.AuthService;
import java.io.IOException;


public abstract class User {

    protected String email;

    protected String password;

    public User() {}
    

    
    public void updateProfile(String name, String email)
    {
        
        this.email = email;
        
    }
    
    public String getEmail()
    {
        return email;
    }
    
    public String getPassword()
    {
        return password;
    }
}
