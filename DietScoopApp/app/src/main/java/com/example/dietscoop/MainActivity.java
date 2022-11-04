package com.example.dietscoop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    /**
     * Handler for navigating to ingredient list activity
     * @param view current view
     */
    public void goToIngredientList(View view) {

        Intent intent = new Intent(this, IngredientListActivity.class);
        startActivity(intent);
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