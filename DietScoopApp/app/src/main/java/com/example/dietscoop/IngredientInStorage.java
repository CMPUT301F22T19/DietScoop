package com.example.dietscoop;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/*
 * TODO: Need to add a custom equals() method for this wrapper class.
 */

/**
 * Location of ingredient enum.
 */
enum Location {
    //TODO: What are locations and categories? Ask Jakaria
    pantry,
    freezer,
    fridge;

    /**
     * Converts string to enum type for location.
     * @param name String name to convert to enum.
     * @return
     */
    public static Location stringToLocation(String name) {
        name = name.toUpperCase();

        if (name.equals("PANTRY")) {
            return pantry;
        } else if (name.equals("FREEZER")) {
            return freezer;
        } else if (name.equals("FRIDGE")) {
            return fridge;
        } else {
            // TODO: MAKE THIS THROW ERROR
            Log.i("null?", "LOCATION NOT RECOGNIZED");
            return null;
        }
    }
}

//enum IngredientCategory {
//    vegetable,
//    meat,
//    fruit;
//
//    public static IngredientCategory stringToCategory(String name) {
//        name = name.toUpperCase(Locale.ROOT);
//
//        if (name == "VEGETABLE") {
//            return vegetable;
//        } else if (name == "MEAT") {
//            return meat;
//        } else if (name == "FRUIT") {
//            return fruit;
//        } else {
//            // TODO: MAKE THIS THROW ERROR
//            return null;
//        }
//
//    }
//}

/**
 * Class representing an ingredient in storage.
 */
public class IngredientInStorage extends Ingredient{

    LocalDate bestBeforeDate;
    Location location;
    Category category;
    String id;
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
    @RequiresApi(api = Build.VERSION_CODES.O)
    public IngredientInStorage(String description, String measurementUnit, double amount,
                               int year, int month, int day, Location location, Category category){
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
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getFormattedBestBefore() {
        return bestBeforeDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    /**
     * Setter for best before date of ingredient in storage.
     * @param year year value to be set
     * @param month month value to be set
     * @param day day value to be set
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setBestBeforeDate(int year, int month, int day) {
        bestBeforeDate = LocalDate.of(year, month, day);
    }

    /**
     * Setter for category of ingredient in storage.
     * @param category
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Getter for ID of ingredient in storage
     * @return ID of ingredient in storage
     */
    public String getId(){return this.id;}

    /**
     * Setter for ID of ingredient in storage
     * @param id Value to be set.
     */
    public void setId(String id){this.id=id;}
}
