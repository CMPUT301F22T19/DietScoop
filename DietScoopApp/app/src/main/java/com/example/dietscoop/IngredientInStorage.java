package com.example.dietscoop;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Calendar;

enum Location {
    pantry,
    freezer,
    fridge
}
enum Category {
    vegetable,
    meat,
    fruit
}

public class IngredientInStorage extends Ingredient{

    Calendar bestBeforeDate;
    Location location;
    Category category;
    @RequiresApi(api = Build.VERSION_CODES.O)
    public IngredientInStorage(String description, String measurementUnit, int amount,
                               int year, int month, int day, Location location, Category category){
        super(description,measurementUnit,amount);
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
