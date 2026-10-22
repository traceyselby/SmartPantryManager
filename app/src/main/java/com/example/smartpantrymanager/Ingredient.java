package com.example.smartpantrymanager;

public class Ingredient {

    // Ingredient attributes
    private int id;
    private String name;
    private double quantity;
    private String unit;
    private String expiryDate;

    // Constructor for a new ingredient
    public Ingredient(String name, double quantity,
                      String unit, String expiryDate) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.expiryDate = expiryDate;
    }

    // Constructor for an existing ingredient
    public Ingredient(int id, String name, double quantity,
                      String unit, String expiryDate) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.expiryDate = expiryDate;
    }

    // Get ingredient ID
    public int getId() {
        return id;
    }

    // Set ingredient ID
    public void setId(int id) {
        this.id = id;
    }

    // Get ingredient name
    public String getName() {
        return name;
    }

    // Set ingredient name
    public void setName(String name) {
        this.name = name;
    }

    // Get ingredient quantity
    public double getQuantity() {
        return quantity;
    }

    // Set ingredient quantity
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    // Get ingredient unit
    public String getUnit() {
        return unit;
    }

    // Set ingredient unit
    public void setUnit(String unit) {
        this.unit = unit;
    }

    // Get expiry date
    public String getExpiryDate() {
        return expiryDate;
    }

    // Set expiry date
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}
