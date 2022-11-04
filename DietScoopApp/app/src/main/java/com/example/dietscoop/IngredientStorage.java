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
 * This class also acts as a container for the Storing of IngredientsInStorage.
 * @implNote IngredientInStorage is a defined class. Database is a separate class entity.
 */
public class IngredientStorage {
    private ArrayList<IngredientInStorage> storage;
    Database db;


    /**
     * Constructor for ingredient in storage. Creates Database object.
     */
    public IngredientStorage() {
        db = new Database();
        storage = new ArrayList<IngredientInStorage>();
    }

    /**
     * Method for adding an ingredient to storage.
     * @param ingredientInStorage Ingredient in storage to be added to Database
     */
    public void addIngredientToStorage(IngredientInStorage ingredientInStorage) {
        //storage.add(ingredientInStorage); // -> Added this method @Marcos
        // ^^ this happens whenever something is added to DB (in snapshot listener).
        // By doing this, we won't know if something actually gets added to DB

        db.addIngredientToStorage(ingredientInStorage);
    }

    /**
     * Method for removing an ingredient in storage.
     * @param ingredientInStorage IngredientInStorage to be removed from the database.
     */
    public void removeIngredientFromStorage(IngredientInStorage ingredientInStorage) {
        db.removeIngredientFromStorage(ingredientInStorage);
    }

    /**
     * Getter for ingredientStorage
     * @return ArrayList of IngredientInStorage
     */
    public ArrayList<IngredientInStorage> getIngredientStorage() {
        return storage;
    }

    /**
     * Getter wrapper method for accessing IngredientStorage portion of database.
     */
    public void getIngredientStorageFromDatabase() {
        db.getIngredientStorage();
    }

    /**
     * Method to update an IngredientInStorage within the database.
     * @param ingredient ingredient to be updated in Database
     */
    public void updateIngredientInStorage(IngredientInStorage ingredient) {
        db.updateIngredientInStorage(ingredient);
    }

    /**
     * Method to initialize a listen for an Ingredient Snapshot
     */
    public void setupIngredientSnapshotListener() {
        setupIngredientSnapshotListener(null);
    }

    /**
     * Method to initialize a listener for an ingredient snapshot
     * @param adapter Adapter that will notify Database if changed
     */
    // TODO: improve documentation for snapshot methods
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

    /**
     * Method to sort Ingredients by given selection
     * @param sortBy selection to be sorted by
     */
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
