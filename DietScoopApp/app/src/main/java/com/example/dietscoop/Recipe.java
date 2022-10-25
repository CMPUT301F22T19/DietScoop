package com.example.dietscoop;

import java.util.ArrayList;

public class Recipe {
    private ArrayList<Ingredient> ingredients;

    public Recipe() {
        ingredients = new ArrayList<Ingredient>();
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

    public void removeIngredient(Ingredient ingredient) {
        ingredients.remove(ingredient);
    }
}
