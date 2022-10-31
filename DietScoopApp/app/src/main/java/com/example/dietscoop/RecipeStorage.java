package com.example.dietscoop;

import java.util.ArrayList;

public class RecipeStorage {

    private ArrayList<Recipe> recipes;
    Database db;

    public RecipeStorage() {
        db = new Database();
        recipes = new ArrayList<Recipe>();
    }

    public ArrayList<Recipe> getRecipeStorage() {
        return recipes;
    }

    public void getRecipeStorageFromDatabase() {
        db.getRecipeStorage();
    }

    public void addRecipeToStorage(Recipe recipe) {
        db.addRecipeToStorage(recipe);
    }

    public void removeRecipeFromStorage(Recipe recipe) {
        db.removeRecipeFromStorage(recipe);
    }
}
