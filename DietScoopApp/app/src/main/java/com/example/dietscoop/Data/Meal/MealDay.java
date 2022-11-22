package com.example.dietscoop.Data.Meal;


import com.example.dietscoop.Data.FoodItem;
import com.example.dietscoop.Data.Ingredient.Ingredient;
import com.example.dietscoop.Data.Ingredient.IngredientInMealDay;
import com.example.dietscoop.Data.Recipe.Recipe;
import com.example.dietscoop.Data.Recipe.RecipeInMealDay;

import org.checkerframework.checker.units.qual.A;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Class will work as a container for the Meals in the given mealday.
 *
 * 1. Class handles the fetching, adding and deletion of meals.
 * 2. Deletion of itself?
 * TODO: Add the removers for each of the following arraylists (Recipes and Ingredients).
 */
public class MealDay {

//    TODO: Uncomment this if going for the meal in mealday schema.
    ArrayList<Meal> mealsInDay;
    LocalDate date;
    ArrayList<FoodItem> foodItems;
    ArrayList<IngredientInMealDay> ingredientInMealDays;
    ArrayList<RecipeInMealDay> recipeInMealDays;
    private ArrayList<String> ingredientIDs;
    private ArrayList<String> recipeIDs;
    private String id;

    //Constructors: TODO: Incorporate more constructors for this class:
    public MealDay(LocalDate date) {
        mealsInDay = new ArrayList<>();
        foodItems = new ArrayList<>();
        ingredientIDs = new ArrayList<>();
        recipeIDs = new ArrayList<>();
        ingredientInMealDays = new ArrayList<>();
        recipeInMealDays = new ArrayList<>();
        this.date = date;
    }

    //Getters and Setters:
    /**
     * Returns the specified meal from the index specified.
     * @param i index of meal to retrieve.
     * @return Meal with index i.
     */
    public Meal getMeal(int i) {
        return this.mealsInDay.get(i);
    }

    public ArrayList<Meal> getMealsInDay() {
        return this.mealsInDay;
    }

    //Unique Methods:
    /**
     * Adds a meal to the MealDay.
     * @param meal Meal to add.
     */
    public void addMeal(Meal meal) {
        this.mealsInDay.add(meal);
    }


    /**
     * Removes a meal from the MealDay.
     * @param i index of meal to remove.
     */
    public void deleteMeal(int i) {
        this.mealsInDay.remove(i);
    }


    public void deleteIngredientFromMealDay(IngredientInMealDay ingredient) {
        this.ingredientIDs.remove(ingredient.getId());
        this.ingredientInMealDays.remove(ingredient);
    }

    public void addIngredientInMealDay(IngredientInMealDay ingredientInMealDay) {
        this.ingredientIDs.add(ingredientInMealDay.getId());
        this.ingredientInMealDays.add(ingredientInMealDay);
    }

    public void addRecipeInMealDay(RecipeInMealDay recipeInMealDay) {
        this.recipeInMealDays.add(recipeInMealDay);
    }

    public ArrayList<String> getRecipeIDs() {
        return this.recipeIDs;
    }

    public ArrayList<String> getIngredientIDs() {return this.ingredientIDs;}

    public LocalDate getDate() {
        return this.date;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
}
