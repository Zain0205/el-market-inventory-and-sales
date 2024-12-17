package com.inventorymanagementsystem.entity;

public class Product {
    private int id;
    private String itemNumber;
    private String ItemGroup;
    private int quantity;
    private double price;
    private String status;  // Tambahkan atribut status
    private String supplier; // Tambahkan atribut supplier


    public Product(int id, String itemNumber, String itemGroup, int quantity, double price, String status, String supplier) {
        this.id = id;
        this.itemNumber = itemNumber;
        this.ItemGroup = itemGroup;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
        this.supplier = supplier;
    }

    // Getter and Setter untuk status dan supplier
    public String getStatus() {
        return status;
    }

    public String getSupplier() {
        return supplier;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getItemGroup() {
        return ItemGroup;
    }

    public void setItemGroup(String itemGroup) {
        ItemGroup = itemGroup;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
