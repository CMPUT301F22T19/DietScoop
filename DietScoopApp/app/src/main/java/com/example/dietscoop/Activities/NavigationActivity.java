package com.example.dietscoop.Activities;

import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dietscoop.Data.Meal.Meal;
import com.example.dietscoop.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

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
     * Handler for navigating to recipe list activity
     */
    public void goToRecipeList() {
        Intent intent = new Intent(this, RecipeListActivity.class);
        startActivity(intent);
    }

    private void goToShoppingList() {
        Intent intent = new Intent(this, ShoppingListActivity.class);
        startActivity(intent);
    }

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
