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
}
