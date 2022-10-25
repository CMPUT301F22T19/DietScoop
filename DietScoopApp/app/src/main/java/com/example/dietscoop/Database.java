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
public class Database {
    private static final String TAG = "testing";
    FirebaseFirestore db;
    CollectionReference ingredientStorage;
    CollectionReference recipes;
    CollectionReference ingredientsInRecipe;

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
    public ArrayList<IngredientInStorage> getIngredientStorage(IngredientStorageAdapter adapter) {
        ArrayList<IngredientInStorage> ingredientList = new ArrayList<IngredientInStorage>();
        ingredientStorage.whereEqualTo("amount",3);
        ingredientStorage
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }

                        for (QueryDocumentSnapshot doc : value) {
                            if (doc.getId() != null) {
                                Log.d(TAG, doc.getId() + " => " + doc.getData());
                                ingredientList.add(new IngredientInStorage(doc.getId(), (String)doc.getData()
                                        .get("unit"), ((Long)doc.getData().get("amount")).intValue(),
                                        ((Long)doc.getData().get("year")).intValue(),
                                        ((Long)doc.getData().get("month")).intValue(), ((Long)doc.getData().get("day")).intValue(),
                                        Location.stringToLocation(doc.getData().get("location").toString()),
                                        Category.stringToCategory(doc.getData().get("category").toString())));
                            }
                        }
                        adapter.notifyDataSetChanged();

                    }
                });
        //TODO: always returns list of size 0; never enters onComplete method
        return ingredientList;

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
}
