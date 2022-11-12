package com.example.dietscoop.Database;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dietscoop.Data.Ingredient.IngredientCategory;
import com.example.dietscoop.Data.Ingredient.IngredientInRecipe;
import com.example.dietscoop.Data.Recipe.Recipe;
import com.example.dietscoop.Data.Recipe.recipeCategory;
import com.example.dietscoop.Data.Recipe.timeUnit;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
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
                    ArrayList<DocumentReference> ingredientMaps = (ArrayList<DocumentReference>)doc.getData().get("ingredients");
                    for (DocumentReference ingredient: ingredientMaps) {
                            ingredient.addSnapshotListener((doc1, e1) -> {
                                String TAG1 = "BALLSSS";
                                if (e != null) {
                                    Log.w(TAG1, "Listen failed.", e);
                                    return;
                                }
                                if (doc1.getId() != null) {
                                    Log.i(TAG1, doc1.getData().toString());
                                    ingredients.add(new IngredientInRecipe(doc1.getString("description"),
                                            doc1.getString("measurementUnit"),doc1.getDouble("amount"),
                                            IngredientCategory.stringToCategory(doc1.getString("category"))));
                                }
                                if (adapter!=null) {
                                    adapter.notifyDataSetChanged();
                                }
                            });
                            ingredient.get();

//                        String descr = ingredient.get().getResult().get("description").toString();
//                        String unit = ingredient.get().getResult().get("unit").toString();
//                        Double amount = ((Long)ingredient.get().getResult().get("amount")).doubleValue();
//                        IngredientCategory cat = IngredientCategory.stringToCategory(ingredient.get().getResult().get("category").toString());
//                        ingredients.add(new IngredientInRecipe(descr,unit, amount,cat));
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

    public void addDummySnapshotListener() {db.setupDummyAllIngredientsInRecipesSnapshotListener();}

//    public void addRealSnapshotListener() {
//        addRealSnapshotListener(null,null);
//    }
    public void addRealSnapshotListener(Recipe recipe, RecyclerView.Adapter adapter, boolean flag) {
        db.getIngredientsInRecipesCollectionRef().addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value,
                                @Nullable FirebaseFirestoreException e) {
        String TAG = "test";
        if (e != null) {
            Log.w(TAG, "Listen failed.", e);
            return;
        }
        if (value.getDocumentChanges().size()==1) {
            for (DocumentChange doc : value.getDocumentChanges()) {
                switch (doc.getType()) {
                    case ADDED:
                        if (recipe != null && adapter != null) {
                            recipe.addIngredientID(doc.getDocument().getId());
                            IngredientInRecipe ingredient = new IngredientInRecipe(doc.getDocument().getString("description"),
                                    doc.getDocument().getString("unit"), doc.getDocument().getDouble("amount"),
                                    IngredientCategory.stringToCategory(doc.getDocument().getString("category")));
                            ingredient.setId(doc.getDocument().getId());
                            recipe.addIngredient(ingredient);
                            adapter.notifyDataSetChanged();
                        }
                        Log.i("added new", doc.getDocument().getId() + doc.getDocument().getData().toString());
                        break;
                    case MODIFIED:
                        Log.i("modified new", doc.getDocument().getData().toString());
                        break;
                    case REMOVED:
                        Log.i("removed new", doc.getDocument().getData().toString());
                        break;
                }
            }
        }
        //final boolean flag = true;
    }});}

    public void getAllIngsInRecipes() {db.getAllIngredientsInRecipes();}
}
