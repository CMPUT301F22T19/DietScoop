package com.example.dietscoop.Database;

import android.util.Log;

import androidx.annotation.Nullable;

import com.example.dietscoop.Adapters.IngredientRecipeAdapter;
import com.example.dietscoop.Adapters.MealDayRecyclerAdapter;
import com.example.dietscoop.Data.Ingredient.Ingredient;
import com.example.dietscoop.Data.Ingredient.IngredientCategory;
import com.example.dietscoop.Data.Ingredient.IngredientInRecipe;
import com.example.dietscoop.Data.Ingredient.IngredientInStorage;
import com.example.dietscoop.Data.Ingredient.IngredientUnit;
import com.example.dietscoop.Data.Recipe.Recipe;
import com.example.dietscoop.Data.Recipe.RecipeInMealDay;
import com.example.dietscoop.Data.Recipe.recipeCategory;
import com.example.dietscoop.Data.Recipe.timeUnit;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;

public class ShoppingListInfo {

    // Keep track of recipes and ingredients in any meal day
    // will be used to calculate shopping list
    private ArrayList<RecipeInMealDay> recipesInMealPlans;
    private ArrayList<IngredientInRecipe> ingInMealPlans;

    private ArrayList<IngredientInRecipe> shoppingList;

    private RecipeStorage recipeStorage;
    private IngredientStorage ingredientStorage;

    private Database db;

    public ShoppingListInfo() {
        this.db = new Database();
        this.recipesInMealPlans = new ArrayList<>();
        this.ingInMealPlans = new ArrayList<>();

        this.recipeStorage = new RecipeStorage();
        this.ingredientStorage = new IngredientStorage();
    }

    public void setUpSnapshotListeners(IngredientRecipeAdapter adapter) {

        setupIngredientsInMealDaysSnapshotListener(adapter);
        setupRecipesInMealDaysSnapshotListener(adapter);

        recipeStorage.setupRecipeSnapshotListener(adapter);
        ingredientStorage.setupIngredientSnapshotListener(adapter);

    }

    private void setupRecipesInMealDaysSnapshotListener(IngredientRecipeAdapter adapter) {

        db.getIngredientsInMealDaysCollectionRef().addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null) {
                    Log.w("SNAPSHOT FAILED", "RECIPES IN MEAL DAYS SNAPSHOT LISTENER FAILED");
                    return;
                }

                for (DocumentChange doc : value.getDocumentChanges()) {
                    switch(doc.getType()) {
                        case ADDED:
                            String parentRecipeID = doc.getDocument().getString("parentRecipeID");
                            String mealDayID = doc.getDocument().getString("mealDayID");
                            int desiredServings = doc.getDocument().getLong("desiredNumOfServings").intValue();
                            String description = doc.getDocument().getString("description");

                            Recipe parentRecipe = recipeStorage.getRecipeWithID(parentRecipeID);

                            RecipeInMealDay recipe = new RecipeInMealDay(parentRecipe);
                            recipe.setMealdayID(mealDayID);
                            recipe.setScalingFactor((double)desiredServings / (double)parentRecipe.getNumOfServings());
                            recipe.setDescription(description);

                            recipesInMealPlans.add(recipe);
                            break;
                        case MODIFIED:
                            QueryDocumentSnapshot document = doc.getDocument();
                            for (RecipeInMealDay rec : recipesInMealPlans) {
                                if (rec.getId().equals(doc.getDocument().getId())) {
                                    rec.setParentRecipe(recipeStorage.getRecipeWithID(document.getString("parentRecipeID")));
                                    rec.setMealdayID(document.getString("mealDayID"));
                                    rec.setDescription(document.getString("description"));
                                    rec.setScalingFactor(document.getLong("desiredNumOfServings").doubleValue() / rec.getParentRecipe().getNumOfServings());
                                    break;
                                }
                            }
                            break;
                        case REMOVED:
                            recipesInMealPlans.removeIf(rec -> rec.getId().equals(doc.getDocument().getId()));
                            break;
                    }
                }

            }
        });

    }

    private void setupIngredientsInMealDaysSnapshotListener(IngredientRecipeAdapter adapter) {

        db.getIngredientsInMealDaysCollectionRef().addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w("SNAPSHOT FAILED", "INGREDIENTS IN MEAL DAYS SNAPSHOT LISTENER FAILED");
                    return;
                }

                for (DocumentChange doc : value.getDocumentChanges()) {
                    QueryDocumentSnapshot document = doc.getDocument();
                    switch (doc.getType()) {
                        case ADDED:
                            double amount = document.getDouble("amount");
                            String catString = document.getString("category");
                            IngredientCategory cat = IngredientCategory.stringToCategory(catString);
                            String description = document.getString("description");
                            String unitString = document.getString("measurementUnit");
                            IngredientUnit unit = IngredientUnit.stringToUnit(unitString);

                            IngredientInRecipe ing = new IngredientInRecipe(description, unit, amount, cat);
                            ingInMealPlans.add(ing);
                            break;
                        case REMOVED:
                            ingInMealPlans.removeIf(ingInRec -> ingInRec.getId().equals(document.getId()));
                            break;
                        case MODIFIED:
                            for (IngredientInRecipe rec : ingInMealPlans) {
                                if (rec.getId().equals(document.getId())) {
                                    double newAmount = document.getDouble("amount");
                                    String newCatString = document.getString("category");
                                    IngredientCategory newCat = IngredientCategory.stringToCategory(newCatString);
                                    String newDescription = document.getString("description");
                                    String newUnitString = document.getString("measurementUnit");
                                    IngredientUnit newUnit = IngredientUnit.stringToUnit(newUnitString);

                                    rec.setAmount(newAmount);
                                    rec.setCategory(newCat);
                                    rec.setDescription(newDescription);
                                    rec.setMeasurementUnit(newUnit);

                                    break;
                                }
                            }
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }

    public ArrayList<IngredientInRecipe> getShoppingList() {
        updateShoppingList();
        return shoppingList;
    }

    private void updateShoppingList() {


    }


}
