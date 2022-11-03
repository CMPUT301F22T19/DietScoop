package com.example.dietscoop;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;


public class IngredientListActivity extends AppCompatActivity implements sortIngredientByFragment.OnFragmentInteractionListener {

    IngredientStorage foodStorage;
    IngredientStorageAdapter ingredientStorageAdapter;
    RecyclerView ingredientListView;

    Button ingredientButton;
    Button recipesButton;
    Button mealsButton;
    Button shoppingButton;
    Button sortButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_list);

        ingredientListView = findViewById(R.id.ingredient_list);
        ingredientListView.setHasFixedSize(false);
        ingredientListView.setLayoutManager(new LinearLayoutManager(this));

        //Main container declarations:
        foodStorage = new IngredientStorage();
        ingredientStorageAdapter = new IngredientStorageAdapter(this, foodStorage.getIngredientStorage());
        ingredientListView.setAdapter(ingredientStorageAdapter);
        foodStorage.setupIngredientSnapshotListener(ingredientStorageAdapter);
        foodStorage.getIngredientStorageFromDatabase();

        //testing code:
        //IngredientStorage sampleIngredientStorage = new IngredientStorage();
        IngredientInStorage sampleIngredient = new IngredientInStorage("Poop", "kg",
                5, 2022, 4, 25, Location.freezer, Category.meat);
        //sampleIngredientStorage.setupIngredientSnapshotListener(); //TODO: need to add the pass value.
        //sampleIngredientStorage.addIngredientToStorage(sampleIngredient);
        foodStorage.addIngredientToStorage(sampleIngredient);

        sampleIngredient = new IngredientInStorage("Roop", "kg",
                5, 2022, 5, 25, Location.fridge, Category.meat);
        foodStorage.addIngredientToStorage(sampleIngredient);

        sampleIngredient = new IngredientInStorage("Toop", "kg",
                5, 2022, 6, 25, Location.fridge, Category.fruit);
        foodStorage.addIngredientToStorage(sampleIngredient);

        sampleIngredient = new IngredientInStorage("Scoop", "kg",
                5, 2022, 6, 25, Location.pantry, Category.vegetable);
        foodStorage.addIngredientToStorage(sampleIngredient);

        ingredientButton = findViewById(R.id.ingr_nav);
        recipesButton = findViewById(R.id.recipes_nav);
        mealsButton = findViewById(R.id.meals_nav);
        shoppingButton = findViewById(R.id.shopping_nav);
        sortButton = findViewById((R.id.sort_button));

        ingredientButton.setBackgroundColor(Color.rgb(252, 186, 3));

        recipesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToRecipes();
            }
        });

        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new sortIngredientByFragment().show(getFragmentManager(),"SORT_BY");
            }
        });
    }
    // TODO: add bundled info
    private void switchToRecipes() {
        Intent switchActivityIntent = new Intent(this, RecipeListActivity.class);
        startActivity(switchActivityIntent);
    }

    @Override
    public void onSortSelection(sortIngredientByFragment.selection sortBy) {
        foodStorage.sortBy(sortBy);
        ingredientStorageAdapter.notifyDataSetChanged();
    }

    // POSSIBLE TODO: could be used to fix animations
//    @Override
//    public void finish() {
//        super.finish();
//        overridePendingTransition(androidx.navigation.ui.R.anim.nav_default_exit_anim, androidx.navigation.ui.R.anim.nav_default_enter_anim);
//    }

}