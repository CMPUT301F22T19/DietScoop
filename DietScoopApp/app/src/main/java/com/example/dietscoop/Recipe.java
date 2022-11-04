package com.example.dietscoop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;

enum timeUnit implements Serializable {
/**
 * Enum representing time units
 */
    hour,
    minute;

    /**
     * Converts string to associated time unit
     * @param unit String unit to be converted to timeUnit Enum
     * @return timeUnit enum value
     */
    public static timeUnit stringToTimeUnit(String unit) {
        unit = unit.toUpperCase(Locale.ROOT);

        if (unit.equals("HOUR")) {
            return hour;
        } else if (unit.equals("MINUTE")) {
            return minute;
        } else {
            // TODO: MAKE THIS THROW ERROR
            return null;
        }

    }
}

/**
 * Enum representing recipe categories
 */
enum recipeCategory implements Serializable {
    breakfast,
    lunch,
    dinner,
    appetizer,
    salad,
    baked;

    /**
     * Converts string to associated recipe category
     * @param name String name to be converted to recipeCategory enum
     * @return recipeCategory enum value
     */
    public static recipeCategory stringToRecipeCategory(String name) {
        name = name.toUpperCase(Locale.ROOT);

        if (name.equals("BREAKFAST")) {
            return breakfast;
        } else if (name.equals("LUNCH")) {
            return lunch;
        } else if (name.equals("DINNER")) {
            return dinner;
        } else if (name.equals("APPETIZER")) {
            return appetizer;
        } else if (name.equals("SALAD")) {
            return salad;
        } else if (name.equals("BAKED")) {
            return baked;
        } else {
            // TODO: MAKE THIS THROW ERROR
            return null;
        }

    }
}

/**
 * Class representing a Recipe
 */
public class Recipe extends FoodItem implements Serializable {
    private String name;
    private int prepTime;
    private int servings;
    private timeUnit prepUnitTime;
    private recipeCategory category;
    ArrayList<IngredientInRecipe> ingredientsList;
    private String instructions;

    /**
     * Constructor for Recipe object
     * @param description Description of recipe
     * @param prepTime Preparation time of recipe
     * @param servings Serving number of recipe
     * @param prepUnitTime Units used for preparation time (prepTime)
     * @param category Category of recipe
     * @param ingredientsList List of ingredients used in recipe
     * @param instructions Instructions for recipe preparation
     */
    public Recipe(String description, int prepTime, int servings, timeUnit prepUnitTime,
                  recipeCategory category, ArrayList<IngredientInRecipe> ingredientsList, String instructions) {
        this.description = description;
        this.prepTime = prepTime;
        this.servings = servings;
        this.prepUnitTime = prepUnitTime;
        this.category = category;
        this.ingredientsList = ingredientsList;
        this.instructions = instructions;
    }

    /**
     * Getter for recipe preparation time
     * @return prep time of recipe (int)
     */
    public int getPrepTime() {
        return prepTime;
    }

    /**
     * Setter for recipe preparation time
     * @param prepTime new prepTime of recipe
     */
    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

    /**
     * Setter for recipe number of servings
     * @param servings new servings count of recipe
     */
    public void setNumOfServings(int servings) {
        this.servings = servings;
    }

    /**
     * Setter for preparation time (prepTime) units
     * @param prepUnitTime new prep time units
     */
    public void setPrepUnitTime(timeUnit prepUnitTime) {
        this.prepUnitTime = prepUnitTime;
    }

    /**
     * Getter for prep time units
     * @return Prep time units for recipe
     */
    public timeUnit getPrepUnitTime() {
        return prepUnitTime;
    }

    /**
     * Setter for recipe category
     * @param category new Category
     */
    public void setCategory(recipeCategory category) {
        this.category = category;
    }

    /**
     * Setter for recipe associated ingredients list
     * @param ingredientsList new ingredients list
     */
    public void setIngredientsList(ArrayList<IngredientInRecipe> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    /**
     * Setter for recipe instructions
     * @param description new instructions for recipe
     */
    public void setInstructions(String description) {
        this.instructions = description;
    }

    /**
     * Getter for number of ingredients used in recipe
     * @return the size of the ingredients list
     */
    public int getNumberOfIngredients() {
        return ingredientsList.size();
    }

    /**
     * Getter for recipe instructions
     * @return the instructions of the current recipe
     */
    public String getInstructions() {
        return this.instructions;
    }

    /**
     * Getter for ArrayList of ingredients
     * @return the ArrayList containing the recipe ingredients
     */
    public ArrayList<IngredientInRecipe> getIngredients() {
        return this.ingredientsList;
    }

    /**
     * Getter for category of recipe
     * @return the category of the recipe
     */
    public recipeCategory getCategory() {
        return this.category;
    }

    /**
     * Getter for the recipe category in string form
     * @return
     */
    public String getCategoryName() {return this.category.name(); }

    /**
     * Getter for the number of servings for the recipe
     * @return the number of servings of the recipe
     */
    public int getNumOfServings() {
        return this.servings;
    }

    /**
     * Method to add ingredient to current ArrayList of ingredients
     * @param ingredient ingredient to be added to ingredients list
     */
    public void addIngredient(IngredientInRecipe ingredient){
        this.ingredientsList.add(ingredient);
    }

    /**
     * Method to remove ingredient from current ArrayList of ingredients
     * @param ingredient ingredient to be removed from ingredients list
     */
    public void removeIngredient(IngredientInRecipe ingredient){
        this.ingredientsList.remove(ingredient);
    }
}
