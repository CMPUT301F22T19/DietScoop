package com.example.dietscoop;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import com.google.api.core.ApiFuture;
//import com.google.api.core.ApiFutureCallback;
//import com.google.api.core.ApiFutures;

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
        Calendar expiry = ingredient.getBestBeforeDate();
        int year = expiry.get(Calendar.YEAR);
        int month = expiry.get(Calendar.MONTH);
        int day = expiry.get(Calendar.DATE);
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
        //ArrayList<IngredientInStorage> ingredientList = new ArrayList<IngredientInStorage>();

        //was trying to get this to work
        //https://cloud.google.com/firestore/docs/samples/firestore-data-get-all-documents
//        // asynchronously retrieve all documents
//        ApiFuture<QuerySnapshot> future = db.collection("cities").get();
//        // future.get() blocks on response
//        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
//        for (QueryDocumentSnapshot document : documents) {
//            System.out.println(document.getId() + " => " + document.toObject(City.class));
//        }


        ingredientStorage.whereNotEqualTo("amount",0);

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
