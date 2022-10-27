package com.example.dietscoop;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Calendar;
import java.util.Locale;

//TODO: What are locations and categories?
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

public class IngredientInStorage extends Ingredient{

    Calendar bestBeforeDate;
    Location location;
    Category category;
    //TODO: change year,month,day to Calendar pls pls
    @RequiresApi(api = Build.VERSION_CODES.O)
    public IngredientInStorage(String description, String measurementUnit, int amount,
                               int year, int month, int day, Location location, Category category){
        super(description,measurementUnit,amount, category);
        bestBeforeDate = new Calendar.Builder().setDate(year,month,day).build();
        this.location = location;
        this.category = category;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Calendar getBestBeforeDate() {
        return bestBeforeDate;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setBestBeforeDate(int year, int month, int day) {
        bestBeforeDate = new Calendar.Builder().setDate(year,month,day).build();
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
