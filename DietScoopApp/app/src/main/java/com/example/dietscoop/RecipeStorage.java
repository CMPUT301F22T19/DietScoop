package com.example.dietscoop;

import android.util.Log;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RecipeStorage {

    private ArrayList<Recipe> recipes;
    Database db;

    public RecipeStorage() {
        db = new Database();
        recipes = new ArrayList<Recipe>();
    }

    public ArrayList<Recipe> getRecipeStorage() {
        return recipes;
    }

    public void getRecipeStorageFromDatabase() {
        db.getRecipeStorage();
    }

    public void addRecipeToStorage(Recipe recipe) {
        db.addRecipeToStorage(recipe);
    }

    public void removeRecipeFromStorage(Recipe recipe) {
        db.removeRecipeFromStorage(recipe);
    }

    public void setupRecipeSnapshotListener() {
        setupRecipeSnapshotListener(null);
    }

    public void setupRecipeSnapshotListener(RecipeListAdapter adapter) {
        //TODO: How is this gonna work???
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
                        Recipe recipe = new Recipe(doc.getString("description"),
                                doc.getLong("prepTime").intValue(), doc.getLong("servings").intValue(),
                                timeUnit.stringToTimeUnit(doc.getData().get("prepUnitTime").toString()),
                                recipeCategory.stringToRecipeCategory(doc.getData().get("category").toString()),
                                ingredients);
                    }
                }
                if (adapter!=null) {
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}
