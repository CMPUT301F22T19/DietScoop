package com.example.dietscoop.Activities;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dietscoop.Database.RecipeStorage;
import com.example.dietscoop.Fragments.IngredientAddFragment;
import com.example.dietscoop.Data.Ingredient.IngredientInStorage;
import com.example.dietscoop.Database.IngredientStorage;
import com.example.dietscoop.Adapters.IngredientStorageAdapter;
import com.example.dietscoop.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * Class tying to Ingredient list. Associated with the activity_ingredient_list.xml layout.
 */
public class IngredientListActivity extends AppCompatActivity implements IngredientAddFragment.OnFragmentInteractionListener, RecyclerItemClickListener {

    //testing
    RecipeStorage r;

    IngredientStorage foodStorage;
    IngredientStorageAdapter ingredientStorageAdapter;
    RecyclerView ingredientListView;

    Button ingredientButton;
    Button recipesButton;
    Button mealsButton;
    Button shoppingButton;

    TextView nameSort, categorySort, bestBeforeSort, locationSort;
    public enum sortSelection {
        NAME,
        CATEGORY,
        DATE,
        LOCATION
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_list);

        //testing
//        r = new RecipeStorage();
//        r.addSnapshotListener();

        ingredientListView = findViewById(R.id.ingredient_list);
        ingredientListView.setHasFixedSize(false);
        ingredientListView.setLayoutManager(new LinearLayoutManager(this));

        //Main container declarations:
        foodStorage = new IngredientStorage();
        ingredientStorageAdapter = new IngredientStorageAdapter(this, foodStorage.getIngredientStorage());
        ingredientListView.setAdapter(ingredientStorageAdapter);
        foodStorage.setupIngredientSnapshotListener(ingredientStorageAdapter);
        foodStorage.getIngredientStorageFromDatabase();

        ingredientButton = findViewById(R.id.ingr_nav);
        recipesButton = findViewById(R.id.recipes_nav);
        mealsButton = findViewById(R.id.meals_nav);
        shoppingButton = findViewById(R.id.shopping_nav);

        nameSort = findViewById(R.id.name_sort);
        categorySort = findViewById(R.id.category_sort);
        bestBeforeSort = findViewById(R.id.bestbefore_sort);
        locationSort = findViewById(R.id.location_sort);

        ingredientButton.setBackgroundColor(Color.rgb(252, 186, 3));

        final FloatingActionButton addIngredientButton = findViewById(R.id.add_new_ingredient_button);
        addIngredientButton.setOnClickListener((v) -> new IngredientAddFragment().show(getSupportFragmentManager(), "ADD_INGREDIENT"));

        recipesButton.setOnClickListener(unused -> switchToRecipes());
        ingredientStorageAdapter.setItemClickListener(this);

        nameSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSortSelection(sortSelection.NAME);
            }
        });

        categorySort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSortSelection(sortSelection.CATEGORY);
            }
        });

        bestBeforeSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSortSelection(sortSelection.DATE);
            }
        });

        locationSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSortSelection(sortSelection.LOCATION);
            }
        });

    }

    /**
     * handler for switching to Recipe activity.
     */
    private void switchToRecipes() {
        // TODO: add bundled info
        Intent switchActivityIntent = new Intent(this, RecipeListActivity.class);
        startActivity(switchActivityIntent);
        final FloatingActionButton addIngredientButton = findViewById(R.id.add_new_ingredient_button);

        addIngredientButton.setOnClickListener((e) -> new IngredientAddFragment().show(getSupportFragmentManager(), "ADD_INGREDIENT"));

    }

    public void onSortSelection(sortSelection sortBy) {
        foodStorage.sortBy(sortBy);
        ingredientStorageAdapter.notifyDataSetChanged();
    }

    @Override
    public void onOkPressed(IngredientInStorage newIngredientInStorage) {
        foodStorage.addIngredientToStorage(newIngredientInStorage);
    }

    @Override
    public void onOkPressedUpdate(IngredientInStorage updateIngredientInStorage){
        foodStorage.updateIngredientInStorage(updateIngredientInStorage);
    }

    @Override
    public void onDeletePressed(IngredientInStorage deleteIngredientInStorage){
        foodStorage.removeIngredientFromStorage(deleteIngredientInStorage);
    }

    @Override
    public void onItemClick(View view, int position) {

        ArrayList<IngredientInStorage> ingredients = foodStorage.getIngredientStorage();
        IngredientInStorage ingredient = ingredients.get(position);
        new IngredientAddFragment(ingredient).show(getSupportFragmentManager(), "EDIT_INGREDIENT");
    }

}