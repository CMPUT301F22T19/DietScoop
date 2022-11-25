package com.example.dietscoop.Database;

import android.content.Intent;
import android.util.Log;

import com.example.dietscoop.Adapters.IngredientRecipeAdapter;
import com.example.dietscoop.Data.Ingredient.IngredientCategory;
import com.example.dietscoop.Data.Ingredient.IngredientInMealDay;
import com.example.dietscoop.Data.Ingredient.IngredientInRecipe;
import com.example.dietscoop.Data.Ingredient.IngredientInStorage;
import com.example.dietscoop.Data.Meal.Meal;
import com.example.dietscoop.Data.Meal.MealDay;
import com.example.dietscoop.Data.Recipe.Recipe;
import com.example.dietscoop.Data.Recipe.RecipeInMealDay;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.Map;
import java.util.UUID;

/**
 * The Database class provides the methods and functionality for connecting with the Firestore database.
 */
class Database implements Serializable {

    private static final String TAG = "testing";
    private FirebaseFirestore db;
    private CollectionReference ingredientStorage;
    private CollectionReference recipeStorage;
    private CollectionReference ingredientsInRecipes;
    private CollectionReference mealPlan;
    private CollectionReference ingredientsInMealDays;
    private CollectionReference recipesInMealDays;

    /**
     * Constructor for the Database class.
     */
    public Database() {

        String user = getUserEmail();

        db = FirebaseFirestore.getInstance();
        ingredientStorage = db.collection("Users").document(user).collection("IngredientsStorage");
        recipeStorage = db.collection("Users").document(user).collection("Recipes");
        ingredientsInRecipes = db.collection("Users").document(user).collection("IngredientsInRecipes");
        mealPlan = db.collection("Users").document(user).collection("MealPlan");
        ingredientsInMealDays = db.collection("Users").document(user).collection("IngredientsInMealDays");
        recipesInMealDays = db.collection("Users").document(user).collection("RecipesInMealDays");
    }

    private String getUserEmail() throws RuntimeException {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null) {
            throw new RuntimeException("NO USER SIGNED IN TO DATABASE");
        }

        return user.getEmail();
    }


    /*************************** INGREDIENT METHODS ******************************/

    /**
     * Getter for Firestore collection reference of Recipes collection. Does not query database.
     * @return collection reference to Ingredients storage
     */
    public CollectionReference getIngredientCollectionRef() {
        return ingredientStorage;
    }

    /**
     * Method for adding ingredient to Firestore database.
     * @param ingredient the ingredient object to be added to database
     */
    public void addIngredientToStorage(IngredientInStorage ingredient) {
        Map<String, Object> ingredientDetails = new HashMap<>();
        LocalDate expiry = ingredient.getBestBeforeDate();
        int year = expiry.getYear();
        int month = expiry.getMonthValue();
        int day = expiry.getDayOfMonth();
        ingredientDetails.put("description", ingredient.getDescription().toLowerCase());
        ingredientDetails.put("amount", ingredient.getAmount());
        ingredientDetails.put("measurementUnit", ingredient.getMeasurementUnit());
        ingredientDetails.put("year", year);
        ingredientDetails.put("month", month);
        ingredientDetails.put("day", day);
        ingredientDetails.put("location", ingredient.getLocation());
        ingredientDetails.put("category", ingredient.getCategory());
        // .add() auto generates document ID in Firestore; this doesn't use ingredient's name as ID
        ingredientStorage.add(ingredientDetails);
    }

    /**
     * Asynchronously queries IngredientStorage collection in database.
     * All classes with snapshot listeners for this database will be updated when the asynchronous
     * call returns.
     */
    public void getIngredientStorage() {
        ingredientStorage.get();
    }

    /**
     * Method for removing ingredient from Firestore database. Method removes associated object.
     * @param ingredientInStorage general ingredient in storage to remove
     */
    public void removeIngredientFromStorage(IngredientInStorage ingredientInStorage) {
        Log.d(TAG, "delete ingredient from storage: " + ingredientInStorage.getDescription());
        ingredientStorage.document(ingredientInStorage.getId()).delete()
                .addOnSuccessListener(unused -> Log.d(TAG, "Data has been deleted successfully!"));
    }

    /**
     * Method to update the an ingredient in storage.
     * @param ingredient new ingredient to be added in place of old
     */
    public void updateIngredientInStorage(IngredientInStorage ingredient) {
        // This requires the passed-in ingredient to already have an ID, which we get from
        // Firestore, so it has to get the ID from the ingredient being edited
        Map<String, Object> ingredientDetails = new HashMap<>();
        LocalDate expiry = ingredient.getBestBeforeDate();
        int year = expiry.getYear();
        int month = expiry.getMonthValue();
        int day = expiry.getDayOfMonth();
        ingredientDetails.put("description", ingredient.getDescription().toLowerCase());
        ingredientDetails.put("amount", ingredient.getAmount());
        ingredientDetails.put("measurementUnit", ingredient.getMeasurementUnit().name().toLowerCase());
        ingredientDetails.put("year", year);
        ingredientDetails.put("month", month);
        ingredientDetails.put("day", day);

        ingredientDetails.put("location", ingredient.getLocation());
        ingredientDetails.put("category", ingredient.getCategory());

        ingredientStorage.document(ingredient.getId()).set(ingredientDetails);
        Log.e("update ingredientInStor","ID: "+ingredient.getId());

    }


    /*************************** RECIPE METHODS ******************************/

    /**
     * Method for adding recipe to Firestore database.
     * @param recipe the recipe object to be added to database
     */
    public void addRecipeToStorage(Recipe recipe) {
        Map<String, Object> recipeDetails = new HashMap<>();
        recipeDetails.put("prepTime", recipe.getPrepTime());
        recipeDetails.put("servings", recipe.getNumOfServings());
        recipeDetails.put("description", recipe.getDescription().toLowerCase());
        recipeDetails.put("instructions", recipe.getInstructions());
        recipeDetails.put("category", recipe.getCategory().toString());
        recipeDetails.put("prepUnitTime", recipe.getPrepUnitTime().toString());
        recipeDetails.put("ingredients", recipe.getIngredientRefs());
        recipeDetails.put("imageBitmap", recipe.getImageBitmap());
        recipeStorage.document(recipe.getId()).set(recipeDetails);
    }

    /**
     * Method for removing Recipe from Firestore database. Method removes associated object.
     * @param recipe Object to be removed from the database
     */
    public void removeRecipeFromStorage(Recipe recipe) {
        ArrayList<String> temp = recipe.getIngredientRefs();

        Log.d(TAG, "delete recipe from storage: "+ recipe.getDescription());
        recipeStorage.document(recipe.getId()).delete()
                .addOnSuccessListener(unused -> Log.d(TAG, "Data has been deleted successfully!"));
        for (String ingredient: temp) {
            ingredientsInRecipes.document(ingredient).delete();
        }
    }

    /**
     * Getter for Firestore collection reference for Recipes collection. Does not query database
     * @return reference to remote recipe collection
     */
    public CollectionReference getRecipeCollectionRef() { return this.recipeStorage; }

    /**
     * Asynchronously query recipe collection in Firestore database
     * All classes with snapshot listeners for this database will be updated when the asynchronous
     * call returns.
     */
    public void getRecipeStorage() {
        // queries recipes collection in DB
        recipeStorage.get();
    }

    /**
     * Method to update the a recipe in storage.
     * @param recipe object that will be updated with new recipe
     */
    public void updateRecipeInStorage(Recipe recipe) {
        Map<String, Object> recipeDetails = new HashMap<>();
        recipeDetails.put("prepTime", recipe.getPrepTime());
        recipeDetails.put("servings", recipe.getNumOfServings());
        recipeDetails.put("description", recipe.getDescription().toLowerCase());
        recipeDetails.put("instructions", recipe.getInstructions());
        recipeDetails.put("category", recipe.getCategory().toString());
        recipeDetails.put("prepUnitTime", recipe.getPrepUnitTime().toString());
        recipeDetails.put("ingredients", recipe.getIngredientRefs());
        recipeDetails.put("imageBitmap", recipe.getImageBitmap());
        recipeStorage.document(recipe.getId()).set(recipeDetails);
    }

    public void getAllIngredientsInRecipes() {
        ingredientsInRecipes.get();
    }

    public CollectionReference getIngredientsInRecipesCollectionRef() {return this.ingredientsInRecipes;}

    public void addIngredientToIngredientsInRecipesCollection(IngredientInRecipe ingredientInRecipe) {
        ingredientsInRecipes.document(ingredientInRecipe.getId()).set(ingredientInRecipe);
    }

    public void removeIngredientFromIngredientsInRecipesCollection(IngredientInRecipe ingredient) {
        ingredientsInRecipes.document(ingredient.getId()).delete();
    }

    //TODO: not tested yet; test when editing-ingredient-in-recipe thing is implemented
    public void updateIngredientInIngredientsInRecipesCollection(IngredientInRecipe ingredient) {
        ingredientsInRecipes.document(ingredient.getId()).set(ingredient);
    }

    /*************************** MEAL PLAN METHODS ******************************/

    public void getMealPlanFromDB() {mealPlan.get();}

    public void addMealDayToMealPlan(MealDay mealday) {
        Map<String, Object> mealdayDetails = new HashMap<>();
        LocalDate date = mealday.getDate();
        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();
        mealdayDetails.put("year", year);
        mealdayDetails.put("month", month);
        mealdayDetails.put("day", day);
        mealdayDetails.put("ingredients",mealday.getIngredientIDs());
        mealdayDetails.put("recipes",mealday.getRecipeIDs());
        mealPlan.document(mealday.getId()).set(mealdayDetails);
    }

    public void updateMealDayInMealPlan(MealDay mealday) {
        this.addMealDayToMealPlan(mealday);
    }

    public void removeMealDayFromMealPlan(MealDay mealDay) {
        mealPlan.document(mealDay.getId()).delete();
    }

    //TODO: Marcos fiddled here:
    public void addIngredientToIngredientsInMealDaysCollection(IngredientInMealDay ingredient) {
        Map<String, Object> ingredientDetails = new HashMap<>();
        ingredientDetails.put("description", ingredient.getDescription().toLowerCase());
        ingredientDetails.put("amount", ingredient.getAmount());
        ingredientDetails.put("measurementUnit", ingredient.getMeasurementUnit());
        ingredientDetails.put("category", ingredient.getCategory());
        ingredientDetails.put("mealdayID",ingredient.getMealdayID());
        ingredientDetails.put("parentIngredientID", ingredient.getParentIngredient().getId()); //Changed this to add a reference to the parent item.
        ingredientsInMealDays.document(ingredient.getId()).set(ingredientDetails);

    }

    public void updateIngredientInIngredientsInMealDaysCollection(IngredientInMealDay ingredient) {
        this.addIngredientToIngredientsInMealDaysCollection(ingredient);
    }

    public void removeIngredientFromIngredientsInMealDaysCollection(IngredientInMealDay ingredient) {
        ingredientsInMealDays.document(ingredient.getId()).delete();
    }
    //TODO: Marcos fiddled here:
    public void addRecipeToRecipesInMealDaysCollection(RecipeInMealDay recipeInMealDay) {
        Map<String, Object> recipeDetails = new HashMap<>();
        recipeDetails.put("prepTime", recipeInMealDay.getPrepTime());
        recipeDetails.put("servings", recipeInMealDay.getNumOfServings());
        recipeDetails.put("description", recipeInMealDay.getDescription().toLowerCase());
        recipeDetails.put("instructions", recipeInMealDay.getInstructions());
        recipeDetails.put("category", recipeInMealDay.getCategory().toString());
        recipeDetails.put("prepUnitTime", recipeInMealDay.getPrepUnitTime().toString());
        recipeDetails.put("ingredients", recipeInMealDay.getIngredientRefs());
        recipeDetails.put("scalingFactor",recipeInMealDay.getScalingFactor());
        recipeDetails.put("mealDayID",recipeInMealDay.getMealdayID());
        recipeDetails.put("parentRecipeID", recipeInMealDay.getParentRecipe().getId()); //Changed this to add a reference to the parent item.
        recipesInMealDays.document(recipeInMealDay.getId()).set(recipeDetails);
    }

    public void updateRecipeInRecipesInMealDaysCollection(RecipeInMealDay recipeInMealDay) {
        this.addRecipeToRecipesInMealDaysCollection(recipeInMealDay);
    }

    public void removeRecipeFromRecipesInMealDaysCollection(RecipeInMealDay recipeInMealDay) {
        recipesInMealDays.document(recipeInMealDay.getId()).delete();
    }

    public CollectionReference getIngredientsInMealDaysCollectionRef() {
        return ingredientsInMealDays;
    }

    public CollectionReference getRecipesInMealDaysCollectionRef() {
        return recipesInMealDays;
    }

    public CollectionReference getMealPlan() {return this.mealPlan;}



}


