package com.example.dietscoop;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.firestore.CollectionReference;

import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;

import java.util.Map;

public class Database {
    private static final String TAG = "testing";
    private FirebaseFirestore db;
    private CollectionReference ingredientStorage;
    private CollectionReference recipes;
    private CollectionReference ingredientsInRecipe;

    public Database() {
        db = FirebaseFirestore.getInstance();
        ingredientStorage = db.collection("IngredientStorage");
        recipes = db.collection("Recipes");
    }

    public void addIngredientToStorage(IngredientInStorage ingredient) {
        Map<String, Object> data1 = new HashMap<>();
        LocalDate expiry = ingredient.getBestBeforeDate();
        int year = expiry.getYear();
        int month = expiry.getMonthValue();
        int day = expiry.getDayOfMonth();
        data1.put("amount", Integer.valueOf(ingredient.getAmount()));
        data1.put("unit", ingredient.getMeasurementUnit());
        data1.put("year", Integer.valueOf(year));
        data1.put("month", Integer.valueOf(month));
        data1.put("day", Integer.valueOf(day));

        data1.put("location", ingredient.getLocation().toString());
        data1.put("category", ingredient.getCategory().toString());


        ingredientStorage.document(ingredient.getDescription()).set(data1);
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
        ingredientStorage.get();
    }

    public void removeIngredientFromStorage(IngredientInStorage ingredientInStorage) {
        Log.d(TAG, "delete ingredient from storage: "+ ingredientInStorage.getDescription());
        ingredientStorage.document(ingredientInStorage.getDescription()).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "Data has been deleted successfully!");
                    }
                });
    }

    public CollectionReference getIngredientStorageRef() {
        return ingredientStorage;
    }
}
