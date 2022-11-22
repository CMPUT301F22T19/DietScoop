package com.example.dietscoop.Database;

import com.example.dietscoop.Data.Meal.MealDay;
import com.example.dietscoop.Data.Meal.MealPlan;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class MealPlanStorage {

    private Database db;
    private ArrayList<MealDay> mealPlan;
    public MealPlanStorage() {
        db = new Database();
        mealPlan = new ArrayList<>();
    }




}
