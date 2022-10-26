package com.example.dietscoop;

import java.util.ArrayList;
import java.util.Locale;

//TODO: What are categories? Ask Jakaria
enum RecipeCategory {
    dinner,
    lunch,
    breakfast;
    public static RecipeCategory stringToCategory(String name) {
        name = name.toUpperCase(Locale.ROOT);

        if (name == "DINNER") {
            return dinner;
        } else if (name == "LUNCH") {
            return lunch;
        } else if (name == "BREAKFAST") {
            return breakfast;
        } else {
            // TODO: MAKE THIS THROW ERROR
            return null;
        }

    }
}
public class Recipe extends FoodItem{
    private ArrayList<Ingredient> ingredients;
    private int numOfServings;
    private int prepTime; // in minutes??
    private String comments;
    private RecipeCategory category;
    //TODO: how to store picture??

    public Recipe(int numOfServings, int prepTime, String comments, RecipeCategory recipeCategory,
                  ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
        this.prepTime = prepTime;
        this.comments = comments;
        this.category = recipeCategory;
        this.numOfServings = numOfServings;
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

    public void removeIngredient(Ingredient ingredient) {
        ingredients.remove(ingredient);
    }
}
