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
                        Object balls = doc.getData().get("ingredients");
                        String [] ingredos = doc.getData().get("ingredients").toString().split(",")[0].split(",");
                        for (int i = 0; i<4;i++) {
                            
                        }
                        Log.i("balls",doc.getData().get("ingredients").toString().split(","));
//                        Log.i("balls",doc.getData().entrySet().toArray()[1].toString());
                        Log.i("balls",doc.getData().entrySet().toArray()[2].toString());
//                        Log.i("balls",doc.getData().entrySet().toArray()[3].toString());
                        Log.i("balls",balls.toString());
                        Log.i("preptime:",doc.getLong("prepTime").toString());
                        Log.i("preptime:",doc.getLong("prepTime").toString());
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
    }
}
