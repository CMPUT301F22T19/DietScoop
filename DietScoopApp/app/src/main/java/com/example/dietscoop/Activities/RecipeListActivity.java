package com.example.dietscoop.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.dietscoop.Data.Ingredient.IngredientInRecipe;
import com.example.dietscoop.R;
import com.example.dietscoop.Data.Recipe.Recipe;
import com.example.dietscoop.Adapters.RecipeListAdapter;
import com.example.dietscoop.Database.RecipeStorage;
import com.example.dietscoop.Data.Recipe.recipeCategory;
import com.example.dietscoop.Data.Recipe.timeUnit;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

/**
 * Class associated with the activity_recipe_list.xml file.
 * This class handles the relation of Recipe list to display
 */
public class RecipeListActivity extends NavigationActivity implements RecyclerItemClickListener {
    RecyclerView recipeListView;
    RecipeStorage recipeStorage;
    RecipeListAdapter recipeListAdapter;

    TextView titleSort, prepTimeSort, servingSort, categorySort;
    
    ActionBar topBar;

    public enum sortSelection {
        TITLE,
        PREPTIME,
        SERVING,
        CATEGORY
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        initNavigationActivity();
        navBar.setSelectedItemId(R.id.recipes);

        setupActionBar();

        recipeListView = findViewById(R.id.recipe_list);
        recipeListView.setHasFixedSize(false);
        recipeListView.setLayoutManager(new LinearLayoutManager(this));

        recipeStorage = new RecipeStorage();
        recipeListAdapter = new RecipeListAdapter(this, recipeStorage.getRecipeStorage());
        recipeListView.setAdapter(recipeListAdapter);

        recipeStorage.setupRecipeSnapshotListener(recipeListAdapter);
        recipeStorage.getRecipeStorageFromDatabase();

        titleSort = findViewById(R.id.title_sort);
        prepTimeSort = findViewById(R.id.preptime_sort);
        servingSort = findViewById(R.id.serving_sort);
        categorySort = findViewById(R.id.category_sort);

        recipeListAdapter.setItemClickListener(this);

        titleSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortRecipesBy(sortSelection.TITLE);
            }
        });

        prepTimeSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortRecipesBy(sortSelection.PREPTIME);
            }
        });

        servingSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortRecipesBy(sortSelection.SERVING);
            }
        });

        categorySort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortRecipesBy(sortSelection.CATEGORY);
            }
        });

    }

    private void addNewRecipe() {
        Intent intent = new Intent(this, ViewRecipeActivity.class);
        intent.putExtra("ADDING", true);
        startActivity(intent);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, ViewRecipeActivity.class);
        intent.putExtra("ADDING", false);
        intent.putExtra("RECIPE", recipeStorage.getRecipeStorage().get(position));
        startActivity(intent);
    }

    public void sortRecipesBy(RecipeListActivity.sortSelection sortBy) {
        recipeStorage.sortBy(sortBy);
        recipeListAdapter.notifyDataSetChanged();
    }

    private void setupActionBar() {

        topBar = getSupportActionBar();
        topBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        topBar.setDisplayShowCustomEnabled(true);
        topBar.setCustomView(R.layout.top_bar_add_layout);

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

    void onAddClicked() {
        addNewRecipe();
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}