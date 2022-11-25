package com.example.dietscoop.Data.Recipe;

import com.example.dietscoop.Data.Ingredient.IngredientInRecipe;
import com.example.dietscoop.Data.Recipe.Recipe;

import java.util.ArrayList;
import java.util.Date;

public class RecipeInMealDay extends Recipe {
        private Double scalingFactor;
        private Recipe parentRecipe;
        private String mealdayID;
        //TODO: WARNING MARCOS FIDDLED HERE:
        private String parentRecipeID; //This will be used to always hold a reference to the parent to fetch updates.
    public RecipeInMealDay(String description, int prepTime, int servings, timeUnit prepUnitTime,
                           recipeCategory category, ArrayList<IngredientInRecipe> ingredientsList, String instructions) {
        super(description, prepTime, servings, prepUnitTime, category, ingredientsList, instructions);
    }

    public RecipeInMealDay(Recipe r) {
        super(r.getDescription(),r.getPrepTime(), r.getNumOfServings(),r.getPrepUnitTime(),r.getCategory(),r.ingredientsList,
                r.getInstructions());
        this.parentRecipe = r;
        this.parentRecipeID = r.getId();
    }

    public void setScalingFactor(Double fac) {
        this.scalingFactor = fac;
    }

    public Double getScalingFactor() {
        return this.scalingFactor;
    }

    public Recipe getParentRecipe() {
        return parentRecipe;
    }

    public void setParentRecipe(Recipe parentRecipe) {
        this.parentRecipe = parentRecipe;
    }

    public String getMealdayID() {
        return mealdayID;
    }

    public void setMealdayID(String mealdayID) {
        this.mealdayID = mealdayID;
    }
}
