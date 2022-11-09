package com.example.dietscoop;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.firestore.CollectionReference;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.Locale;
import java.util.Map;

/**
 * The Database class provides the methods and functionality for connecting with the Firestore database.
 */
public class Database implements Serializable {

    private static final String TAG = "testing";
    private FirebaseFirestore db;
    private CollectionReference ingredientStorage;
    private CollectionReference recipeStorage;
    private CollectionReference ingredientsInRecipe;

    /**
     * Constructor for the Database class.
     */
    public Database() {
        db = FirebaseFirestore.getInstance();
        ingredientStorage = db.collection("IngredientStorage");
        recipeStorage = db.collection("Recipes");
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
        ingredientDetails.put("amount", Double.valueOf(ingredient.getAmount()));
        ingredientDetails.put("unit", ingredient.getMeasurementUnit());
        ingredientDetails.put("year", Integer.valueOf(year));
        ingredientDetails.put("month", Integer.valueOf(month));
        ingredientDetails.put("day", Integer.valueOf(day));

        ingredientDetails.put("location", ingredient.getLocation().toString());
        ingredientDetails.put("category", ingredient.getCategory().toString());

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
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "Data has been deleted successfully!");
                    }
                });
    }

    /**
     * Method to update the an ingredient in storage.
     * @param ingredient new ingredient to be added in place of old
     */
    public void updateIngredientInStorage(IngredientInStorage ingredient) {
        // also, this requires the passed-in ingredient to already have an ID, which we get from
        // Firestore, so it has to get the ID from the ingredient being edited
        Map<String, Object> ingredientDetails = new HashMap<>();
        LocalDate expiry = ingredient.getBestBeforeDate();
        int year = expiry.getYear();
        int month = expiry.getMonthValue();
        int day = expiry.getDayOfMonth();
        ingredientDetails.put("description", ingredient.getDescription().toLowerCase());
        ingredientDetails.put("amount", Double.valueOf(ingredient.getAmount()));
        ingredientDetails.put("unit", ingredient.getMeasurementUnit().toLowerCase());
        ingredientDetails.put("year", Integer.valueOf(year));
        ingredientDetails.put("month", Integer.valueOf(month));
        ingredientDetails.put("day", Integer.valueOf(day));

        ingredientDetails.put("location", ingredient.getLocation().toString());
        ingredientDetails.put("category", ingredient.getCategory().toString());

        ingredientStorage.document(ingredient.getId()).set(ingredientDetails);
        Log.e("update ingredientInStor","ID: "+ingredient.getId());

    }


    /*************************** RECIPE METHODS ******************************/

    /**
     * Method for adding recipe to Firestore database.
     * @param recipe the recipe object to be added to database
     */
    public void addRecipeToStorage(Recipe recipe) {
        // hash map containing details EXCEPT list of ingredients in recipe
        Map<String, Object> recipeDetails = new HashMap<>();
        recipeDetails.put("prepTime", Integer.valueOf(recipe.getPrepTime()));
        recipeDetails.put("servings", Integer.valueOf(recipe.getNumOfServings()));
        recipeDetails.put("description", recipe.getDescription().toLowerCase());
        recipeDetails.put("instructions", recipe.getInstructions());
        recipeDetails.put("category", recipe.getCategory().toString());
        recipeDetails.put("prepUnitTime", recipe.getPrepUnitTime().toString());


        ArrayList<Map<String, Object>> ingredientsInRecipe = new ArrayList<>();
        for(IngredientInRecipe ingredientInRecipe: recipe.getIngredients()) {
            // hash map for each ingredient document in sub-collection IngredientsInRecipe
            Map<String, Object> ingredientDetails = new HashMap<>();
            ingredientDetails.put("amount", Double.valueOf(ingredientInRecipe.getAmount()));
            ingredientDetails.put("unit", ingredientInRecipe.getMeasurementUnit().toLowerCase());
            ingredientDetails.put("description", ingredientInRecipe.getDescription().toLowerCase());
            ingredientDetails.put("category", ingredientInRecipe.getCategory().toString());
            ingredientsInRecipe.add(ingredientDetails);

        }
        recipeDetails.put("ingredients", ingredientsInRecipe);
        recipeStorage.document(recipe.getDescription().toLowerCase()).set(recipeDetails);
    }

    /**
     * Method for removing Recipe from Firestore database. Method removes associated object.
     * @param recipe Object to be removed from the database
     */
    public void removeRecipeFromStorage(Recipe recipe) {
        //TODO: serious issue: removing doc does not remove subcollection, so need to go through and delete
        // each doc in sub-collection, but HOW??
        Log.d(TAG, "delete recipe from storage: "+ recipe.getDescription());
        recipeStorage.document(recipe.getDescription().toLowerCase()).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "Data has been deleted successfully!");
                    }
                });
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
        // hash map containing details EXCEPT list of ingredients in recipe
        Map<String, Object> recipeDetails = new HashMap<>();
        recipeDetails.put("prepTime", Integer.valueOf(recipe.getPrepTime()));
        recipeDetails.put("servings", Integer.valueOf(recipe.getNumOfServings()));
        recipeDetails.put("description", recipe.getDescription().toLowerCase());
        recipeDetails.put("instructions", recipe.getInstructions());
        recipeDetails.put("category", recipe.getCategory().toString());

        recipeStorage.document(recipe.getDescription().toLowerCase()).set(recipeDetails);

        for(IngredientInRecipe ingredientInRecipe: recipe.getIngredients()) {
            // hash map for each ingredient document in sub-collection IngredientsInRecipe
            Map<String, Object> ingredientDetails = new HashMap<>();
            ingredientDetails.put("amount", Double.valueOf(ingredientInRecipe.getAmount()));
            ingredientDetails.put("unit", ingredientInRecipe.getMeasurementUnit().toLowerCase());
            ingredientDetails.put("description", ingredientInRecipe.getDescription().toLowerCase());
            ingredientDetails.put("category", ingredientInRecipe.getCategory().toString());

            recipeStorage.document(recipe.getDescription()).collection("IngredientsInRecipe")
                    .document(ingredientInRecipe.getDescription().toLowerCase()).set(ingredientDetails);
        }
    }
}


