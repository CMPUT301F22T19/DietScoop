package com.example.dietscoop;

import java.io.Serializable;
import java.util.Locale;

enum Category implements Serializable {
/**
 *  Category for different ingredients
 */
enum Category {
    vegetable,
    meat,
    fruit;

    /**
     * Method change string to Enum class
     * @param name String to change to enum
     * @return Enum value of category
     */
    public static Category stringToCategory(String name) {
        name = name.toUpperCase(Locale.ROOT);

        if (name.equals("VEGETABLE")) {
            return vegetable;
        } else if (name.equals("MEAT")) {
            return meat;
        } else if (name.equals("FRUIT")) {
            return fruit;
        } else {
            // TODO: MAKE THIS THROW ERROR
            return null;
        }

    }
}

/**
 * Ingredient abstract class to be inherented by ingredient and ingredient in storage
 */
public abstract class Ingredient extends FoodItem implements Serializable {
    private double amount;
    private String measurementUnit;
    private Category category;

    /**
     * Constructor for Ingredient
     * @param description Description of ingredient
     * @param measurementUnit Measurement unit of ingredient
     * @param amount Amount of ingredient
     * @param category Category of ingredient.
     */
    public Ingredient(String description, String measurementUnit, double amount, Category category) {
        this.description = description;
        this.amount = amount;
        this.measurementUnit = measurementUnit;
        this.category = category;

    }

    /**
     * getter for ingredient amount
     * @return ingredient amount
     */
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(String measurementUnit) {
        this.measurementUnit = measurementUnit;
    }
    public Category getCategory() {
        return category;
    }
    public String getCategoryName() {return category.name(); }

}
