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
//TODO: Need to change the Ingredient to ingredientInRecipe?
public class Recipe extends FoodItem{
    private ArrayList<Ingredient> ingredients;
    private int numOfServings;
    private int prepTime; // in minutes??
    private String comments;
    private RecipeCategory category;
    //TODO: how to store picture -> Can potentially stash a path to an image in project directory.

    public Recipe(int numOfServings, int prepTime, String comments, RecipeCategory recipeCategory,
                  ArrayList<Ingredient> ingredients, String recipeName) {
        this.description = recipeName;
        this.ingredients = ingredients;
        this.prepTime = prepTime;
        this.comments = comments;
        this.category = recipeCategory;
        this.numOfServings = numOfServings;
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

    /**
     * getDescription() handles the retrieval of the recipe name.
     * TODO: Rename this function for better syntax.
     * @return String
     */
    public String getDescription() {
        return this.description;
    }

    public String getComments() {
        return this.comments;
    }

    public int getNumberOfIngredients() {
        return this.ingredients.size();
    }


    public int getPrepTime() {
        return this.prepTime;
    }

    public RecipeCategory getCategory() {
        return this.category;
    }

    public int getNumOfServings() {
        return this.numOfServings;
    }

    /**
     * getIngredients() returns the ingredients used in the recipe.
     * @return
     */
    public ArrayList<Ingredient> getIngredients() {
        return this.ingredients;
    }

    public void removeIngredient(Ingredient ingredient) {
        ingredients.remove(ingredient);
    }

    public boolean containsIngredient(Ingredient ingredient) {
        return this.ingredients.contains(ingredient);
    }
}
