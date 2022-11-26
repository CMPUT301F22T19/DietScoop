package com.example.dietscoop.Data.Recipe;

import com.example.dietscoop.Data.Ingredient.IngredientInRecipe;
import com.example.dietscoop.Data.Recipe.Recipe;

import java.util.ArrayList;
import java.util.Date;

public class RecipeInMealDay extends Recipe {
        private Integer desiredNumOfServings;
        private Recipe parentRecipe;
        private String mealdayID;
        //TODO: WARNING MARCOS FIDDLED HERE:
        private String parentRecipeID; //This will be used to always hold a reference to the parent to fetch updates.
    public RecipeInMealDay(String description) {
        super(description);
    }

    public RecipeInMealDay(Recipe r) {
        super(r.getDescription());
        this.parentRecipeID = r.getId();
    }

    public void setDesiredNumOfServings(Integer desiredNumOfServings) {
        this.desiredNumOfServings = desiredNumOfServings;
    }

    public Integer getDesiredNumOfServings() {
        return desiredNumOfServings;
    }

    public Recipe getParentRecipe() {
        return parentRecipe;
    }

    public String getParentRecipeID() {
        return parentRecipeID;
    }

    public void setParentRecipeID(String parentRecipeID) {
        this.parentRecipeID = parentRecipeID;
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
