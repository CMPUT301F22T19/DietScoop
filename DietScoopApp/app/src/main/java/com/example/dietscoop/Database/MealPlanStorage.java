package com.example.dietscoop.Database;

import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.example.dietscoop.Data.Ingredient.IngredientCategory;
import com.example.dietscoop.Data.Ingredient.IngredientInMealDay;
import com.example.dietscoop.Data.Ingredient.IngredientInRecipe;
import com.example.dietscoop.Data.Meal.Meal;
import com.example.dietscoop.Data.Meal.MealDay;
import com.example.dietscoop.Data.Meal.MealPlan;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.checkerframework.checker.units.qual.A;

import java.time.LocalDate;
import java.util.ArrayList;

import kotlin.time.MeasureTimeKt;

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
            for (DocumentChange doc : value.getDocumentChanges()) {

                    switch (doc.getType()) {
                        case ADDED:
                            if (adapter != null) {
                                MealDay mealDayToAdd = new MealDay(LocalDate.of(doc.getDocument().getLong("year").intValue(),
                                        doc.getDocument().getLong("month").intValue(),
                                        doc.getDocument().getLong("day").intValue()));
                                mealDayToAdd.setIngredientIDs((ArrayList<String>)doc.getDocument().get("ingredients"));
                                mealDayToAdd.setRecipeIDs((ArrayList<String>)doc.getDocument().get("recipes"));
                                mealPlan.add(mealDayToAdd);
                                adapter.notifyDataSetChanged();
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
                                    adapter.notifyDataSetChanged();
                                    break;
                                }
                            }
                            Log.i("modified new", doc.getDocument().getData().toString());
                            adapter.notifyDataSetChanged();
                            break;
                        case REMOVED:
                            Log.i("removed new", doc.getDocument().getData().toString());
                            adapter.notifyDataSetChanged();
                            break;
                    }
                }
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






}
