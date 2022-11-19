package com.example.dietscoop.Data.Meal;


import com.example.dietscoop.Data.FoodItem;
import com.example.dietscoop.Data.Recipe.Recipe;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Class will work as a container for the Meals in the given mealday.
 *
 * 1. Class handles the fetching, adding and deletion of meals.
 * 2. Deletion of itself?
 */
public class MealDay {

//    TODO: Uncomment this if going for the meal in mealday schema.
    ArrayList<Meal> mealsInDay;
    LocalDate date;
    ArrayList<FoodItem> foodItems;

    //Constructors: TODO: Incorporate more constructors for this class:
    public MealDay(LocalDate date) {
        mealsInDay = new ArrayList<>();
        foodItems = new ArrayList<>();
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

    public ArrayList<FoodItem> getFoodItems() {
        return this.foodItems;
    }

    public LocalDate getDate() {
        return this.date;
    }

}
