package com.example.dietscoop.Data;

import java.io.Serializable;

public abstract class FoodItem implements Serializable {
/**
 * The abstract class FoodItem abstracts the shared methods of ingredient and recipe
 */
    protected String description;
    protected String id;
    protected String imageString;

    public String getImageString() {
        return imageString;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }

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

    /**
     * Getter for ID of ingredient in storage
     * @return ID of ingredient in storage
     */
    public String getId() {return this.id;}

    /**
     * Setter for ID of ingredient in storage
     * @param id Value to be set.
     */
    public void setId(String id){this.id=id;}
}
