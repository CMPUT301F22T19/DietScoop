package com.example.dietscoop.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.dietscoop.Data.Ingredient.IngredientCategory;
import com.example.dietscoop.Data.Ingredient.IngredientInRecipe;
import com.example.dietscoop.Data.Recipe.recipeCategory;
import com.example.dietscoop.Data.Recipe.timeUnit;
import com.example.dietscoop.Fragments.EditInstructionsEntryFragment;
import com.example.dietscoop.Adapters.IngredientRecipeAdapter;
import com.example.dietscoop.R;
import com.example.dietscoop.Data.Recipe.Recipe;
import com.example.dietscoop.Database.RecipeStorage;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;

/**
 * This class handles the creation of the
 * recipe view activity.
 *
 * It handles the
 * logic instantiation for the events that
 * go on in this activity.
 */
public class ViewRecipeActivity extends AppCompatActivity {

    TextView prepTime, numServings, category, instructions, name;
    RecyclerView ingredientsView;
    RecipeStorage storage;
    IngredientRecipeAdapter adapter;
    Recipe currentRecipe;
    Button editInstructions;

    Button backButton;
    Button deleteButton;
    boolean adding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        Intent intent = getIntent();

        //Fetching the serialized recipe:
        adding = intent.getBooleanExtra("ADDING", false);
        if (adding) {
            currentRecipe = new Recipe("",0,0, timeUnit.minute, recipeCategory.appetizer,
                    new ArrayList<>(),"");
        } else {
            currentRecipe = (Recipe) intent.getSerializableExtra("RECIPE");
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
        name = findViewById(R.id.recipe_title);

        backButton = findViewById(R.id.recipe_back_button);
        backButton.setOnClickListener(view -> confirmRecipe());

        deleteButton = findViewById(R.id.recipe_delete_button);
        deleteButton.setOnClickListener(view -> deleteThisRecipe());

        storage = new RecipeStorage();
        storage.getRecipeStorageFromDatabase();

        adapter = new IngredientRecipeAdapter(this,
                currentRecipe.getIngredients());

        Log.i("DESCRIPTION IN INIT", currentRecipe.getDescription());

        ingredientsView.setAdapter(adapter);
        ingredientsView.setHasFixedSize(false);
        ingredientsView.setLayoutManager(new LinearLayoutManager(this));

        //storage.setupRecipeSnapshotListener(adapter);
        storage.addRealSnapshotListener(currentRecipe, adapter, false);//testing
        //storage.getRecipeStorageFromDatabase();

        //Adding the button here for instruction updating:
        editInstructions = findViewById(R.id.recipe_add_comment_button);
        editInstructions.setOnClickListener(v -> {
            //Need to launch the new fragment activity for editing the instructions.
            new EditInstructionsEntryFragment().show(getSupportFragmentManager(), "EDIT INSTRUCTIONS");
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
        //storage.addRecipeToStorage(currentRecipe);
    }

    private void confirmRecipe() {
        if (adding) {
            for (IngredientInRecipe i: currentRecipe.getIngredients()) {
                Log.i("adding ingros in recip", i.getDescription());
            }
            storage.addRecipeToStorage(currentRecipe);
        } else {
            for (DocumentReference i: currentRecipe.getIngredientRefs()) {
                Log.i("editing ingros in recip", i.toString());
            }
            storage.updateRecipeInStorage(currentRecipe);
        }
        goBack();
    }

    private void cancel() {
        for (DocumentReference doc: currentRecipe.getIngredientRefs()) {
            doc.delete();
        }
        goBack();
    }

    private void goBack() {
        Intent intent = new Intent(this, RecipeListActivity.class);
        startActivity(intent);
    }

    private void deleteThisRecipe() {
        storage.addIngredientBlah(new IngredientInRecipe("babann","dongs",95, IngredientCategory.fruit));
//        storage.removeRecipeFromStorage(currentRecipe);
//        goBack();
    }

}