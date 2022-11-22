package com.example.dietscoop.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dietscoop.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public abstract class NavigationActivity extends AppCompatActivity implements BottomNavigationView.OnItemSelectedListener, NavigationBarView.OnItemReselectedListener {

    BottomNavigationView navBar;

    protected void initNavBar() {
        navBar = findViewById(R.id.bottomNavBar);
        navBar.setOnItemSelectedListener(this);
        navBar.setOnItemReselectedListener(this);
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
        item.setChecked(true);
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
            default:
                return false;
        }
    }

    @Override
    public void onNavigationItemReselected(@NonNull MenuItem item) {}
}
