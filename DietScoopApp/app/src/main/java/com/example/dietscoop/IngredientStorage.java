package com.example.dietscoop;

import android.util.Log;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * This class encapsulates the Database interface class.
 * This class also acts as a container for the Storing of Ingredients in Storage.
 */
public class IngredientStorage {
    private ArrayList<IngredientInStorage> storage;
    Database db;


    public IngredientStorage() {
        db = new Database();
        storage = new ArrayList<IngredientInStorage>();
    }

    public void addIngredientToStorage(IngredientInStorage ingredientInStorage) {
        storage.add(ingredientInStorage); // -> Added this method @Marcos
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

    public void setupIngredientSnapshotListener() {
        setupIngredientSnapshotListener(null);
    }

    public void setupIngredientSnapshotListener(IngredientStorageAdapter adapter) {

        db.getIngredientStorageRef().addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        String TAG = "test";
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }
                        storage.clear();
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
                        if (adapter != null) { //This is for testing.
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}
