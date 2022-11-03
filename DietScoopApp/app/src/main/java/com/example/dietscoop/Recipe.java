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

public class Recipe extends FoodItem{
    private String name;
    private int prepTime;
    private int servings;
    private timeUnit prepUnitTime;
    private recipeCategory category;
    ArrayList<IngredientInRecipe> ingredientsList;
    private String instructions;

    public Recipe(String name, int prepTime, int servings, timeUnit prepUnitTime, recipeCategory category, ArrayList<IngredientInRecipe> ingredientsList,
                  String description) {
        this.description = description;
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

    public int getPrepTime() {
        return prepTime;

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

    public void setIngredientsList(ArrayList<IngredientInRecipe> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    public void setInstructions(String description) {
        this.description = description;
    }

    public int getNumberOfIngredients() {
        return ingredientsList.size();
    }

    public String getInstructions() {
        return this.instructions;
    }

    public ArrayList<IngredientInRecipe> getIngredients() {
        return this.ingredientsList;
    }

    public recipeCategory getCategory() {
        return this.category;
    }

    public int getNumOfServings() {
        return this.servings;
    }

    public void addIngredient(IngredientInRecipe ingredient){
        this.ingredientsList.add(ingredient);
    }

    public void removeIngredient(IngredientInRecipe ingredient){
        this.ingredientsList.remove(ingredient);
    }
}
