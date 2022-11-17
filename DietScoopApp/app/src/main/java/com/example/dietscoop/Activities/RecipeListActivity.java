package com.example.dietscoop.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.dietscoop.Data.Ingredient.IngredientInRecipe;
import com.example.dietscoop.R;
import com.example.dietscoop.Data.Recipe.Recipe;
import com.example.dietscoop.Adapters.RecipeListAdapter;
import com.example.dietscoop.Database.RecipeStorage;
import com.example.dietscoop.Data.Recipe.recipeCategory;
import com.example.dietscoop.Data.Recipe.timeUnit;
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

    TextView titleSort, prepTimeSort, servingSort, categorySort;
    public enum sortSelection {
        TITLE,
        PREPTIME,
        SERVING,
        CATEGORY
    }
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

        titleSort = findViewById(R.id.title_sort);
        prepTimeSort = findViewById(R.id.preptime_sort);
        servingSort = findViewById(R.id.serving_sort);
        categorySort = findViewById(R.id.category_sort);

        addRecipeButton.setOnClickListener(view -> addNewRecipe());

        recipesButton.setBackgroundColor(Color.rgb(252, 186, 3));

        ingredientButton.setOnClickListener(view -> switchToIngredients());

        recipeListAdapter.setItemClickListener(this);


        titleSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSortSelection(sortSelection.TITLE);
            }
        });

        prepTimeSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSortSelection(sortSelection.PREPTIME);
            }
        });

        servingSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSortSelection(sortSelection.SERVING);
            }
        });

        categorySort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSortSelection(sortSelection.CATEGORY);
            }
        });

    }

    private void addNewRecipe() {
        Intent intent = new Intent(this, ViewRecipeActivity.class);
        intent.putExtra("ADDING", true);
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
        Intent intent = new Intent(this, ViewRecipeActivity.class);
        intent.putExtra("ADDING", false);
        intent.putExtra("RECIPE", recipeStorage.getRecipeStorage().get(position));
        startActivity(intent);
    }

    public void onSortSelection(RecipeListActivity.sortSelection sortBy) {
        recipeStorage.sortBy(sortBy);
        recipeListAdapter.notifyDataSetChanged();
    }
}