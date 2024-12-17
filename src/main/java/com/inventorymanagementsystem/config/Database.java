package com.inventorymanagementsystem.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    private static Database database;

    // Singleton Instance
    public static Database getInstance() {
        if (database == null) {
            database = new Database();
        }
        return database;
    }

    public Connection connectDB() {
        // Hardcoded database connection details
        String jdbcDriver = "com.mysql.cj.jdbc.Driver";
        String jdbcUrl = "jdbc:mysql://localhost:3306/el_market_db1"; // Ganti "test" dengan nama database Anda
        String username = "root"; // Ganti dengan username database Anda
        String password = "defanda2005"; // Ganti dengan password database Anda

        try {
            // Load JDBC Driver
            Class.forName(jdbcDriver);
            // Establish the connection
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Koneksi ke database berhasil!");
            return connection;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        // Test the connection
        Database db = Database.getInstance();
        Connection conn = db.connectDB();
        if (conn != null) {
            System.out.println("Koneksi berhasil boyyy!");
        } else {
            System.out.println("Koneksi gagal!");
        }
    }
}
