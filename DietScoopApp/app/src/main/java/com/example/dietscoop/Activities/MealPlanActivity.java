package com.example.dietscoop.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageButton;

import androidx.appcompat.app.ActionBar;
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
import com.google.firebase.auth.FirebaseAuth;

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

    ActionBar topBar;

    CalendarView mealDayCalendar;

    Boolean inMealDayFragment;

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
        setupActionBar();

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

        changeToMealPlan();
    }

    public void listDates(){
        for(MealDay x : this.mealPlan){
            String date = x.getDate().toString();
            Log.w("DATE", date);
        }
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

    /**
     * This method navigates the user to the page where they can see their meal plan
     */
    public void changeToMealPlan() {
        Fragment prevFragment = mealPlanManager.findFragmentById(R.id.meal_plan_fragment);

        if (prevFragment != null) {
            mealPlanManager.beginTransaction().show(prevFragment).commit();
            return;
        }

        MealPlanFragment mealPlanFragment = new MealPlanFragment(this.mealPlan);

        mealPlanManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.full_fragment_container_view, mealPlanFragment) //TODO: Make it so it sends over the current mealPlan.
                .commit();
    }

    /**
     * This method navigates the user to the page where they can add a new meal day to their mealplan
     */
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

    /**
     * This method navigates the user to the page where they can change an existing meal day in their mealplan.
     * It is called when the user clicks on a meal day in their meal plan
     * @param indexOfDay index of the meal day the user has clicked on
     */
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
        deleteIndividualFoodItemsInMealDayFromDB(itemsToDelete);
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

    /**
     * This method adds to the database all ingredients and recipes that the user has added to their meal day
     * @param mealDay the current meal day being edited
     */
    private void addIndividualFoodItemsInMealDayToDB(MealDay mealDay) {
        for (IngredientInMealDay i: mealDay.getIngredientInMealDays()) {
            mealPlanStorage.addIngredientToIngredientsInMealDaysCollection(i);
        }
        for (RecipeInMealDay r: mealDay.getRecipeInMealDays()) {
            mealPlanStorage.addRecipeToRecipesInMealDaysCollection(r);
        }
    }

    /**
     * This method deletes all the ingredients and recipes that the user has selected to be deleted from a meal day
     * @param itemsToDelete the list of staged deletions
     */
    private void deleteIndividualFoodItemsInMealDayFromDB(ArrayList<FoodItem> itemsToDelete) {
        for (FoodItem i: itemsToDelete) {
            if (i.getType() == "Ingredient") {
                mealPlanStorage.removeIngredientFromIngredientsInMealDaysCollection((IngredientInMealDay)i);
            } else if (i.getType() == "Recipe") {
                mealPlanStorage.removeRecipeFromRecipesInMealDaysCollection((RecipeInMealDay)i);
            }
        }
    }

    /**
     * Method called when user wants to delete a particular mealday.
     * @param indexToDel index of meal day to delete
     */
    public void mealDayDelete(int indexToDel) {
        mealPlanStorage.removeMealDayFromMealPlan(this.mealPlan.get(indexToDel));
        this.mealPlan.remove(indexToDel);
        changeToMealPlan();
    }

    /**
     * This method sets up the top bar with a logout button and an add button
     */
    public void setupActionBar() {

        topBar = getSupportActionBar();
        topBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        topBar.setDisplayShowCustomEnabled(true);
        topBar.setCustomView(R.layout.top_bar_add_layout);
        inMealDayFragment = false;

        View topBarView = topBar.getCustomView();

        ImageButton logout = topBarView.findViewById(R.id.logout_button);
        ImageButton addItem = topBarView.findViewById(R.id.add_button);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddClicked();
            }
        });
    }

    /**
     * This method is called by MealDayFragment to set up a top bar without an add button and without a logout button
     */
    public void actionBarNoLogout(){
        topBar.setCustomView(R.layout.top_bar_no_logout_no_add);
    }

    /**
     * This method is called to navigate to the adding meal day page
     */
    void onAddClicked() {
        changeToMealDayAdd();
    }

    /**
     * This method logs out a currently logged in user
     */
    private void logout() {
        FirebaseAuth.getInstance().signOut();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

}
