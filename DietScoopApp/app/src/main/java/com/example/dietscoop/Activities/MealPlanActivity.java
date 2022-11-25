package com.example.dietscoop.Activities;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.dietscoop.Data.FoodItem;
import com.example.dietscoop.Data.Ingredient.IngredientInMealDay;
import com.example.dietscoop.Data.Ingredient.IngredientInStorage;
import com.example.dietscoop.Data.Meal.MealDay;
import com.example.dietscoop.Data.Recipe.Recipe;
import com.example.dietscoop.Data.Recipe.RecipeInMealDay;
import com.example.dietscoop.Database.IngredientStorage;
import com.example.dietscoop.Database.MealPlanStorage;
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
 */
public class MealPlanActivity extends NavigationActivity {

    FragmentManager mealPlanManager;

    public MealPlanStorage mealPlanStorage;
    //Database:
    IngredientStorage ingredients;
    RecipeStorage recipes;

    //Root Containers:
    ArrayList<Recipe> recipesList;
    ArrayList<IngredientInStorage> ingredientsList;

    //Concat List:
    ArrayList<FoodItem> allFoodItems;

    //MealPlans:
    ArrayList<MealDay> mealPlan;

    /*
    Loads ingredients and recipes to pass onto fragments.
    Works as a hub to connect different fragments of activities.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meal_plan_activity);
        initNavigationActivity();
        navBar.setSelectedItemId(R.id.meals);

        mealPlanManager = getSupportFragmentManager();
        recipes = new RecipeStorage();
        ingredients = new IngredientStorage();
        allFoodItems = new ArrayList<>();

        mealPlanStorage = new MealPlanStorage();
        mealPlan = mealPlanStorage.getMealPlan();

        ingredients = new IngredientStorage();
        ingredients.setupIngredientSnapshotListener();
        ingredients.getIngredientStorageFromDatabase();

        recipes = new RecipeStorage();
        recipes.setupRecipeSnapshotListener();
        recipes.getRecipeStorageFromDatabase();

        ingredientsList = ingredients.getIngredientStorage();
        recipesList = recipes.getRecipeStorage();

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

    public ArrayList<FoodItem> getAllFoodItems() {
        allFoodItems.clear();
        allFoodItems.addAll(recipesList);
        allFoodItems.addAll(ingredientsList);
        return this.allFoodItems;
    }

    //Fragment View Switches:

    /**
     * Must be called the first time:
     */
    public void changeToMealPlanInitialize() {
        Bundle testing = null;
        if (this.mealPlan.size() > 0) {
            testing = new Bundle();
            testing.putSerializable("mealplan", this.mealPlan);
        }
        mealPlanManager.beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.full_fragment_container_view, MealPlanFragment.class, testing) //TODO: Make it so it sends over the current mealPlan.
                .commit();
    }

    //TODO: If time allows for it, add some garbage collecting to the fragment manager.

    /**
     * TODO: Need to add a bundle that sends over the mealPlan for this user.
     * Can change this to send over a variables needed in bundle.
     */
    public void changeToMealPlan() {
        Fragment prevFragment = mealPlanManager.findFragmentById(R.id.meal_plan_fragment);

        if (prevFragment != null) {
//            mealPlanManager.beginTransaction()
//                    .setReorderingAllowed(true)
//                    .commit();
            mealPlanManager.beginTransaction().show(prevFragment).commit();
            return;
        }
//        if (prevFragment != null) {
//            mealPlanManager.beginTransaction().remove(prevFragment).commit();
//        }

//        Bundle mealPlanSend = new Bundle();
//        mealPlanSend.putSerializable("mealplan", this.mealPlan); //Will always send a copy of the original data.
        MealPlanFragment mealPlanFragment = new MealPlanFragment(this.mealPlan);

        mealPlanManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.full_fragment_container_view, mealPlanFragment) //TODO: Make it so it sends over the current mealPlan.
                .commit();
    }

    public void changeToMealDayAdd() {
        Fragment prevFragment = mealPlanManager.findFragmentById(R.id.meal_day_fragment);

        if (prevFragment != null) {
            mealPlanManager.beginTransaction().remove(prevFragment).commit();
        }

        mealPlanManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.full_fragment_container_view, MealDayFragment.class, null)
                .commit();
    }

    public void changeToMealDayEdit(int indexOfDay) {
        Fragment prevFragment = mealPlanManager.findFragmentById(R.id.meal_day_fragment);

        if (prevFragment != null) {
            mealPlanManager.beginTransaction().remove(prevFragment).commit();
        }

        Bundle mealDayToEdit = new Bundle();
        mealDayToEdit.putSerializable("mealdaytoedit", this.mealPlan.get(indexOfDay));
        mealDayToEdit.putInt("dayindex", indexOfDay);
        MealDayFragment mealDayFragment = new MealDayFragment(mealDayToEdit);

        mealPlanManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.full_fragment_container_view, mealDayFragment)
                .commit();
    }

    //Making Changes to Days:

    /**
     * Method to call when confirming the changes to a specific day.
     * Method is meant to be called when a full transaction is done on a day.
     * @param indexOfDayToChange index of day to change.
     * @param changeToDay day to replace the index with.
     */
    public void makeChangeToDay(int indexOfDayToChange, MealDay changeToDay, ArrayList<FoodItem> itemsToDelete) {
        addIndividualFoodItemsInMealDayToDB(changeToDay); // don't worry "add" is same as "update" here
        deleteInvididualFoodItemsInMealDayFromDB(itemsToDelete);
        mealPlanStorage.updateMealDayInMealPlan(changeToDay);
        this.mealPlan.set(indexOfDayToChange, changeToDay);
    }

    /**
     * Will add the specified meal day to our mealPlan.
     * @param mealDay MealDay
     */
    public void mealDayAdd(MealDay mealDay) {
        addIndividualFoodItemsInMealDayToDB(mealDay);
        mealPlanStorage.addMealDayToMealPlan(mealDay);
        this.mealPlan.add(mealDay);
    }

    //TODO: Marcos fiddled in the depths of db code that stems from here:
    private void addIndividualFoodItemsInMealDayToDB(MealDay mealDay) {
        for (IngredientInMealDay i: mealDay.getIngredientInMealDays()) {
            mealPlanStorage.addIngredientToIngredientsInMealDaysCollection(i);
        }
        for (RecipeInMealDay r: mealDay.getRecipeInMealDays()) {
            mealPlanStorage.addRecipeToRecipesInMealDaysCollection(r);
        }
    }

    private void deleteInvididualFoodItemsInMealDayFromDB(ArrayList<FoodItem> itemsToDelete) {
        for (FoodItem i: itemsToDelete) {
            if (i.getType() == "Ingredient") {
                mealPlanStorage.removeIngredientFromIngredientsInMealDaysCollection((IngredientInMealDay)i);
            } else if (i.getType() == "Recipe") {

            }
        }
    }

    /**
     * Method to call when user wants to delete a particular mealday.
     * @param indexToDel
     */
    public void mealDayDelete(int indexToDel) {
        this.mealPlan.remove(indexToDel);
    }

    public ArrayList<MealDay> getMealDays() {
        return this.mealPlan;
    }

    public void updateDatabaseOfChange() {
        //Fill with method.
    }

}
