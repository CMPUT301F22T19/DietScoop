package com.example.dietscoop.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dietscoop.Data.Recipe.Recipe;
import com.example.dietscoop.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public abstract class NavigationActivity extends AppCompatActivity implements BottomNavigationView.OnItemSelectedListener {

    BottomNavigationView navBar;
    ActionBar topBar;

    protected void initNavigationActivity() {
        navBar = findViewById(R.id.bottomNavBar);
        navBar.setOnItemSelectedListener(this);

        topBar = getSupportActionBar();
        topBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        topBar.setDisplayShowCustomEnabled(true);
        topBar.setCustomView(R.layout.top_bar_layout);

        View topBarView = topBar.getCustomView();

        ImageButton logout = topBarView.findViewById(R.id.logout_button);
        ImageButton addItem = topBarView.findViewById(R.id.add_button);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddClicked();
            }
        });
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
//                if (!(this instanceof ShoppingListActivity)) {
//                    goToShoppingList();
//                }
                break;
            case R.id.meals:
//                if (!(this instanceof MealPlanActivity)) {
//                    goToMealPlans();
//                }
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

    protected void logout() {
        FirebaseAuth.getInstance().signOut();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    abstract void onAddClicked();
}
