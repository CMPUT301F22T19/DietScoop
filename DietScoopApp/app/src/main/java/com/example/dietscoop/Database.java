package com.example.dietscoop;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.firestore.CollectionReference;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;

import java.util.Map;

public class Database {
    private static final String TAG = "testing";
    private FirebaseFirestore db;
    private CollectionReference ingredientStorage;
    private CollectionReference recipeStorage;
    private CollectionReference ingredientsInRecipe;

    public Database() {
//        db = FirebaseFirestore.getInstance();
//        ingredientStorage = db.collection("IngredientStorage");
//        recipeStorage = db.collection("Recipes");
    }

    private FirebaseFirestore db() {
        return FirebaseFirestore.getInstance();
    }

    public CollectionReference getIngredientStorageRef() {
        return db().collection("IngredientStorage");
    }
    public CollectionReference getRecipeStorageRef() {
        return recipeStorage;
    }

    public void addIngredientToStorage(IngredientInStorage ingredient) {
        Map<String, Object> ingredientDetails = new HashMap<>();
        Calendar expiry = ingredient.getBestBeforeDate();
        int year = expiry.get(Calendar.YEAR);
        int month = expiry.get(Calendar.MONTH);
        int day = expiry.get(Calendar.DATE);
        ingredientDetails.put("amount", Integer.valueOf(ingredient.getAmount()));
        ingredientDetails.put("unit", ingredient.getMeasurementUnit());
        ingredientDetails.put("year", Integer.valueOf(year));
        ingredientDetails.put("month", Integer.valueOf(month));
        ingredientDetails.put("day", Integer.valueOf(day));

        ingredientDetails.put("location", ingredient.getLocation().toString());
        ingredientDetails.put("category", ingredient.getCategory().toString());


        getIngredientStorageRef().document(ingredient.getDescription()).set(ingredientDetails);
    }
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

        // hacky way of getting all ingredients as query (none of them should have amount 0)
        //ingredientStorage.whereNotEqualTo("amount",0);
        getIngredientStorageRef().get();
    }

    public void removeIngredientFromStorage(IngredientInStorage ingredientInStorage) {
        Log.d(TAG, "delete ingredient from storage: "+ ingredientInStorage.getDescription());
        getIngredientStorageRef().document(ingredientInStorage.getDescription()).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "Data has been deleted successfully!");
                    }
                });
    }



    public void addRecipe(Recipe recipe) {
        Map<String, Object> recipeDetails = new HashMap<>();
        recipeDetails.put("prepMins", Integer.valueOf(recipe.getPrepTime()));
        recipeDetails.put("numOfServings", Integer.valueOf(recipe.getNumOfServings()));
        recipeDetails.put("description", recipe.getDescription());
        recipeDetails.put("instructions", recipe.getInstructions());
        recipeDetails.put("category", recipe.getCategory().toString());
        recipeStorage.document(recipe.getName()).set(recipeDetails);
        for(IngredientInRecipe ingredientInRecipe: recipe.getIngredients()) {
            Map<String, Object> ingredientDetails = new HashMap<>();
            ingredientDetails.put("amount", Integer.valueOf(ingredientInRecipe.getAmount()));
            ingredientDetails.put("unit", ingredientInRecipe.getMeasurementUnit());
            ingredientDetails.put("description", ingredientInRecipe.getDescription());
            ingredientDetails.put("category", ingredientInRecipe.getCategory().toString());

            recipeStorage.document(recipe.getName()).collection("IngredientsInRecipe")
                    .document(ingredientInRecipe.getDescription()).set(ingredientDetails);
        }

    }

    public void getRecipeStorage() {
        recipeStorage.get();
    }

}


