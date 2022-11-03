package com.example.dietscoop;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ListView;


public class IngredientListActivity extends AppCompatActivity {

    IngredientStorage foodStorage;
    IngredientStorageAdapter ingredientStorageAdapter;
    RecyclerView ingredientListView;

    Button ingredientButton;
    Button recipesButton;
    Button mealsButton;
    Button shoppingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_list);

        ingredientListView = findViewById(R.id.ingredient_list);
        ingredientListView.setHasFixedSize(true);
        ingredientListView.setLayoutManager(new LinearLayoutManager(this));

        //Main container declarations:
        foodStorage = new IngredientStorage();
        ingredientStorageAdapter = new IngredientStorageAdapter(this, foodStorage.getIngredientStorage());
        ingredientListView.setAdapter(ingredientStorageAdapter);
        foodStorage.setupIngredientSnapshotListener(ingredientStorageAdapter);
        foodStorage.getIngredientStorageFromDatabase();

        IngredientStorage sampleIngredientStorage = new IngredientStorage();
        IngredientInStorage sampleIngredient = new IngredientInStorage("Pop", "kg",
                5, 2022, 4, 24, Location.freezer, Category.meat);
        sampleIngredientStorage.setupIngredientSnapshotListener(); //TODO: need to add the pass value.
        sampleIngredientStorage.addIngredientToStorage(sampleIngredient);



        ingredientButton = findViewById(R.id.ingr_nav);
        recipesButton = findViewById(R.id.recipes_nav);
        mealsButton = findViewById(R.id.meals_nav);
        shoppingButton = findViewById(R.id.shopping_nav);

        ingredientButton.setBackgroundColor(Color.rgb(252, 186, 3));

        recipesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToRecipes();
            }
        });
    }
    // TODO: add bundled info
    private void switchToRecipes() {
        Intent switchActivityIntent = new Intent(this, RecipeListActivity.class);
        startActivity(switchActivityIntent);
    }

    // POSSIBLE TODO: could be used to fix animations
//    @Override
//    public void finish() {
//        super.finish();
//        overridePendingTransition(androidx.navigation.ui.R.anim.nav_default_exit_anim, androidx.navigation.ui.R.anim.nav_default_enter_anim);
//    }

}