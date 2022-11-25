package com.example.dietscoop.Database;

import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.example.dietscoop.Data.Ingredient.IngredientCategory;
import com.example.dietscoop.Data.Ingredient.IngredientInMealDay;
import com.example.dietscoop.Data.Ingredient.IngredientInRecipe;
import com.example.dietscoop.Data.Ingredient.IngredientUnit;
import com.example.dietscoop.Data.Meal.Meal;
import com.example.dietscoop.Data.Meal.MealDay;
import com.example.dietscoop.Data.Meal.MealPlan;
import com.example.dietscoop.Data.Recipe.RecipeInMealDay;
import com.example.dietscoop.Data.Recipe.recipeCategory;
import com.example.dietscoop.Data.Recipe.timeUnit;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.checkerframework.checker.units.qual.A;

import java.time.LocalDate;
import java.util.ArrayList;

import kotlin.time.MeasureTimeKt;

// TODO: CASCADE CHANGES TO ANY RECIPE MEAL PLANS
public class MealPlanStorage {

    private Database db;
    private ArrayList<MealDay> mealPlan;

    public MealPlanStorage() {
        db = new Database();
        mealPlan = new ArrayList<>();
    }

    public ArrayList<MealDay> getMealPlan() {
        return this.mealPlan;
    }

    public void getMealPlanFromDB() {db.getMealPlanFromDB();}

    public void addMealDayToMealPlan(MealDay mealDay) {
        db.addMealDayToMealPlan(mealDay);
    }

    public void updateMealDayInMealPlan(MealDay mealDay) {
        db.updateMealDayInMealPlan(mealDay);
    }

    public void removeMealDayFromMealPlan(MealDay mealDay) {
        db.removeMealDayFromMealPlan(mealDay);
    }

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
//                                mealDayToAdd.setIngredientIDs(ingredientIDs);
//                                mealDayToAdd.setRecipeIDs(recipeIDs);
                                mealPlan.add(mealDayToAdd);


                                // grab ingredients
                                for (String ingredient: ingredientIDs) {
                                    db.getIngredientsInMealDaysCollectionRef().document(ingredient).addSnapshotListener((doc1, e1) -> {
                                        String TAG1 = "BALLSSS";

                                        if (e != null) {
                                            Log.w(TAG1, "Listen failed.", e);
                                            return;
                                        }
                                        if (doc1.exists()) {
                                            Log.i(TAG1, doc1.getData().toString());
                                            IngredientInMealDay ing = new IngredientInMealDay(doc1.getString("description"),
                                                    IngredientUnit.stringToUnit(doc1.getString("measurementUnit")),doc1.getDouble("amount"),
                                                    IngredientCategory.stringToCategory(doc1.getString("category")));
                                            ing.setId(doc1.getId());
                                            ing.setMealdayID(mealDayToAdd.getId());
                                            mealDayToAdd.addIngredientInMealDay(ing);
                                        }

                                        // TODO: idk if we need this?

                                    });
                                    db.getIngredientsInMealDaysCollectionRef().document(ingredient).get();
                                }

                                // grab recipes
                                for (String recipe: recipeIDs) {
                                    db.getRecipesInMealDaysCollectionRef().document(recipe).addSnapshotListener((doc1, e1) -> {
                                        String TAG1 = "BALLSSS";

                                        if (e1 != null) {
                                            Log.w(TAG1, "Listen failed.", e1);
                                            return;
                                        }
                                        if (doc1.exists()) {
                                            Log.i(TAG1, doc1.getData().toString());
                                            ArrayList<IngredientInRecipe> ingredients = new ArrayList<>();
                                            RecipeInMealDay rec = new RecipeInMealDay(doc1.getString("description"),
                                                    doc1.getLong("prepTime").intValue(), doc1.getLong("servings").intValue(),
                                                    timeUnit.stringToTimeUnit(doc1.getString("prepUnitTime")),
                                                    recipeCategory.stringToRecipeCategory(doc1.getString("category")),
                                                            ingredients, doc1.getString("instructions"));
                                            rec.setScalingFactor(doc1.getDouble("scalingFactor"));
                                            rec.setId(doc1.getId());
                                            rec.setMealdayID(mealDayToAdd.getId());
                                            mealDayToAdd.addRecipeInMealDay(rec);

                                            for (String ingredientInRec: (ArrayList<String>)doc1.get("ingredients")) {
                                                db.getIngredientsInRecipesCollectionRef().document(ingredientInRec).addSnapshotListener((doc2, e2) -> {
                                                    String TAG2 = "BALLSSS";

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
                                                        rec.addIngredientRef(doc2.getId());
                                                        ingredients.add(ing);
                                                    }
                                                });
                                                db.getIngredientsInRecipesCollectionRef().document(ingredientInRec).get();
                                            }
                                        }

                                        // TODO: idk if we need this?
                                    });
                                    db.getRecipesInMealDaysCollectionRef().document(recipe).get();
                                }

                            Log.i("added new", doc.getDocument().getId() + doc.getDocument().getData().toString());
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
                                            String TAG1 = "BALLSSS";

                                            if (e != null) {
                                                Log.w(TAG1, "Listen failed.", e);
                                                return;
                                            }
                                            if (doc1.exists()) {
                                                Log.i(TAG1, doc1.getData().toString());
                                                IngredientInMealDay ing = new IngredientInMealDay(doc1.getString("description"),
                                                        IngredientUnit.stringToUnit(doc1.getString("measurementUnit")),doc1.getDouble("amount"),
                                                        IngredientCategory.stringToCategory(doc1.getString("category")));
                                                ing.setId(doc1.getId());
                                                ing.setMealdayID(mealDay.getId());
                                                mealDay.addIngredientInMealDay(ing);
                                            }

                                            // TODO: idk if we need this?
                                        });
                                        db.getIngredientsInMealDaysCollectionRef().document(ingredient).get();
                                    }

                                    // grab recipes
                                    for (String recipe: recipeModIDs) {
                                        db.getRecipesInMealDaysCollectionRef().document(recipe).addSnapshotListener((doc1, e1) -> {
                                            String TAG1 = "BALLSSS";

                                            if (e1 != null) {
                                                Log.w(TAG1, "Listen failed.", e1);
                                                return;
                                            }
                                            if (doc1.exists()) {
                                                Log.i(TAG1, doc1.getData().toString());
                                                ArrayList<IngredientInRecipe> ingredients = new ArrayList<>();
                                                RecipeInMealDay rec = new RecipeInMealDay(doc1.getString("description"),
                                                        doc1.getLong("prepTime").intValue(), doc1.getLong("servings").intValue(),
                                                        timeUnit.stringToTimeUnit(doc1.getString("prepUnitTime")),
                                                        recipeCategory.stringToRecipeCategory(doc1.getString("category")),
                                                        ingredients, doc1.getString("instructions"));
                                                rec.setId(doc1.getId());
                                                rec.setMealdayID(mealDay.getId());
                                                mealDay.addRecipeInMealDay(rec);

                                                for (String ingredientInRec: (ArrayList<String>)doc1.get("ingredients")) {
                                                    db.getIngredientsInRecipesCollectionRef().document(ingredientInRec).addSnapshotListener((doc2, e2) -> {
                                                        String TAG2 = "BALLSSS";

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

                                            // TODO: idk if we need this?

                                        });
                                        db.getRecipesInMealDaysCollectionRef().document(recipe).get();
                                    }
                                    break;
                                }
                            }
                            Log.i("modified new", doc.getDocument().getData().toString());

                            break;

                        case REMOVED:
                            Log.i("removed new", doc.getDocument().getData().toString());

                            break;
                    }

                }
            adapter.notifyDataSetChanged();
        });
    }

    public void addIngredientToIngredientsInMealDaysCollection(IngredientInMealDay ingredient) {
        db.addIngredientToIngredientsInMealDaysCollection(ingredient);
    }

    public void updateIngredientInIngredientsInMealDaysCollection(IngredientInMealDay ingredient) {
        db.updateIngredientInIngredientsInMealDaysCollection(ingredient);
    }

    public void removeIngredientFromIngredientsInMealDaysCollection(IngredientInMealDay ingredient) {
        db.removeIngredientFromIngredientsInMealDaysCollection(ingredient);
    }

    public void addRecipeToRecipesInMealDaysCollection(RecipeInMealDay recipeInMealDay) {
        db.addRecipeToRecipesInMealDaysCollection(recipeInMealDay);
    }

    public void updateRecipeInRecipesInMealDaysCollection(RecipeInMealDay recipeInMealDay) {
        db.updateRecipeInRecipesInMealDaysCollection(recipeInMealDay);
    }





}
