package com.example.dietscoop;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.firestore.CollectionReference;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDate;
import java.util.HashMap;

import java.util.Locale;
import java.util.Map;

public class Database {
    private static final String TAG = "testing";
    private FirebaseFirestore db;
    private CollectionReference ingredientStorage;
    private CollectionReference recipeStorage;
    private CollectionReference ingredientsInRecipe;

    public Database() {
        db = FirebaseFirestore.getInstance();
        ingredientStorage = db.collection("IngredientStorage");
        recipeStorage = db.collection("Recipes");
    }



    /*************************** INGREDIENT METHODS ******************************/


    /**
     * Getter for Firestore collection reference of Recipes collection. Does not query database
     * @return
     */
    public CollectionReference getIngredientCollectionRef() {
        return ingredientStorage;
    }

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
     * queries IngredientStorage collection in database
     */
    public void getIngredientStorage() {

        //was trying to get this to work
        //https://cloud.google.com/firestore/docs/samples/firestore-data-get-all-documents
//        // asynchronously retrieve all documents
//        ApiFuture<QuerySnapshot> future = db.collection("cities").get();
//        // future.get() blocks on response
//        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
//        for (QueryDocumentSnapshot document : documents) {
//            System.out.println(document.getId() + " => " + document.toObject(City.class));
//        }

        // queries ingredients in storage from DB
        ingredientStorage.get();
    }

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
     * @return
     */
    public CollectionReference getRecipeCollectionRef() {return this.recipeStorage;}

    public void getRecipeStorage() {
        // queries recipes collection in DB
        recipeStorage.get();
    }

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


