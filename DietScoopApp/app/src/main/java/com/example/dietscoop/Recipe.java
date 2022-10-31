package com.example.dietscoop;

import java.util.ArrayList;
import java.util.Locale;

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
    private recipeCategory category;
    ArrayList<Ingredient> ingredientsList;
    private String instructions;

    public Recipe(String name, int prepTime, int servings, recipeCategory category, ArrayList<Ingredient> ingredientsList, String instructions) {
        this.name = name;
        this.prepTime = prepTime;
        this.servings = servings;
        this.category = category;
        this.ingredientsList = ingredientsList;
        this.instructions = instructions;
    }

    public Recipe(String name, int prepTime, int servings, recipeCategory category, ArrayList<Ingredient> ingredientsList) {
        this.name = name;
        this.prepTime = prepTime;
        this.servings = servings;
        this.category = category;
        this.ingredientsList = ingredientsList;
    }

    public String getName() {
        return name;
    }

    public String getPrepTime() {
        return Integer.toString(prepTime) + " minutes";
    }

    public int getServings() {
        return servings;
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

    public void setCategory(recipeCategory category) {
        this.category = category;
    }

    public void setIngredientsList(ArrayList<Ingredient> ingredientsList) {
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

    public ArrayList<Ingredient> getIngredients() {
        return this.ingredientsList;
    }

    public recipeCategory getCategory() {
        return this.category;
    }

    public int getNumOfServings() {
        return this.servings;
    }

    public void addIngredient(Ingredient ingredient){
        this.ingredientsList.add(ingredient);
    }

    public void removeIngredient(Ingredient ingredient){
        this.ingredientsList.remove(ingredient);
    }
}
