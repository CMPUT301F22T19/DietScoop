package com.example.dietscoop.Data.Meal;

import com.example.dietscoop.Data.Ingredient.IngredientInStorage;
import com.example.dietscoop.Data.Recipe.Recipe;

import java.util.ArrayList;

/**
 * Class that works as a wrapper for meals in a day:
 *
 *  1. Contains recipes and ingredients for the given meal.
 *  2. Has optional time that shows when to eat the meal.
 *  3. Will be connected to the database later with help from Gurbir.
 */
public class Meal {

    ArrayList<Recipe> recipesInMeal;
    ArrayList<IngredientInStorage> ingredientsInMeal;
    MealCategory category;
    String mealName;

    //Constructor:
    public Meal() {
        recipesInMeal = new ArrayList<>();
        ingredientsInMeal = new ArrayList<>();
    }

    //Getters and Setters: TODO: Apply error checking afterwards as necessary.
    public Recipe getRecipe(int i) {
        return recipesInMeal.get(i);
    }

    public IngredientInStorage getIngredient(int i) {
        return ingredientsInMeal.get(i);
    }



}
