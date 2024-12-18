package com.inventorymanagementsystem.entity;

public class User {
    public static String name;
    private int id;
    private String username;
    private String password;

    // Constructor
    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}