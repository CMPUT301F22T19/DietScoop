package com.example.dietscoop;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;

/**
 * TODO: Need to add a custom equals() method for this wrapper class.
 */

//TODO: What are locations and categories? Ask Jakaria
enum Location {
    pantry,
    freezer,
    fridge;

    public static Location stringToLocation(String name) {
        name = name.toUpperCase(Locale.ROOT);

        if (name == "PANTRY") {
            return pantry;
        } else if (name == "FREEZER") {
            return freezer;
        } else if (name == "FRIDGE") {
            return fridge;
        } else {
            // TODO: MAKE THIS THROW ERROR
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

public class IngredientInStorage extends Ingredient{

    LocalDate bestBeforeDate;
    Location location;
    Category category;
    //TODO: change year,month,day to Calendar pls pls
    @RequiresApi(api = Build.VERSION_CODES.O)
    public IngredientInStorage(String description, String measurementUnit, int amount,
                               int year, int month, int day, Location location, Category category){
        super(description,measurementUnit,amount, category);
        bestBeforeDate = LocalDate.of(year, month, day);
        this.location = location;
        this.category = category;
    }

    public Location getLocation() {
        return location;
    }

    public String getLocationName() {return location.name(); } // ADDED

    public void setLocation(Location location) {
        this.location = location;
    }

    public LocalDate getBestBeforeDate() {
        return bestBeforeDate;
    }

    public String getFormattedBestBefore() {
        return bestBeforeDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setBestBeforeDate(int year, int month, int day) {
        bestBeforeDate = LocalDate.of(year, month, day);
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
