package com.example.dietscoop;

import android.util.Log;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FoodStorage {
    private ArrayList<IngredientInStorage> storage;
    Database db;

    public FoodStorage() {
        db = new Database();
        storage = new ArrayList<IngredientInStorage>();
    }

    public void addIngredientToStorage(IngredientInStorage ingredientInStorage) {
        db.addIngredientToStorage(ingredientInStorage);
    }

    public void removeIngredientFromStorage(IngredientInStorage ingredientInStorage) {
        db.removeIngredientFromStorage(ingredientInStorage);
    }

    public ArrayList<IngredientInStorage> getIngredientStorage() {
        return storage;
    }

    public void getIngredientStorageFromDatabase() {
        db.getIngredientStorage();
    }

    public void addIngredientSnapshotListener(IngredientStorageAdapter adapter) {

        db.getIngredientStorageRef().addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        String TAG = "test";
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }

                        for (QueryDocumentSnapshot doc : value) {
                            if (doc.getId() != null) {
                                Log.d(TAG, doc.getId() + " => " + doc.getData());
                                storage.add(new IngredientInStorage(doc.getId(), (String)doc.getData()
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
    }
}
