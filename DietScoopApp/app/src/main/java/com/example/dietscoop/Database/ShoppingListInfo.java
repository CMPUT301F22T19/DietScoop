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
                }
                updateShoppingList();
                adapter.notifyDataSetChanged();
            }
        });
    }

    public ArrayList<IngredientInRecipe> getShoppingList() {
        return shoppingList;
    }

    private void updateShoppingList() {

        // The has maps below map description_unit_category -> amount
        // we want this method to update the shoppingList arraylist so that it stores the items we
        // need to buy and the amount of each we need to buy

        // General idea: Iterate over the Hashmap of what we Need, check if it exists in what we have
        // if it does not, add the whole thing to the shopping list
        // if it does, add the difference to the shopping list

        // assume that the units are all in mg and mL in the hash maps

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

    private HashMap<String, Double> getIngredientsInRecipes() {

        ArrayList<IngredientInRecipe> allIngInMealPlans = new ArrayList<>();

        allIngInMealPlans.addAll(this.ingInMealPlans);

        for (Recipe rec : this.recipesInMealPlans) {
            allIngInMealPlans.addAll(rec.getIngredients());
        }

        return AggregateIngredients(allIngInMealPlans);

    }

    // WARNING: DESTROYS THE ARRAYLIST PASSED IN! MODIFIES EVERY INGREDIENT UNIT TO BE LOWEST SIZE
    private HashMap<String, Double> AggregateIngredients(ArrayList<IngredientInRecipe> ingredientsList) {

        HashMap<String, Double> aggregated = new HashMap<>();

        for (IngredientInRecipe ing : ingredientsList) {
            ing.setAmount(UnitConverter.convertIngredientUnit(ing.getAmount(), ing.getMeasurementUnit(), ));
        }

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
