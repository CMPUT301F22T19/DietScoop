package com.example.dietscoop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ViewRecipe extends AppCompatActivity {

    TextView prepTime, numServings, category, instructions;
    RecyclerView ingredientsView;
    RecipeStorage storage;
    IngredientRecipeAdapter adapter;
    int currentRecipePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        Intent intent = getIntent();
        storage = (RecipeStorage) intent.getSerializableExtra("RECIPE STORAGE");
        currentRecipePosition = intent.getIntExtra("POSITION", -1);
        if (currentRecipePosition == -1) {
            throw new RuntimeException("FAILED TO GET POSITION FROM INTENT");
        }

        initialize();
        updateTextViews();

    }

    private void initialize() {

        prepTime = findViewById(R.id.recipe_prep_time);
        numServings = findViewById(R.id.recipe_no_of_servings);
        category = findViewById(R.id.recipe_category);
        instructions = findViewById(R.id.recipe_instructions);
        ingredientsView = findViewById(R.id.recipe_recycler_view);

        adapter = new IngredientRecipeAdapter(this,
                storage.getRecipeStorage().get(currentRecipePosition).getIngredients());

        ingredientsView.setAdapter(adapter);
        ingredientsView.setHasFixedSize(false);
        ingredientsView.setLayoutManager(new LinearLayoutManager(this));

        storage.setupRecipeSnapshotListener(adapter);
        storage.getRecipeStorageFromDatabase();

    }

    private void updateTextViews() {

        Recipe recipe = storage.getRecipeStorage().get(currentRecipePosition);

        prepTime.setText(String.valueOf(recipe.getPrepTime()));
        numServings.setText(String.valueOf(recipe.getNumOfServings()));
        category.setText(recipe.getCategoryName());
        instructions.setText(recipe.getInstructions());
    }

}