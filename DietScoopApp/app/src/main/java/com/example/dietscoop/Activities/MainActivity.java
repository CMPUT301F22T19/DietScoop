package com.example.dietscoop.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.dietscoop.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseAuth auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            Log.i("PROGRESS", "Starting Login");
            startActivity(intent);
        }

        Intent intent = new Intent(this, MealPlanActivity.class);
        startActivity(intent);

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