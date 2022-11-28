package com.example.dietscoop.Database;

import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.example.dietscoop.Data.Ingredient.IngredientCategory;
import com.example.dietscoop.Data.Ingredient.IngredientInMealDay;
import com.example.dietscoop.Data.Ingredient.IngredientInRecipe;
import com.example.dietscoop.Data.Ingredient.IngredientUnit;
import com.example.dietscoop.Data.Meal.MealDay;
import com.example.dietscoop.Data.Recipe.RecipeInMealDay;
import com.example.dietscoop.Data.Recipe.recipeCategory;
import com.example.dietscoop.Data.Recipe.timeUnit;
import com.google.firebase.firestore.DocumentChange;
import java.time.LocalDate;
import java.util.ArrayList;

import kotlin.time.MeasureTimeKt;

/**
 * This class wraps a Database instance and a list of MealDay objects representing the meal plan.
 * Contains methods to query database and set up database snapshot listeners.
 */
public class MealPlanStorage {

    private Database db;
    private ArrayList<MealDay> mealPlan;

    /**
     * Creates Database instance and empty list (meal plan)
     */
    public MealPlanStorage() {
        db = new Database();
        mealPlan = new ArrayList<>();
    }

    /**
     * Getter for meal plan list
     * @return ArrayList representing meal plan
     */
    public ArrayList<MealDay> getMealPlan() {
        return this.mealPlan;
    }

    /**
     * Queries database for all documents in meal plan
     */
    public void getMealPlanFromDB() {db.getMealPlanFromDB();}

    /**
     * Adds meal day object to database
     * @param mealDay meal day to be added
     */
    public void addMealDayToMealPlan(MealDay mealDay) {
        db.addMealDayToMealPlan(mealDay);
    }

    /**
     * Updates existing meal day in database
     * @param mealDay meal day to be updated
     */
    public void updateMealDayInMealPlan(MealDay mealDay) {
        db.updateMealDayInMealPlan(mealDay);
    }

    /**
     * Deletes meal day from database. Deletes all contained ingredients and recipes from their collections.
     * @param mealDay meal day to be deleted
     */
    public void removeMealDayFromMealPlan(MealDay mealDay) {
        for (IngredientInMealDay i: mealDay.getIngredientInMealDays()) {
            db.removeIngredientFromIngredientsInMealDaysCollection(i);
        }
        for (RecipeInMealDay r: mealDay.getRecipeInMealDays()) {
            db.removeRecipeFromRecipesInMealDaysCollection(r);
        }
        db.removeMealDayFromMealPlan(mealDay);
    }

    /**
     * Sets snapshot listener on MealPlan collection in order to populate meal plan with all meal days
     * and contained ingredients and recipes
     * @param adapter adapter to be notified when all data is received from database
     */
    public void addMealPlanSnapshotListener(RecyclerView.Adapter adapter) {
        db.getMealPlan().addSnapshotListener((value, e) -> {
            String TAG = "test";
            if (e != null) {
                Log.w(TAG, "Listen failed.", e);
                return;
            }
            mealPlan.clear();
            for (DocumentChange doc : value.getDocumentChanges()) {

                    switch (doc.getType()) {
                        case ADDED:

                                ArrayList<String> ingredientIDs = (ArrayList<String>)doc.getDocument().get("ingredients");
                                ArrayList<String> recipeIDs = (ArrayList<String>)doc.getDocument().get("recipes");
                                MealDay mealDayToAdd = new MealDay(LocalDate.of(doc.getDocument().getLong("year").intValue(),
                                        doc.getDocument().getLong("month").intValue(),
                                        doc.getDocument().getLong("day").intValue()));
                                mealDayToAdd.setId(doc.getDocument().getId());
                                mealPlan.add(mealDayToAdd);


                                // grab ingredients
                                for (String ingredient: ingredientIDs) {
                                    db.getIngredientsInMealDaysCollectionRef().document(ingredient).addSnapshotListener((doc1, e1) -> {
                                        String TAG1 = "not good";

                                        if (e != null) {
                                            Log.w(TAG1, "Listen failed.", e);
                                            return;
                                        }
                                        if (doc1.exists()) {
                                            Log.i(TAG1, doc1.getData().toString());
                                            IngredientInMealDay ing = new IngredientInMealDay(doc1.getString("description"),
                                                    IngredientUnit.stringToUnit(doc1.getString("measurementUnit")),doc1.getDouble("amount"),
                                                    IngredientCategory.stringToCategory(doc1.getString("category")),
                                                    doc1.getString("parentIngredientID"));
                                            ing.setId(doc1.getId());
                                            ing.setMealdayID(mealDayToAdd.getId());
                                            mealDayToAdd.addIngredientInMealDay(ing);
                                        }

                                    });
                                    db.getIngredientsInMealDaysCollectionRef().document(ingredient).get();
                                }

                                // grab recipes
                                for (String recipe: recipeIDs) {
                                    db.getRecipesInMealDaysCollectionRef().document(recipe).addSnapshotListener((doc1, e1) -> {
                                        String TAG1 = "not good";

                                        if (e1 != null) {
                                            Log.w(TAG1, "Listen failed.", e1);
                                            return;
                                        }
                                        if (doc1.exists()) {
                                            Log.i(TAG1, doc1.getData().toString());
                                            RecipeInMealDay rec = new RecipeInMealDay(doc1.getString("description"));
                                            rec.setParentRecipeID(doc1.getString("parentRecipeID"));
                                            rec.setDesiredNumOfServings(doc1.getDouble("desiredNumOfServings").doubleValue());
                                            rec.setId(doc1.getId());
                                            rec.setMealdayID(mealDayToAdd.getId());


                                            db.getRecipeCollectionRef().document(doc1.getString("parentRecipeID"))
                                                    .addSnapshotListener((doc4, e4) -> {
                                                        String TAG4 = "not good";

                                                        if (e4 != null) {
                                                            Log.w(TAG4, "Listen failed.", e4);
                                                            return;
                                                        }
                                                        if (doc4.exists()) {
                                                            rec.setDescription(doc4.getString("description"));
                                                            mealDayToAdd.addRecipeInMealDay(rec);
                                                        } else {
                                                            db.getRecipesInMealDaysCollectionRef().document(doc1.getId()).delete();
                                                        }

                                                    });
                                            db.getRecipeCollectionRef().document(doc1.getString("parentRecipeID")).get();

                                        }

                                    });
                                    db.getRecipesInMealDaysCollectionRef().document(recipe).get();
                                }

                            break;

                        case MODIFIED:
                            for (MealDay mealDay: mealPlan) {
                                if (mealDay.getId().equals(doc.getDocument().getId())) {
                                    mealDay.setDate(LocalDate.of(doc.getDocument().getLong("year").intValue(),
                                            doc.getDocument().getLong("month").intValue(),
                                            doc.getDocument().getLong("day").intValue()));
                                    mealDay.setIngredientIDs((ArrayList<String>)doc.getDocument().get("ingredients"));
                                    mealDay.setRecipeIDs((ArrayList<String>)doc.getDocument().get("recipes"));

                                    ArrayList<String> ingredientModIDs = (ArrayList<String>)doc.getDocument().get("ingredients");
                                    ArrayList<String> recipeModIDs = (ArrayList<String>)doc.getDocument().get("recipes");

                                    for (String ingredient: ingredientModIDs) {
                                        db.getIngredientsInMealDaysCollectionRef().document(ingredient).addSnapshotListener((doc1, e1) -> {
                                            String TAG1 = "not good";

                                            if (e != null) {
                                                Log.w(TAG1, "Listen failed.", e);
                                                return;
                                            }
                                            if (doc1.exists()) {
                                                Log.i(TAG1, doc1.getData().toString());
                                                IngredientInMealDay ing = new IngredientInMealDay(doc1.getString("description"),
                                                        IngredientUnit.stringToUnit(doc1.getString("measurementUnit")),doc1.getDouble("amount"),
                                                        IngredientCategory.stringToCategory(doc1.getString("category")),
                                                        doc1.getString("parentIngredientID"));
                                                ing.setId(doc1.getId());
                                                ing.setMealdayID(mealDay.getId());
                                                mealDay.addIngredientInMealDay(ing);
                                            }

                                        });
                                        db.getIngredientsInMealDaysCollectionRef().document(ingredient).get();
                                    }

                                    // grab recipes
                                    for (String recipe: recipeModIDs) {
                                        db.getRecipesInMealDaysCollectionRef().document(recipe).addSnapshotListener((doc1, e1) -> {
                                            String TAG1 = "not good";

                                            if (e1 != null) {
                                                Log.w(TAG1, "Listen failed.", e1);
                                                return;
                                            }
                                            if (doc1.exists()) {
                                                Log.i(TAG1, doc1.getData().toString());
                                                ArrayList<IngredientInRecipe> ingredients = new ArrayList<>();
                                                RecipeInMealDay rec = new RecipeInMealDay(doc1.getString("description"));
                                                rec.setParentRecipeID(doc1.getString("parentRecipeID"));
                                                rec.setDesiredNumOfServings(doc1.getDouble("desiredNumOfServings").doubleValue());
                                                rec.setId(doc1.getId());
                                                rec.setMealdayID(mealDay.getId());
                                                mealDay.addRecipeInMealDay(rec);

                                                for (String ingredientInRec: (ArrayList<String>)doc1.get("ingredients")) {
                                                    db.getIngredientsInRecipesCollectionRef().document(ingredientInRec).addSnapshotListener((doc2, e2) -> {
                                                        String TAG2 = "not good";

                                                        if (e2 != null) {
                                                            Log.w(TAG2, "Listen failed.", e2);
                                                            return;
                                                        }
                                                        if (doc2.exists()) {
                                                            Log.i(TAG2, doc2.getData().toString());
                                                            IngredientInRecipe ing = new IngredientInRecipe(doc2.getString("description"),
                                                                    IngredientUnit.stringToUnit(doc2.getString("measurementUnit")),doc2.getDouble("amount"),
                                                                    IngredientCategory.stringToCategory(doc2.getString("category")));
                                                            ing.setId(doc2.getId());
                                                            ing.setRecipeID(rec.getId());
                                                            ingredients.add(ing);
                                                        }
                                                    });
                                                    db.getIngredientsInRecipesCollectionRef().document(ingredientInRec).get();
                                                }
                                            }


                                        });
                                        db.getRecipesInMealDaysCollectionRef().document(recipe).get();
                                    }
                                    break;
                                }
                            }
                            break;

                        case REMOVED:
                            break;
                    }

                }
            adapter.notifyDataSetChanged();
        });
    }

    /**
     * Adds ingredient to collection containing all ingredients in meal days
     * @param ingredient ingredient to be added
     */
    public void addIngredientToIngredientsInMealDaysCollection(IngredientInMealDay ingredient) {
        db.addIngredientToIngredientsInMealDaysCollection(ingredient);
    }

    /**
     * Deletes ingredient from collection containing all ingredients in meal days
     * @param ingredient ingredient to be deleted from database
     */
    public void removeIngredientFromIngredientsInMealDaysCollection(IngredientInMealDay ingredient) {
        db.removeIngredientFromIngredientsInMealDaysCollection(ingredient);
    }

    /**
     * Updates recipe in collection containing all recipes in meal days
     * @param recipeInMealDay recipe to be added to database
     */
    public void addRecipeToRecipesInMealDaysCollection(RecipeInMealDay recipeInMealDay) {
        db.addRecipeToRecipesInMealDaysCollection(recipeInMealDay);
    }

    /**
     * Deletes recipe from collection containing all recipes in meal days
     * @param recipeInMealDay recipe to be deleted from database
     */
    public void removeRecipeFromRecipesInMealDaysCollection(RecipeInMealDay recipeInMealDay) {
        db.removeRecipeFromRecipesInMealDaysCollection(recipeInMealDay);
    }





}
