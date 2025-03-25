package com.mycompany.pharmacy.model;

import com.mycompany.pharmacy.auth.AuthService;
import java.io.IOException;


public abstract class User {
    protected String id;
    
    protected String name;
    
    protected String email;
    
    protected String phone;
    
    protected String password;

    public User() {}
    
    public void login() throws IOException
    {
        AuthService aslogin = new AuthService();
        
        aslogin.login(email, password);
    }
    
    public void logout()
    {
        AuthService aslogout = new AuthService();
        
        aslogout.logout(this);
    }
    
    public void updateProfile(String name, String email, String phone)
    {
        this.name = name;
        
        this.email = email;
        
        this.phone = phone;
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
