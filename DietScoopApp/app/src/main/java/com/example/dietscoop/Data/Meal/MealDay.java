package com.example.dietscoop.Data.Meal;


import java.time.LocalDate;
import java.util.ArrayList;

public class MealDay {

    ArrayList<Meal> mealsInDay;
    LocalDate date;

    //Constructors:
    public MealDay(LocalDate date) {
        mealsInDay = new ArrayList<>();
        this.date = date;
    }

    //Getters and Setters:

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

    /**
     * Returns the specified meal from the index specified.
     * @param i index of meal to retrieve.
     * @return Meal with index i.
     */
    public Meal editMeal(int i) {
        return this.mealsInDay.get(i);
    }

}
