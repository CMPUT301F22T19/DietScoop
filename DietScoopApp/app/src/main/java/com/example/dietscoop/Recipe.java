package com.example.dietscoop;

import java.util.ArrayList;
import java.util.Locale;

enum timeUnit {
    hour,
    minute;

    public static timeUnit stringToCategory(String unit) {
        unit = unit.toUpperCase(Locale.ROOT);

        if (unit == "HOUR") {
            return hour;
        } else if (unit == "MINUTE") {
            return minute;
        } else {
            // TODO: MAKE THIS THROW ERROR
            return null;
        }

    }
}

enum recipeCategory {
    breakfast,
    lunch,
    dinner,
    appetizer,
    salad,
    baked;

    public static recipeCategory stringToCategory(String name) {
        name = name.toUpperCase(Locale.ROOT);

        if (name == "BREAKFAST") {
            return breakfast;
        } else if (name == "LUNCH") {
            return lunch;
        } else if (name == "DINNER") {
            return dinner;
        } else if (name == "APPETIZER") {
            return appetizer;
        } else if (name == "SALAD") {
            return salad;
        } else if (name == "BAKED") {
            return baked;
        } else {
            // TODO: MAKE THIS THROW ERROR
            return null;
        }

    }
}

public class Recipe {
    private String name;
    private int prepTime;
    private int servings;
    private timeUnit prepUnitTime;
    private recipeCategory category;
    ArrayList<Ingredient> ingredientsList;

    public Recipe(String name, int prepTime, int servings, timeUnit prepUnitTime, recipeCategory category, ArrayList<Ingredient> ingredientsList) {
        this.name = name;
        this.prepTime = prepTime;
        this.servings = servings;
        this.prepUnitTime = prepUnitTime;
        this.category = category;
        this.ingredientsList = ingredientsList;
    }
}
