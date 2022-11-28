package com.example.dietscoop.Data.Recipe;

public class RecipeInMealDay extends Recipe {
        private Double desiredNumOfServings;
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

    public void setDesiredNumOfServings(Double desiredNumOfServings) {
        this.desiredNumOfServings = desiredNumOfServings;
    }

    public Double getDesiredNumOfServings() {
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
