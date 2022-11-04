package com.example.dietscoop;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * This class encapsulates the Database interface class.
 * This class also acts as a container for the Storing of Recipes.
 */
public class RecipeStorage implements Serializable {

    private ArrayList<Recipe> recipes;
    private Database db;

    /**
     * Constructor for a recipe in storage. Initializes Database class.
     */
    public RecipeStorage() {
        db = new Database();
        recipes = new ArrayList<Recipe>();
    }

    /**
     * Getter for ArrayList of recipes
     * @return ArrayList of Recipes
     */
    public ArrayList<Recipe> getRecipeStorage() {
        return recipes;
    }

    /**
     * Getter for Database recipes storage
     */
    public void getRecipeStorageFromDatabase() {
        db.getRecipeStorage();
    }

    /**
     * Add Recipe to database recipe storage
     * @param recipe Object to be added to database
     */
    public void addRecipeToStorage(Recipe recipe) {
        db.addRecipeToStorage(recipe);
    }

    /**
     * Remove recipe from database recipe storage
     * @param recipe Object to be removed from database
     */
    public void removeRecipeFromStorage(Recipe recipe) {
        db.removeRecipeFromStorage(recipe);
    }

//    public com.google.firebase.firestore.ListenerRegistration setupRecipeSnapshotListener() {
//        return(setupRecipeSnapshotListener(null));
//    }

    public com.google.firebase.firestore.ListenerRegistration setupRecipeSnapshotListener
            (RecyclerView.Adapter adapter) {
    /**
     * Initialize listener for snapshots of the recipes in the database
     */
    public void setupRecipeSnapshotListener() {
        setupRecipeSnapshotListener(null);
    }

    /**
     * Method to listen for snapshot with recipe adapter passed in.
     * @param adapter adapter notifying the database of change in recipes
     */
    public void setupRecipeSnapshotListener(RecipeListAdapter adapter) {
        //TODO: How is this gonna work???
        com.google.firebase.firestore.ListenerRegistration registration =
                db.getRecipeCollectionRef().addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value,
                                @Nullable FirebaseFirestoreException e) {
                String TAG = "test";
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }
                recipes.clear();
                ArrayList<IngredientInRecipe> ingredients = new ArrayList<>();
                for (QueryDocumentSnapshot doc : value) {
                    if (doc.getId() != null) {
                        Log.d(TAG, doc.getId() + " => " + doc.getData() + " " + doc.getId());

                        ArrayList<HashMap> ingredientMaps = (ArrayList<HashMap>)doc.getData().get("ingredients");
                        for (HashMap ingredient: ingredientMaps) {
                            ingredients.add(new IngredientInRecipe(ingredient.get("description").toString(),
                                    (ingredient.get("unit").toString()),
                                    (Double) ingredient.get("amount"),
                                    Category.stringToCategory(ingredient.get("category").toString())));
                        }

                        Recipe recipe = new Recipe(doc.getString("description"),
                                doc.getLong("prepTime").intValue(),
                                doc.getLong("servings").intValue(),
                                timeUnit.stringToTimeUnit(doc.getData().get("prepUnitTime").toString()),
                                recipeCategory.stringToRecipeCategory(doc.getData().get("category").toString()),
                                ingredients, doc.getString("instructions"));
                        recipes.add(recipe);
                    }
                }

                if (adapter!=null) {
                    adapter.notifyDataSetChanged();
                }
            }
        });
        return registration;
    }

    public Recipe getRecipeByDesc(String description) {
        for (Recipe recipe : recipes) {
            Log.i("CHECKED NAME", recipe.getDescription());
            if (recipe.getDescription().equals(description)) {
                return recipe;
            }
        }

        return null;
    }
}
