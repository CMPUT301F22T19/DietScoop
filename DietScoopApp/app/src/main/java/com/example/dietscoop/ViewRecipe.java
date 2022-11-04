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

/**
 * This class handles the creation of the
 * recipe view activity.
 *
 * It handles the
 * logic instantiation for the events that
 * go on in this activity.
 */
public class ViewRecipe extends AppCompatActivity {

    TextView prepTime, numServings, category, instructions, name;
    RecyclerView ingredientsView;
    RecipeStorage storage;
    IngredientRecipeAdapter adapter;
    Recipe currentRecipe;
    Button editInstructions;

    Button backButton;
    Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        Intent intent = getIntent();

        //Fetching the serialized recipe:
        currentRecipe = (Recipe) intent.getSerializableExtra("RECIPE");

        initialize();
        updateTextViews();

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

        deleteButton = findViewById(R.id.recipe_delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteThisRecipe();
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

        //Adding the button here for instruction updating:
        editInstructions = findViewById(R.id.recipe_add_comment_button);
        editInstructions.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Need to launch the new fragment activity for editing the instructions.
                new EditInstructionsEntryFragmnet().show(getSupportFragmentManager(), "EDIT INSTRUCTIONS");
            }
        });

    }

    private void updateTextViews() {

        prepTime.setText(String.valueOf(currentRecipe.getPrepTime()));
        numServings.setText(String.valueOf(currentRecipe.getNumOfServings()));
        category.setText(currentRecipe.getCategoryName());
        instructions.setText(currentRecipe.getInstructions());
        name.setText(String.valueOf(currentRecipe.getDescription()));

    }


    public void updateInstructions(String text) {
        //Changing the text for instructions and the recipe.
        instructions.setText(text);
        currentRecipe.setInstructions(text);

        //Updating the recipe in the Database: Deletion of current recipe:
        //+ updating with the most recently update.
//        storage.removeRecipeFromStorage(currentRecipe);
        storage.addRecipeToStorage(currentRecipe);
    }

    private void goBack() {
        Intent intent = new Intent(this, RecipeListActivity.class);
        startActivity(intent);
    }

    private void deleteThisRecipe() {
        storage.removeRecipeFromStorage(currentRecipe);
        goBack();
    }

}