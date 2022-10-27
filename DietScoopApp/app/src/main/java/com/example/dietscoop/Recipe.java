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

    public String getName() {
        return name;
    }

    public String getPrepTime() {
        return Integer.toString(prepTime) + " " + prepUnitTime.name();

    }

    public int getServings() {
        return servings;
    }

    public String getCategory() {
        return category.name();
    }

    public ArrayList<Ingredient> getIngredientsList() {
        return ingredientsList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public void setPrepUnitTime(timeUnit prepUnitTime) {
        this.prepUnitTime = prepUnitTime;
    }

    public void setCategory(recipeCategory category) {
        this.category = category;
    }

    public void setIngredientsList(ArrayList<Ingredient> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    public void addIngredient(Ingredient ingredient){
        this.ingredientsList.add(ingredient);
    }

    public void removeIngredient(Ingredient ingredient){
        this.ingredientsList.remove(ingredient);
    }
}
