package com.example.dietscoop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class RecipeListActivity extends AppCompatActivity implements RecipeListItemClickListener {
    RecyclerView recipeListView;
    RecipeStorage recipeStorage;
    RecipeListAdapter recipeListAdapter;

    Button ingredientButton;
    Button recipesButton;
    Button mealsButton;
    Button shoppingButton;
    Button sortButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        recipeListView = findViewById(R.id.recipe_list);
        recipeListView.setHasFixedSize(false);
        recipeListView.setLayoutManager(new LinearLayoutManager(this));

        recipeStorage = new RecipeStorage();
        recipeListAdapter = new RecipeListAdapter(this, recipeStorage.getRecipeStorage());
        recipeListView.setAdapter(recipeListAdapter);
        recipeStorage.setupRecipeSnapshotListener(recipeListAdapter);
        recipeStorage.getRecipeStorageFromDatabase();

        ArrayList<IngredientInRecipe> listy = new ArrayList<IngredientInRecipe>();
        listy.add(new IngredientInRecipe("chicken","kg",4,Category.meat));
        listy.add(new IngredientInRecipe("butter","kg",4,Category.vegetable));
        listy.add(new IngredientInRecipe("spice","kg",4,Category.fruit));
        Recipe recipe = new Recipe("butTer chicken",125,4,timeUnit.minute,
                recipeCategory.dinner,listy, "cook it nice");
        recipeStorage.addRecipeToStorage(recipe);

        ingredientButton = findViewById(R.id.ingr_nav);
        recipesButton = findViewById(R.id.recipes_nav);
        mealsButton = findViewById(R.id.meals_nav);
        shoppingButton = findViewById(R.id.shopping_nav);

        recipesButton.setBackgroundColor(Color.rgb(252, 186, 3));

        ingredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToIngredients();
            }
        });

        recipeListAdapter.setItemClickListener(this);

    }

    // TODO: add bundled info
    private void switchToIngredients() {
        Intent switchActivityIntent = new Intent(this, IngredientListActivity.class);
        startActivity(switchActivityIntent);
    }

    @Override
    public void onClick(View view, int position) {
        // TODO FOR KARAN
    }
}