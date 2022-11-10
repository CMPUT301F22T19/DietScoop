package com.example.dietscoop.Data;

/**
 * Class representing ingredient in a recipe.
 */
public class IngredientInRecipe extends Ingredient {

    /**
     * Ingredient in recipe constructor. Calls Ingredient Class constructor.
     * @param description Description of ingredient in recipe.
     * @param measurementUnit Measurement unit of ingredient in recipe.
     * @param amount amount of ingredient in recipe.
     * @param category category of ingredient in recipe.
     */
    public IngredientInRecipe(String description, String measurementUnit, double amount, IngredientCategory category) {
        super(description,measurementUnit,amount, category);
        
    }
}
