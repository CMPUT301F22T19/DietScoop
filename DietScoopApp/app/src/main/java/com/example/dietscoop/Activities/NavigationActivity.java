package com.example.dietscoop.Activities;

import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dietscoop.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * This class handles navigation between the main activities of the application
 */
public abstract class NavigationActivity extends AppCompatActivity implements BottomNavigationView.OnItemSelectedListener {

    BottomNavigationView navBar;

    protected void initNavigationActivity() {
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
     * Method for navigating to recipe list activity
     */
    public void goToRecipeList() {
        Intent intent = new Intent(this, RecipeListActivity.class);
        startActivity(intent);
    }

    /**
     * Method for navigating to shopping list activity
     */
    private void goToShoppingList() {
        Intent intent = new Intent(this, ShoppingListActivity.class);
        startActivity(intent);
    }

    /**
     * Method for navigating to meal plan activity
     */
    private void goToMealPlans() {
        // LAUNCH MEAL PLANS
        Intent intent = new Intent(this, MealPlanActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.i("NAVBAR","CLICKED");
        item.setChecked(true);
        switch(item.getItemId()) {
            case R.id.shopping:
                if (!(this instanceof ShoppingListActivity)) {
                    goToShoppingList();
                }
                break;
            case R.id.meals:
                if (!(this instanceof MealPlanActivity)) {
                    goToMealPlans();
                }
                break;
            case R.id.recipes:
                if (!(this instanceof RecipeListActivity)) {
                    goToRecipeList();
                }
                break;
            case R.id.ingredients:
                if (!(this instanceof IngredientListActivity)) {
                    goToIngredientList();
                }
                break;
            default:
                throw new RuntimeException("UNEXPECTED NAVIGATION ITEM");
        }
        return true;
    }
}
