package com.example.dietscoop.Database;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dietscoop.Data.Ingredient.IngredientCategory;
import com.example.dietscoop.Data.Ingredient.IngredientInRecipe;
import com.example.dietscoop.Data.Recipe.Recipe;
import com.example.dietscoop.Data.Recipe.recipeCategory;
import com.example.dietscoop.Data.Recipe.timeUnit;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

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
        recipes = new ArrayList<>();
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

    /**
     * Method to listen for snapshot with recipe adapter passed in.
     * @param adapter adapter notifying the database of change in recipes
     */
    public com.google.firebase.firestore.ListenerRegistration setupRecipeSnapshotListener
            (RecyclerView.Adapter adapter) {
        return db.getRecipeCollectionRef().addSnapshotListener((value, e) -> {
            String TAG = "test";
            if (e != null) {
                Log.w(TAG, "Listen failed.", e);
                return;
            }
            recipes.clear();

            for (QueryDocumentSnapshot doc : value) {
                if (doc.getId() != null) {
                    Log.d(TAG, doc.getId() + " => " + doc.getData() + " " + doc.getId());
                    ArrayList<IngredientInRecipe> ingredients = new ArrayList<>();
                    ArrayList<HashMap> ingredientMaps = (ArrayList<HashMap>)doc.getData().get("ingredients");
                    for (HashMap ingredient: ingredientMaps) {
                        ingredients.add(new IngredientInRecipe(ingredient.get("description").toString(),
                                (ingredient.get("unit").toString()),
                                ((Long)ingredient.get("amount")).doubleValue(),
                                IngredientCategory.stringToCategory(ingredient.get("category").toString())));
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
        });
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

    public void addIngredientBlah(IngredientInRecipe ingo) {
        db.addIngredientToIngredientsInRecipesCollection(ingo);
    }
}
