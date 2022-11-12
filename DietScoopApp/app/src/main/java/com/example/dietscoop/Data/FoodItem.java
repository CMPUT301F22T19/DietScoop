package com.example.dietscoop.Data;

import java.io.Serializable;

public abstract class FoodItem implements Serializable {
/**
 * The abstract class FoodItem abstracts the shared methods of ingredient and recipe
 */
    protected String description;

    /**
     * Getter method for FoodItem description
     * @return description of food item
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter method for FoodItem description
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

}
