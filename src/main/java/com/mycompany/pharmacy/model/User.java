package com.mycompany.pharmacy.model;

public abstract class User {
    protected String id;
    protected String name;
    protected String email;
    protected String phone;
    protected String password;

    public User(String id, String name, String email, String phone, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public abstract boolean login(String email, String password);

    public void logout() {
        System.out.println(name + " logged out.");
    }

    public void updateProfile(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        System.out.println("Profile updated successfully.");
    }

    
    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
}
