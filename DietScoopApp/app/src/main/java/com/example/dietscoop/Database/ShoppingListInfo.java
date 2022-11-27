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
import com.example.dietscoop.Data.Ingredient.UnitConverter;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShoppingListInfo {

    // Keep track of recipes and ingredients in any meal day
    // will be used to calculate shopping list
    private ArrayList<IngredientInRecipe> ingInMealPlans;

    private ArrayList<IngredientInRecipe> shoppingList;

    private IngredientStorage ingredientStorage;

    private Database db;

    public ShoppingListInfo() {
        this.db = new Database();
        this.ingInMealPlans = new ArrayList<>();

        this.ingredientStorage = new IngredientStorage();

        this.shoppingList = new ArrayList<>();
    }

    public void setUpSnapshotListeners(IngredientRecipeAdapter adapter) {

        setupIngredientsInMealDaysSnapshotListener(adapter);
        db.getIngredientsInMealDaysCollectionRef().get();

        setupRecipesInMealDaysSnapshotListener(adapter);
        db.getRecipesInMealDaysCollectionRef().get();

        ingredientStorage.setupIngredientSnapshotListener(adapter, this);
        ingredientStorage.getIngredientStorageFromDatabase();

    }

    private void setupRecipesInMealDaysSnapshotListener(IngredientRecipeAdapter adapter) {

        db.getRecipesInMealDaysCollectionRef().addSnapshotListener(new EventListener<QuerySnapshot>() {
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

                            db.getRecipeCollectionRef().document(parentRecipeID).addSnapshotListener((document, err) -> {

                                if (err != null) {
                                    Log.w("SNAPSHOT FAILED", "RECIPES IN MEAL DAYS SNAPSHOT LISTENER FAILED", err);
                                    return;
                                }

                                if (document.exists()) {
                                    ArrayList<String> ingredientRefs = (ArrayList<String>)document.get("ingredients");

                                    for (String ingRef : ingredientRefs) {
                                        db.getIngredientsInRecipesCollectionRef().document(ingRef).addSnapshotListener((ingDoc, e) -> {

                                            if (ingDoc.exists()) {
                                                IngredientInRecipe ing = new IngredientInRecipe(
                                                        ingDoc.getString("description"),
                                                        IngredientUnit.stringToUnit(ingDoc.getString("measurementUnit")),
                                                        ingDoc.getDouble("amount"),
                                                        IngredientCategory.stringToCategory(ingDoc.getString("category"))
                                                );

                                                ingInMealPlans.add(ing);
                                            }
                                        });
                                        db.getIngredientsInRecipesCollectionRef().document(ingRef).get();
                                    }

                                } else {
                                    db.getRecipesInMealDaysCollectionRef().document(doc.getDocument().getId()).delete();
                                }

                            });
                            db.getRecipeCollectionRef().document(parentRecipeID).get();
                            break;
                        case REMOVED:
                            Log.i("SNAPSHOT", "RECIPE REMOVED SUCCESSFULLY");
                        default:
                            throw new RuntimeException("OK WTF HOW IS IT ANYTHING OTHER THAN ADDED");

                    }
                }
                updateShoppingList();
                adapter.notifyDataSetChanged();
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
                            break;
                        default:
                            throw new RuntimeException("OK WTF HOW IS IT ANYTHING OTHER THAN ADDED");
                    }
                }
                updateShoppingList();
                adapter.notifyDataSetChanged();
            }
        });
    }

    public ArrayList<IngredientInRecipe> getShoppingList() {
        return shoppingList;
    }

    public void updateShoppingList() {

        this.shoppingList.clear();
        HashMap<String, Double> ingredientsWeHave = getIngredientsInStorage();
        HashMap<String, Double> ingredientsWeNeed = getIngredientsInRecipes();

        // Iterate through what we need
        for (Map.Entry<String, Double> entry: ingredientsWeNeed.entrySet()){
            String key = entry.getKey();
            Double amount = entry.getValue();

            String ingredientAttribute[] = key.split("_");

            String description = ingredientAttribute[0];
            String unit = ingredientAttribute[1];
            String category = ingredientAttribute[2];

            // Check if we already have that existing ingredient in storage
            if (!ingredientsWeHave.containsKey(key)) {
                // If we don't, add that ingredient, along with the amount and other attributes, to shopping list
                IngredientInRecipe newIngredient = new IngredientInRecipe(description, IngredientUnit.stringToUnit(unit), amount, IngredientCategory.stringToCategory(category));
                shoppingList.add(newIngredient);
            } else {
                // If we have an that ingredient, compare how much we need vs. how much we have
                Double newAmount = ingredientsWeNeed.get(key) - ingredientsWeHave.get(key);
                // Check if we need more than we have
                if (newAmount > 0) {
                    IngredientInRecipe newIngredient = new IngredientInRecipe(description, IngredientUnit.stringToUnit(unit), newAmount, IngredientCategory.stringToCategory(category));
                    shoppingList.add(newIngredient);
                }
            }

        }

    }

    /**
     * Returns a HashMap representing the ingredients currently in recipes with their amounts aggregated.
     * @return A hash map mapping description_unit_category -> amount
     */
    private HashMap<String, Double> getIngredientsInRecipes() {

        return AggregateIngredients(ingInMealPlans);

    }

    private HashMap<String, Double> AggregateIngredients(ArrayList<IngredientInRecipe> ingredientsList) {

        HashMap<String, Double> aggregated = new HashMap<>();

        for (IngredientInRecipe ing : ingredientsList) {
            Ingredient newIngredient = UnitConverter.normalizeAmountUnits(ing);
            String key = newIngredient.getDescription() + "_"
                    + newIngredient.getMeasurementUnit().name() +"_"
                    + newIngredient.getCategoryName();

            if (aggregated.containsKey(key)) {
                aggregated.put(key, aggregated.get(key) + newIngredient.getAmount());
            } else {
                aggregated.put(key, newIngredient.getAmount());
            }

        }

        return aggregated;

    }

    private HashMap<String, Double> getIngredientsInStorage() {

        ArrayList<IngredientInStorage> ingInStor = ingredientStorage.getIngredientStorage();
        ArrayList<IngredientInRecipe> ingInStor_rec = new ArrayList<>();

        for (IngredientInStorage ing : ingInStor) {
            ingInStor_rec.add(new IngredientInRecipe(
                    ing.getDescription(),
                    ing.getMeasurementUnit(),
                    ing.getAmount(),
                    ing.getCategory()
            ));
        }

        return AggregateIngredients(ingInStor_rec);

    }

}
