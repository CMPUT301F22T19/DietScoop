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
 * 1. Need a means to add mealdays.
 * 2. Needs a means to generate fragments.
 * 3. Need to store everything back in this activity and send any updates to the firestore database.
 * 4. Need to add the event for the floating button to query the user to add a new mealday.
 *
 * Workflow for checking existence:
 *
 *  1. Query Database to check if mealplan already exists.
 *  2. If exists -> fetch it and update the days that have passed. (Delete them)
 *  3. If not exists -> query the user to create a new mealplan.
 *
 */
public class MealPlanActivity extends AppCompatActivity {

    //Variables:
    FragmentManager mealPlanManager;
    IngredientStorage ingredients;
    RecipeStorage recipes;
    MealPlan mealPlan;
    ArrayList<Recipe> recipesList;
    ArrayList<IngredientInStorage> ingredientsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meal_plan_activity);

        mealPlanManager = getSupportFragmentManager();
        recipes = new RecipeStorage();
        ingredients = new IngredientStorage();

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

        //Changing Fragment to MealPlan:
        changeToMealPlan(null);
    }

    //Calls and Receipts from Fragments:
    public ArrayList<Recipe> getRecipesList() {
        return this.recipesList;
    }

    public ArrayList<IngredientInStorage> getIngredientsList() {
        return this.ingredientsList;
    }

    //TODO: This function can be connected to the fragment view that handles the mealplan after the database works.
    public MealPlan getMealPlan() {
        return this.mealPlan;
    }

    //Fragment View Switches:

    /**
     * TODO: Need to add a bundle that sends over the mealPlan for this user.
     */
    public void changeToMealPlan(Bundle planToSend) {
        mealPlanManager.beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.full_fragment_container_view, MealPlanFragment.class, null) //TODO: Make it so it sends over the current mealPlan.
                .commit();
    }

    public void changeToAddDay() {
        mealPlanManager.beginTransaction()
                .replace(R.id.full_fragment_container_view, MealDayFragment.class, null)
                .commit();
    }

    //TODO: Add a means to retrieve ingredients and recipe change to our mealdays.

    /**
     * Will add the specified meal day to our mealPlan.
     * @param mealDay MealDay
     */
    public void mealDayAdd(MealDay mealDay) {
        this.mealPlan.addMealDay(mealDay);
    }

}
