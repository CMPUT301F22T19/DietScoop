package com.example.dietscoop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class RecipeListActivity extends AppCompatActivity {
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

    }

    // TODO: add bundled info
    private void switchToIngredients() {
        Intent switchActivityIntent = new Intent(this, IngredientListActivity.class);
        startActivity(switchActivityIntent);
    }

}