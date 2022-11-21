package com.example.dietscoop.Activities;

import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dietscoop.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public abstract class NavigationActivity extends AppCompatActivity implements BottomNavigationView.OnItemSelectedListener {

    BottomNavigationView navBar;

    protected void initializeNavBar() {
        navBar = findViewById(R.id.bottomNavBar);
        navBar.setOnItemSelectedListener(this);
    }

    /**
     * Handler for navigating to ingredient list activity
     */
    public void goToIngredientList() {

        Intent intent = new Intent(this, IngredientListActivity.class);
        startActivity(intent);
    }

    /**
     * Handler for navigating to recipe list activity
     */
    public void goToRecipeList() {
        Intent intent = new Intent(this, RecipeListActivity.class);
        startActivity(intent);
    }

    private void goToShoppingList() {
        // LAUNCH SHOPPING LIST
    }

    private void goToMealPlans() {
        // LAUNCH MEAL PLANS
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.i("NAVBAR","CLICKED");
        switch(item.getItemId()) {
            case R.id.shopping:
                goToShoppingList();
                return true;
            case R.id.meals:
                goToMealPlans();
                return true;
            case R.id.recipes:
                goToRecipeList();
                return true;
            case R.id.ingredients:
                goToIngredientList();
                return true;
        }
        return false;
    }


}
