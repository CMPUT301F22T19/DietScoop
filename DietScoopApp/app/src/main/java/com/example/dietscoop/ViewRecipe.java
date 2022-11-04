package com.example.dietscoop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewRecipe extends AppCompatActivity implements ModifyRecipeFragment.OnFragmentInteractionListener{

    TextView prepTime, numServings, category, instructions, name;
    RecyclerView ingredientsView;
    RecipeStorage storage;
    IngredientRecipeAdapter adapter;
    Recipe currentRecipe;

    Button backButton;
    Button modifyRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        Intent intent = getIntent();
        currentRecipe = (Recipe) intent.getSerializableExtra("RECIPE");
        modifyRecipe = findViewById(R.id.recipe_modify_button);

        initialize();
        updateTextViews();

        modifyRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ModifyRecipeFragment(currentRecipe).show(getSupportFragmentManager(), "MODIFY_RECIPE");
            }
        });

    }

    private void initialize() {

        prepTime = findViewById(R.id.recipe_prep_time);
        numServings = findViewById(R.id.recipe_no_of_servings);
        category = findViewById(R.id.recipe_category);
        instructions = findViewById(R.id.recipe_instructions);
        ingredientsView = findViewById(R.id.recipe_recycler_view);
        name = findViewById(R.id.recipe_title);

        backButton = findViewById(R.id.recipe_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });

        storage = new RecipeStorage();
        storage.getRecipeStorageFromDatabase();

        adapter = new IngredientRecipeAdapter(this,
                currentRecipe.getIngredients());

        Log.i("DESCRIPTION IN INIT", currentRecipe.getDescription());

        ingredientsView.setAdapter(adapter);
        ingredientsView.setHasFixedSize(false);
        ingredientsView.setLayoutManager(new LinearLayoutManager(this));

        storage.setupRecipeSnapshotListener(adapter);
        storage.getRecipeStorageFromDatabase();

    }

    private void updateTextViews() {

        prepTime.setText(String.valueOf(currentRecipe.getPrepTime()));
        numServings.setText(String.valueOf(currentRecipe.getNumOfServings()));
        category.setText(currentRecipe.getCategoryName());
        instructions.setText(currentRecipe.getInstructions());
        name.setText(String.valueOf(currentRecipe.getDescription()));

    }

    public void goBack() {
        Intent intent = new Intent(this, RecipeListActivity.class);
        startActivity(intent);
    }

    @Override
    public void onOkPressed(Recipe recipeToChange, String newName, Integer newPrepTime, Integer newServings, recipeCategory newCategory) {
        recipeToChange.setDescription(newName);
        recipeToChange.setPrepTime(newPrepTime);
        recipeToChange.setNumOfServings(newServings);
        recipeToChange.setCategory(newCategory);

        storage.addRecipeToStorage(recipeToChange);
    }
}