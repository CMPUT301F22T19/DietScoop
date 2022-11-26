package com.example.dietscoop.Database;

import android.util.Log;

import com.example.dietscoop.Activities.IngredientListActivity;
import com.example.dietscoop.Adapters.IngredientRecipeAdapter;
import com.example.dietscoop.Data.Ingredient.Ingredient;
import com.example.dietscoop.Data.Ingredient.IngredientCategory;
import com.example.dietscoop.Data.Comparators.IngredientComparator;
import com.example.dietscoop.Data.Ingredient.IngredientInStorage;
import com.example.dietscoop.Adapters.IngredientStorageAdapter;
import com.example.dietscoop.Data.Ingredient.IngredientUnit;
import com.example.dietscoop.Data.Ingredient.Location;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.QueryDocumentSnapshot;

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
        storage = new ArrayList<>();
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
        setupIngredientSnapshotListener((IngredientStorageAdapter)null);
    }

    /**
     * Method to initialize a listener for an ingredient snapshot
     * @param adapter Adapter that will notify Database if changed
     */
    // TODO: improve documentation for snapshot methods
    public void setupIngredientSnapshotListener(IngredientStorageAdapter adapter) {

        db.getIngredientCollectionRef().addSnapshotListener((value, e) -> {
            String TAG = "test";
            if (e != null) {
                Log.w(TAG, "Listen failed.", e);
                return;
            }
//            storage.clear();
//            for (QueryDocumentSnapshot doc : value) {
//                if (doc.getId() != null) {
//                    Log.d(TAG, doc.getId() + " => " + doc.getData() + " " + doc.getId());
//                    IngredientInStorage ingredient = new IngredientInStorage(doc.getString("description"),
//                            IngredientUnit.stringToUnit(doc.getData().get("measurementUnit").toString()),
//                            doc.getDouble("amount"),
//                            (doc.getLong("year")).intValue(),
//                            (doc.getLong("month")).intValue(), (doc.getLong("day")).intValue(),
//                            Location.stringToLocation(doc.getData().get("location").toString()),
//                            IngredientCategory.stringToCategory(doc.getData().get("category").toString()));
//                    storage.add(ingredient);
//                    ingredient.setId(doc.getId());
//                }
//            }
//            if (adapter!=null) {
//                adapter.notifyDataSetChanged();
//            }

            for (DocumentChange doc : value.getDocumentChanges()) {
                switch(doc.getType()) {
                    case ADDED:
                        Log.i("ADDED", "INGREDIENT ADDED TO DB");
                        IngredientInStorage ingredient = new IngredientInStorage(doc.getDocument().getString("description"),
                            IngredientUnit.stringToUnit(doc.getDocument().getData().get("measurementUnit").toString()),
                            doc.getDocument().getDouble("amount"),
                            (doc.getDocument().getLong("year")).intValue(),
                            (doc.getDocument().getLong("month")).intValue(), (doc.getDocument().getLong("day")).intValue(),
                            Location.stringToLocation(doc.getDocument().getData().get("location").toString()),
                            IngredientCategory.stringToCategory(doc.getDocument().getData().get("category").toString()));
                        storage.add(ingredient);
                        ingredient.setId(doc.getDocument().getId());
                        break;
                    case MODIFIED:
                        for (IngredientInStorage ing : storage) {
                            if (ing.getId().equals(doc.getDocument().getId())) {
                                ing.setLocation(Location.stringToLocation(doc.getDocument().getString("location")));
                                ing.setCategory(IngredientCategory.stringToCategory(doc.getDocument().getString("category")));
                                ing.setAmount(doc.getDocument().getDouble("amount"));
                                ing.setBestBeforeDate(doc.getDocument().getLong("year").intValue(),
                                                      doc.getDocument().getLong("month").intValue(),
                                                      doc.getDocument().getLong("day").intValue());
                                ing.setDescription(doc.getDocument().getString("description"));
                                ing.setMeasurementUnit(IngredientUnit.stringToUnit(doc.getDocument().getString("measurementUnit")));
                                break;
                            }
                        }
                        break;
                    case REMOVED:
                        for (IngredientInStorage ing : storage) {
                            if (ing.getId().equals(doc.getDocument().getId())) {
                                storage.remove(ing);
                                break;
                            }
                        }
                        break;
                }
            }

            if (adapter!=null) {
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void setupIngredientSnapshotListener(IngredientRecipeAdapter adapter) {

        db.getIngredientCollectionRef().addSnapshotListener((value, e) -> {
            String TAG = "test";
            if (e != null) {
                Log.w(TAG, "Listen failed.", e);
                return;
            }

            for (DocumentChange doc : value.getDocumentChanges()) {
                switch(doc.getType()) {
                    case ADDED:
                        Log.i("ADDED", "INGREDIENT ADDED TO DB");
                        IngredientInStorage ingredient = new IngredientInStorage(doc.getDocument().getString("description"),
                                IngredientUnit.stringToUnit(doc.getDocument().getData().get("measurementUnit").toString()),
                                doc.getDocument().getDouble("amount"),
                                (doc.getDocument().getLong("year")).intValue(),
                                (doc.getDocument().getLong("month")).intValue(), (doc.getDocument().getLong("day")).intValue(),
                                Location.stringToLocation(doc.getDocument().getData().get("location").toString()),
                                IngredientCategory.stringToCategory(doc.getDocument().getData().get("category").toString()));
                        storage.add(ingredient);
                        ingredient.setId(doc.getDocument().getId());
                        break;
                    case MODIFIED:
                        for (IngredientInStorage ing : storage) {
                            if (ing.getId().equals(doc.getDocument().getId())) {
                                ing.setLocation(Location.stringToLocation(doc.getDocument().getString("location")));
                                ing.setCategory(IngredientCategory.stringToCategory(doc.getDocument().getString("category")));
                                ing.setAmount(doc.getDocument().getDouble("amount"));
                                ing.setBestBeforeDate(doc.getDocument().getLong("year").intValue(),
                                        doc.getDocument().getLong("month").intValue(),
                                        doc.getDocument().getLong("day").intValue());
                                ing.setDescription(doc.getDocument().getString("description"));
                                ing.setMeasurementUnit(IngredientUnit.stringToUnit(doc.getDocument().getString("measurementUnit")));
                                break;
                            }
                        }
                        break;
                    case REMOVED:
                        for (IngredientInStorage ing : storage) {
                            if (ing.getId().equals(doc.getDocument().getId())) {
                                storage.remove(ing);
                                break;
                            }
                        }
                        break;
                }
            }

            adapter.notifyDataSetChanged();
        });
    }

    /**
     * Method to sort Ingredients by given selection
     * @param sortBy selection to be sorted by
     */
    public void sortBy(IngredientListActivity.sortSelection sortBy) {
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

    public IngredientInStorage getIngredientWithID(String id) {
        for (IngredientInStorage ing : storage) {
            if (ing.getId().equals(id)) {
                return ing;
            }
        }
        return null;
    }
}
