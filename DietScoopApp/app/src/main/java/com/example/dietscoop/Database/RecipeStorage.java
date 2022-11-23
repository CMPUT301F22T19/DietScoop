package com.example.dietscoop.Database;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dietscoop.Activities.IngredientListActivity;
import com.example.dietscoop.Activities.RecipeListActivity;
import com.example.dietscoop.Data.Comparators.IngredientComparator;
import com.example.dietscoop.Data.Comparators.RecipeComparator;
import com.example.dietscoop.Data.Ingredient.IngredientCategory;
import com.example.dietscoop.Data.Ingredient.IngredientInRecipe;
import com.example.dietscoop.Data.Ingredient.IngredientUnit;
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
        this.db = new Database();
        this.recipes = new ArrayList<>();
    }

    /**
     * Getter for ArrayList of recipes
     * @return ArrayList of Recipes
     */
    public ArrayList<Recipe> getRecipeStorage() {
        return this.recipes;
    }

    /**
     * Getter for Database recipes storage
     */
    public void getRecipeStorageFromDatabase() {
        this.db.getRecipeStorage();
    }

    /**
     * Add Recipe to database recipe storage
     * @param recipe Object to be added to database
     */
    public void addRecipeToStorage(Recipe recipe) {
        this.db.addRecipeToStorage(recipe);
    }

    /**
     * Remove recipe from database recipe storage
     * @param recipe Object to be removed from database
     */
    public void removeRecipeFromStorage(Recipe recipe) {
        this.db.removeRecipeFromStorage(recipe);
    }

    public com.google.firebase.firestore.ListenerRegistration setupRecipeSnapshotListener() {
        return(setupRecipeSnapshotListener(null));
    }

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

                    Recipe recipe = new Recipe(doc.getString("description"),
                            doc.getLong("prepTime").intValue(),
                            doc.getLong("servings").intValue(),
                            timeUnit.stringToTimeUnit(doc.getData().get("prepUnitTime").toString()),
                            recipeCategory.stringToRecipeCategory(doc.getData().get("category").toString()),
                            ingredients, doc.getString("instructions"));
                    recipe.setId(doc.getId());
                    recipes.add(recipe);

                    ArrayList<String> ingredientMaps = (ArrayList<String>)doc.getData().get("ingredients");
                    recipe.setIngredientRefs(ingredientMaps);

                    for (String ingredient: ingredientMaps) {
                            db.getIngredientsInRecipesCollectionRef().document(ingredient).addSnapshotListener((doc1, e1) -> {
                                String TAG1 = "BALLSSS";

                                if (e != null) {
                                    Log.w(TAG1, "Listen failed.", e);
                                    return;
                                }
                                if (doc1.exists()) {
                                    Log.i(TAG1, doc1.getData().toString());
                                    IngredientInRecipe ing = new IngredientInRecipe(doc1.getString("description"),
                                            IngredientUnit.stringToUnit(doc1.getString("measurementUnit")),doc1.getDouble("amount"),
                                            IngredientCategory.stringToCategory(doc1.getString("category")));
                                    ing.setId(doc1.getId());
                                    ing.setRecipeID(recipe.getId());
                                    ingredients.add(ing);
                                }
                                if (adapter!=null) {
                                    adapter.notifyDataSetChanged();
                                }
                            });
                            db.getIngredientsInRecipesCollectionRef().document(ingredient).get();
                    }
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

    public void addIngredientToIngredientsInRecipesCollection(IngredientInRecipe ingo) {
        db.addIngredientToIngredientsInRecipesCollection(ingo);
    }

    public void addIngredientsInRecipesSnapshotListener(Recipe recipe, RecyclerView.Adapter adapter) {
        db.getIngredientsInRecipesCollectionRef().addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value,
                                @Nullable FirebaseFirestoreException e) {
        String TAG = "test";
        if (e != null) {
            Log.w(TAG, "Listen failed.", e);
            return;
        }

            for (DocumentChange doc : value.getDocumentChanges()) {
                if (doc.getDocument().get("recipeID")==recipe.getId()) {
                    switch (doc.getType()) {
                        case ADDED:
                            if (recipe != null && adapter != null) {
                                recipe.addIngredientRef(doc.getDocument().getId());
                                IngredientInRecipe ingredient = new IngredientInRecipe(doc.getDocument().getString("description"),
                                        IngredientUnit.stringToUnit(doc.getDocument().getString("measurementUnit")), doc.getDocument().getDouble("amount"),
                                        IngredientCategory.stringToCategory(doc.getDocument().getString("category")));
                                ingredient.setId(doc.getDocument().getId());
                                recipe.addIngredient(ingredient);
                                adapter.notifyDataSetChanged();
                            }
                            Log.i("added new", doc.getDocument().getId() + doc.getDocument().getData().toString());
                            break;
                        case MODIFIED:
                            Log.i("modified new", doc.getDocument().getData().toString());
                            adapter.notifyDataSetChanged();
                            break;
                        case REMOVED:
                            Log.i("removed new", doc.getDocument().getData().toString());
                            adapter.notifyDataSetChanged();
                            break;
                    }
                }
            }

    }});}

    public void getAllIngredientsInRecipes() {db.getAllIngredientsInRecipes();}

    public void updateRecipeInStorage(Recipe recipe) {db.updateRecipeInStorage(recipe);}

    public void removeIngredientFromIngredientsInRecipesCollection(IngredientInRecipe ingredient) {
        db.removeIngredientFromIngredientsInRecipesCollection(ingredient);
    }

    // TODO: to be tested
    public void updateIngredientInIngredientsInRecipesCollection(IngredientInRecipe ingredient) {
        db.updateIngredientInIngredientsInRecipesCollection(ingredient);
    }

    /**
     * Method to sort Ingredients by given selection
     * @param sortBy selection to be sorted by
     */
    public void sortBy(RecipeListActivity.sortSelection sortBy) {
        switch (sortBy) {
            case TITLE:
                recipes.sort(new RecipeComparator.byRecipeTitle());
                break;
            case PREPTIME:
                recipes.sort(new RecipeComparator.byRecipePrepTime());
                break;
            case SERVING:
                recipes.sort(new RecipeComparator.byRecipeServingNumber());
                break;
            case CATEGORY:
                recipes.sort(new RecipeComparator.byRecipeCategory());
                break;
        }
    }
}
