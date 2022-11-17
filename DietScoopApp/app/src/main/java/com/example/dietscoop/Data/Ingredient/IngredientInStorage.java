// DONE UML

package com.example.dietscoop.Data.Ingredient;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/*
 * TODO: Need to add a custom equals() method for this wrapper class.
 */

/**
 * Class representing an ingredient in storage.
 */
public class IngredientInStorage extends Ingredient {

    LocalDate bestBeforeDate;
    Location location;
    IngredientCategory category;
    //TODO: change year,month,day to Calendar pls pls

    /**
     * Constructor for ingredient in storage.
     * @param description Description of ingredient in storage.
     * @param measurementUnit Measurement unit of ingredient in storage.
     * @param amount Amount of ingredient in storage.
     * @param year Best before year of ingredient in storage.
     * @param month Best before month of ingredient in storage.
     * @param day Best before day of ingredient in storage.
     * @param location Location of ingredient in storage.
     * @param category Category of ingredient in storage.
     */
    public IngredientInStorage(String description, String measurementUnit, double amount,
                               int year, int month, int day, Location location, IngredientCategory category){
        super(description,measurementUnit,amount, category);
        bestBeforeDate = LocalDate.of(year, month, day);
        this.location = location;
        this.category = category;
    }

    /**
     * getter for location
     * @return Location of ingredient in storage
     */
    public Location getLocation() {
        return location;
    }

    /**
     * getter for location in string form
     * @return String location of ingredient in storage
     */
    public String getLocationName() {return location.name(); } // ADDED

    /**
     * setter for location of ingredient in storage.
     * @param location Value to be set
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * getter for best before date
     * @return Best before date for ingredient in storage.
     */
    public LocalDate getBestBeforeDate() {
        return bestBeforeDate;
    }

    /**
     * Getter for formatted best before date
     * @return Best before date formatted via LOCAL_DATE
     */
    public String getFormattedBestBefore() {
        return bestBeforeDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    /**
     * Setter for best before date of ingredient in storage.
     * @param year year value to be set
     * @param month month value to be set
     * @param day day value to be set
     */
    public void setBestBeforeDate(int year, int month, int day) {
        bestBeforeDate = LocalDate.of(year, month, day);
    }

    /**
     * Setter for category of ingredient in storage.
     * @param category Category to set for this ingredient.
     */
    public void setCategory(IngredientCategory category) {
        this.category = category;
    }


}
