package com.example.dietscoop.Data.Meal;

import com.example.dietscoop.Data.Ingredient.IngredientInStorage;
import com.example.dietscoop.Data.Recipe.Recipe;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Class handles the MealPlan interface for interacting with mealdays and meals.
 * 1. Class fetches specified days of the meal plan which can be used for editing meals.
 * 2. Class will control the creation and deletion of mealdays along with their meals.
 */
public class MealPlan implements Serializable {

    ArrayList<MealDay> mealDays;
    ArrayList<Recipe> recipes;
    ArrayList<IngredientInStorage> ingredients;
    //These properties will track the days for the mealplan.
    LocalDate from;
    LocalDate to;

    //Constructors:
    public MealPlan() {
        mealDays = new ArrayList<>();
        recipes = new ArrayList<>();
        ingredients = new ArrayList<>();
    }

    //Getters and Setters:
    public LocalDate getFrom() {
        return this.from;
    }

    public LocalDate getTo() {
        return this.to;
    }

    public ArrayList<MealDay> getMealDays() {
        return this.mealDays;
    }

    //Unique Methods:

    /**
     * Adds the specified mealday to mealplan.
     * @param mealDay MealDay
     */
    public void addMealDay(MealDay mealDay) {
        this.mealDays.add(mealDay);
    }

    /**
     * Gets the specified meal day.
     * @param i index of meal day.
     * @return
     */
    public MealDay getMealDay(int i) {
        return this.mealDays.get(i);
    }

    /**
     * Removes the specified meal day.
     * @param i index of meal day.
     */
    public void deleteMealDay(int i) {
        this.mealDays.remove(i);
    }

}
