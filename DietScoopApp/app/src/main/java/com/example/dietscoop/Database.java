package com.example.dietscoop;

import android.util.Log;

import androidx.annotation.NonNull;

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
import java.util.Map;

public class Database {
    private static final String TAG = "testing";
    FirebaseFirestore db;
    CollectionReference ingredientStorage;
    CollectionReference recipes;
    CollectionReference ingredientsInRecipe;

    public Database() {
        db = FirebaseFirestore.getInstance();
        ingredientStorage = db.collection("IngredientStorage");

    }

    public void addIngredientToStorage(IngredientInStorage ingredient) {
        Map<String, Object> data1 = new HashMap<>();
        Calendar expiry = ingredient.getBestBeforeDate();
        int year = expiry.get(Calendar.YEAR);
        int month = expiry.get(Calendar.MONTH);
        int day = expiry.get(Calendar.DATE);
        data1.put("amount", Integer.valueOf(ingredient.getAmount());
        data1.put("unit", ingredient.getMeasurementUnit());
        data1.put("year", Integer.valueOf(year));
        data1.put("month", Integer.valueOf(month));
        data1.put("day", Integer.valueOf(day));


        ingredientStorage.document(ingredient.getDescription()).set(data1);
    }
    public ArrayList<IngredientInStorage> getIngredientStorage() {
        ingredientStorage
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<IngredientInStorage> ingredientList = new ArrayList<IngredientInStorage>();
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                Log.d(TAG, doc.getId() + " => " + doc.getData());
                                ingredientList.add(new IngredientInStorage(doc.getId(), doc.getData()
                                        .get("unit"), doc.getData().get("amount")));
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }
}
