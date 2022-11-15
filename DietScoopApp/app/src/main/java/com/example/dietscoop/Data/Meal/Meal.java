package com.example.dietscoop.Data.Meal;

import com.example.dietscoop.Data.Ingredient.IngredientCategory;
import com.example.dietscoop.Data.Ingredient.IngredientInStorage;
import com.example.dietscoop.Data.Ingredient.Location;
import com.example.dietscoop.Data.Recipe.Recipe;

import java.util.ArrayList;

/**
 * Class that works as a container for meals in a day:
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

    public void setCategory(MealCategory category) {
        this.category = category;
    }

    public MealCategory getCategory() {
        return this.category;
    }

    //Unique Methods:
    /**
     * Adds a new ingredient into the meal.
     * @param ingredient IngredientInStorage
     */
    public void addIngredient(IngredientInStorage ingredient) {
        ingredientsInMeal.add(ingredient);
    }

    /**
     * Removes an ingredient from a given meal.
     * @param i int
     */
    public void deleteIngredient(int i) {
        ingredientsInMeal.remove(i);
    }

    /**
     * Adds a new recipe into the meal.
     * @param recipe Recipe
     */
    public void addRecipe(Recipe recipe) {
        recipesInMeal.add(recipe);
    }

    /**
     * Removes a recipe from a given meal.
     * @param i int
     */
    public void deleteRecipe(int i) {
        recipesInMeal.remove(i);
    }

}
