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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * Class associated with the activity_recipe_list.xml file.
 * This class handles the relation of Recipe list to display
 */
public class RecipeListActivity extends AppCompatActivity implements RecyclerItemClickListener {
    RecyclerView recipeListView;
    RecipeStorage recipeStorage;
    RecipeListAdapter recipeListAdapter;

    Button ingredientButton;
    Button recipesButton;
    Button mealsButton;
    Button shoppingButton;
    Button sortButton;
    FloatingActionButton addRecipeButton;

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

        ingredientButton = findViewById(R.id.ingr_nav);
        recipesButton = findViewById(R.id.recipes_nav);
        mealsButton = findViewById(R.id.meals_nav);
        shoppingButton = findViewById(R.id.shopping_nav);
        addRecipeButton = findViewById(R.id.add_new_recipe_button);

        addRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewRecipe();
            }
        });

        recipesButton.setBackgroundColor(Color.rgb(252, 186, 3));

        ingredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToIngredients();
            }
        });

        recipeListAdapter.setItemClickListener(this);



    }

    private void addNewRecipe() {
        Recipe dummy = new Recipe(
                "ENTER",
                -1,
                -1,
                timeUnit.minute,
                recipeCategory.appetizer,
                new ArrayList<IngredientInRecipe>(),
                ""
        );

        recipeStorage.addRecipeToStorage(dummy);

        Intent intent = new Intent(this, ViewRecipe.class);
        intent.putExtra("RECIPE", dummy);
        startActivity(intent);
    }


    // TODO: add bundled info

    /**
     * Method to handle navigation to Ingredients List Activity
     */
    private void switchToIngredients() {
        // TODO: add bundled info
        Intent switchActivityIntent = new Intent(this, IngredientListActivity.class);
        startActivity(switchActivityIntent);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, ViewRecipe.class);
        intent.putExtra("RECIPE", recipeStorage.getRecipeStorage().get(position));
        startActivity(intent);
    }
}