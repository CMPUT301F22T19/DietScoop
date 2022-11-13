package com.example.dietscoop.Database;

import android.util.Log;

import com.example.dietscoop.Adapters.IngredientRecipeAdapter;
import com.example.dietscoop.Data.Ingredient.IngredientCategory;
import com.example.dietscoop.Data.Ingredient.IngredientInRecipe;
import com.example.dietscoop.Data.Ingredient.IngredientInStorage;
import com.example.dietscoop.Data.Recipe.Recipe;
import com.google.android.gms.tasks.OnSuccessListener;

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

/**
 * The Database class provides the methods and functionality for connecting with the Firestore database.
 */
class Database implements Serializable {

    private static final String TAG = "testing";
    private FirebaseFirestore db;
    private CollectionReference ingredientStorage;
    private CollectionReference recipeStorage;
    private CollectionReference ingredientsInRecipes;

    /**
     * Constructor for the Database class.
     */
    public Database() {
        db = FirebaseFirestore.getInstance();
        ingredientStorage = db.collection("IngredientStorage");
        recipeStorage = db.collection("Recipes");
        ingredientsInRecipes = db.collection("IngredientsInRecipes");
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
        ingredientDetails.put("unit", ingredient.getMeasurementUnit());
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
        Log.d(TAG, "delete ingredient from storage: "+ ingredientInStorage.getDescription());
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
        ingredientDetails.put("unit", ingredient.getMeasurementUnit().toLowerCase());
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
        recipeStorage.add(recipeDetails);
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
        recipeStorage.document(recipe.getId()).set(recipeDetails);
    }

    public void getAllIngredientsInRecipes() {
        ingredientsInRecipes.get();
    }

    public CollectionReference getIngredientsInRecipesCollectionRef() {return this.ingredientsInRecipes;}

    public void addIngredientToIngredientsInRecipesCollection(IngredientInRecipe ingredientInRecipe) {
        ingredientsInRecipes.add(ingredientInRecipe);
    }

    public void removeIngredientFromIngredientsInRecipesCollection(String docref) {
        ingredientsInRecipes.document(docref).delete();
    }

    //TODO: not tested yet; test when editing-ingredient-in-recipe thing is implemented
    public void updateIngredientInIngredientsInRecipesCollection(IngredientInRecipe ingredient) {
        ingredientsInRecipes.document(ingredient.getId()).set(ingredient);
    }
}


