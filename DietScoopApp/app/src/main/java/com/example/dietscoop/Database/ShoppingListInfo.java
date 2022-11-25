package com.example.dietscoop.Database;

import android.util.Log;

import androidx.annotation.Nullable;

import com.example.dietscoop.Adapters.MealDayRecyclerAdapter;
import com.example.dietscoop.Data.Ingredient.Ingredient;
import com.example.dietscoop.Data.Ingredient.IngredientInRecipe;
import com.example.dietscoop.Data.Ingredient.IngredientInStorage;
import com.example.dietscoop.Data.Recipe.Recipe;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ShoppingListInfo {

    private ArrayList<IngredientInRecipe> inStorage;
    private ArrayList<IngredientInRecipe> inMealPlans;
    private ArrayList<IngredientInRecipe> shoppingList;
    private Database db;

    public ShoppingListInfo() {
        this.db = new Database();
        this.inStorage = new ArrayList<>();
        this.inMealPlans = new ArrayList<>();

        setUpSnapshotListeners();
    }

    private void setUpSnapshotListeners() {

        setupIngredientsInMealDaysSnapshotListener();
        setupRecipesInMealDaysSnapshotListener();

    }

    private void setupRecipesInMealDaysSnapshotListener() {

        db.getIngredientsInMealDaysCollectionRef().addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null) {
                    Log.w("SNAPSHOT FAILED", "RECIPES IN MEAL DAYS SNAPSHOT LISTENER FAILED");
                    return;
                }

                ArrayList<Recipe> recipesInMealPlans = new ArrayList<>();

                for (QueryDocumentSnapshot doc : value) {
                    if (doc.getId() != null) {

                    }
                }

            }
        });

    }

    private void setupIngredientsInMealDaysSnapshotListener() {


    }

    public ArrayList<IngredientInRecipe> getShoppingList() {
        return shoppingList;
    }





}
