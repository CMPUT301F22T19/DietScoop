package com.example.dietscoop.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.dietscoop.Data.Ingredient.IngredientCategory;
import com.example.dietscoop.Data.Ingredient.IngredientInRecipe;
import com.example.dietscoop.Data.Recipe.Recipe;
import com.example.dietscoop.Database.IngredientStorage;
import com.example.dietscoop.Database.RecipeStorage;
import com.example.dietscoop.R;

public class MainActivity extends AppCompatActivity {

    RecipeStorage r;//testing
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //testing
        r = new RecipeStorage();
        r.addDummySnapshotListener();

        //r.getAllIngsInRecipes();


    }


    /**
     * Handler for navigating to ingredient list activity
     * @param view current view
     */
    public void goToIngredientList(View view) {

//        Intent intent = new Intent(this, IngredientListActivity.class);
//        startActivity(intent);

        //testing

        r.addIngredientBlah(new IngredientInRecipe("babann","dongs",95, IngredientCategory.fruit));
    }

    /**
     * Handler for navigating to recipe list activity
     * @param view current view
     */
    public void goToRecipeList(View view) {
        Intent intent = new Intent(this, RecipeListActivity.class);
        startActivity(intent);
    }
}