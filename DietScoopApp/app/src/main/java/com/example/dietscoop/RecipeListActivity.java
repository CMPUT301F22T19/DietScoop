package com.example.dietscoop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class RecipeListActivity extends AppCompatActivity {
    ListView recipeListView;
    ArrayList<Recipe> recipes;
    RecipeListAdapter recipeListAdapter;

    RecipeStorage recipeStorage;

    Button ingredientButton;
    Button recipesButton;
    Button mealsButton;
    Button shoppingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        recipeListView = findViewById(R.id.recipe_list);

//        recipes = new ArrayList<Recipe>();
        recipeStorage = new RecipeStorage();

        recipeListAdapter = new RecipeListAdapter(this, recipeStorage.getRecipeStorage());
        recipeListView.setAdapter(recipeListAdapter);
        recipeStorage.setupRecipeSnapshotListener();

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

    }

    // TODO: add bundled info
    private void switchToIngredients() {
        recipeStorage.removeRecipeFromStorage(recipeStorage.getRecipeStorage().get(0));
        Intent switchActivityIntent = new Intent(this, IngredientListActivity.class);
        startActivity(switchActivityIntent);
    }

}