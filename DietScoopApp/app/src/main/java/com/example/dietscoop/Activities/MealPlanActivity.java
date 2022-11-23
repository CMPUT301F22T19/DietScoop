package com.example.dietscoop.Activities;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.dietscoop.Data.Ingredient.Ingredient;
import com.example.dietscoop.Data.Ingredient.IngredientCategory;
import com.example.dietscoop.Data.Ingredient.IngredientInStorage;
import com.example.dietscoop.Data.Ingredient.Location;
import com.example.dietscoop.Data.Meal.Meal;
import com.example.dietscoop.Data.Meal.MealDay;
import com.example.dietscoop.Data.Meal.MealPlan;
import com.example.dietscoop.Data.Recipe.Recipe;
import com.example.dietscoop.Database.IngredientStorage;
import com.example.dietscoop.Database.RecipeStorage;
import com.example.dietscoop.Fragments.MealDayFragment;
import com.example.dietscoop.Fragments.MealPlanFragment;
import com.example.dietscoop.R;

import java.util.ArrayList;

/**
 * This activity will handle the MealPlanner and will go through instantiating a new
 * MealPlan Object to contain the meals per day for each day.
 *
 * Directives:
 *
 *  Handles Backend Database connection.
 *  Will extract the recipes and ingredients in storage from the database.
 *  Will control the flow of the mealplan activity by switching fragments based on user end.
 *
 */
public class MealPlanActivity extends AppCompatActivity {

    FragmentManager mealPlanManager;

    //Database:
    IngredientStorage ingredients;
    RecipeStorage recipes;

    //Root Containers:
    ArrayList<Recipe> recipesList;
    ArrayList<IngredientInStorage> ingredientsList;

    //MealPlans:
    ArrayList<MealDay> mealDays;

    /*
    Loads ingredients and recipes to pass onto fragments.
    Works as a hub to connect different fragments of activities.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meal_plan_activity);

        mealPlanManager = getSupportFragmentManager();
        recipes = new RecipeStorage();
        ingredients = new IngredientStorage();

        //TODO: Set up database pull:
        if (mealDays == null) {
            mealDays = new ArrayList<>();
        }

        //TODO: Uncomment these when the code works!!!
//        recipesList = recipes.getRecipeStorage();
//        ingredientsList = ingredients.getIngredientStorage();

        //TESTING!!!!************************************************************************************
        ingredientsList = new ArrayList<>();
        ingredientsList.add(new IngredientInStorage("chicken", "kg", 4.0
        , 2024, 11, 25, Location.Freezer, IngredientCategory.Fruit));
        ingredientsList.add(new IngredientInStorage("billy", "kg", 4.0
                , 2001, 1, 15, Location.Freezer, IngredientCategory.Vegetable));
        //TESTING!!!!************************************************************************************

        //If database has no mealplan, send null and call MealPlanFrag:
//        changeToMealPlanInitialize();
        changeToMealPlan();
    }

    //Calls and Receipts from Fragments:
    public ArrayList<Recipe> getRecipesList() {
        return this.recipesList;
    }

    public ArrayList<IngredientInStorage> getIngredientsList() {
        return this.ingredientsList;
    }

    //Fragment View Switches:

    /**
     * Must be called the first time:
     */
    public void changeToMealPlanInitialize() {
        Bundle testing = null;
        if (this.mealDays.size() > 0) {
            testing = new Bundle();
            testing.putSerializable("mealplan", this.mealDays);
        }
        mealPlanManager.beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.full_fragment_container_view, MealPlanFragment.class, testing) //TODO: Make it so it sends over the current mealPlan.
                .commit();
    }

    /**
     * TODO: Need to add a bundle that sends over the mealPlan for this user.
     */
    public void changeToMealPlan() {
        mealPlanManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.full_fragment_container_view, MealPlanFragment.class, null) //TODO: Make it so it sends over the current mealPlan.
                .commit();
    }

//    public void changeToMealPlanWithExisting() {
//        Bundle status = new Bundle();
//        status.putSerializable("mealplan", this.mealDays);
//        changeToMealPlan(status);
//    }

    public void changeToMealDay() {
        mealPlanManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.full_fragment_container_view, MealDayFragment.class, null)
                .commit();
    }

//    public void changeToMealDayWithExisting(int mealDayIndex) {
//        Bundle status = new Bundle();
//        status.putSerializable("mealday", this.mealDays.get(mealDayIndex));
//        changeToMealPlan(status);
//    }

    //Making Changes to Days:

    /**
     * Method to call when confirming the changes to a specific day.
     * @param indexOfDayToChange index of day to change.
     * @param changeToDay day to replace the index with.
     */
    public void makeChangeToDay(int indexOfDayToChange, MealDay changeToDay) {
        this.mealDays.set(indexOfDayToChange, changeToDay);
    }

    public void changeEntireDays(ArrayList<MealDay> mealDays) {
        this.mealDays = mealDays;
        //TODO: Call database to also update theirs with this.
    }

    //TODO: Add a means to retrieve ingredients and recipe change to our mealdays.

    /**
     * Will add the specified meal day to our mealPlan.
     * @param mealDay MealDay
     */
    public void mealDayAdd(MealDay mealDay) {
        this.mealDays.add(mealDay);
    }

    public ArrayList<MealDay> getMealDays() {
        return this.mealDays;
    }

    public void updateDatabaseOfChange() {
        //Fill with method.
    }

}
