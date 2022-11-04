package com.example.dietscoop;

import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

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
        //storage.add(ingredientInStorage); // -> Added this method @Marcos
        // ^^ this happens whenever something is added to DB (in snapshot listener).
        // By doing this, we won't know if something actually gets added to DB

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

    public void updateIngredientInStorage(IngredientInStorage ingredient) {
        db.updateIngredientInStorage(ingredient);
    }

    public void setupIngredientSnapshotListener() {
        setupIngredientSnapshotListener(null);
    }

    public void setupIngredientSnapshotListener(IngredientStorageAdapter adapter) {

        db.getIngredientCollectionRef().addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
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
                                Log.d(TAG, doc.getId() + " => " + doc.getData() + " " + doc.getId());
                                IngredientInStorage ingredient = new IngredientInStorage(doc.getString("description"),
                                        doc.getString("unit"), doc.getDouble("amount"),
                                        (doc.getLong("year")).intValue(),
                                        (doc.getLong("month")).intValue(), (doc.getLong("day")).intValue(),
                                        Location.stringToLocation(doc.getData().get("location").toString()),
                                        Category.stringToCategory(doc.getData().get("category").toString()));
                                storage.add(ingredient);
                                ingredient.setId(doc.getId());
                            }
                        }
                        if (adapter!=null) {
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sortBy(sortIngredientByFragment.selection sortBy) {
        switch (sortBy) {
            case NAME:
                storage.sort(new IngredientComparator.byName());
                break;
            case DATE:
                storage.sort(new IngredientComparator.byBestBefore());
                break;
            case CATEGORY:
                storage.sort(new IngredientComparator.byCategory());
                break;
            case LOCATION:
                storage.sort(new IngredientComparator.byLocation());
                break;
        }
    }
}
