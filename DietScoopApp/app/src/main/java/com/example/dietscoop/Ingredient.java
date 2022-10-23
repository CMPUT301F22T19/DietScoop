package com.example.dietscoop;




public class Ingredient extends FoodItem{

    int amount;
    String measurementUnit;

    public Ingredient(String description, String measurementUnit, int amount) {
        this.description = description;
        this.amount = amount;
        this.measurementUnit = measurementUnit;

    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(String measurementUnit) {
        this.measurementUnit = measurementUnit;
    }
}
