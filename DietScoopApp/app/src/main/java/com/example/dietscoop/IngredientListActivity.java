package com.example.dietscoop;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class IngredientListActivity extends AppCompatActivity {

    FoodStorage foodStorage;
    IngredientStorageAdapter ingredientStorageAdapter;
    ListView ingredientListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_list);
        ingredientListView = findViewById(R.id.ingredient_list);

        foodStorage = new FoodStorage();
        ingredientStorageAdapter = new IngredientStorageAdapter(this, foodStorage.getIngredientStorage());
        ingredientListView.setAdapter(ingredientStorageAdapter);
        foodStorage.setupIngredientSnapshotListener(ingredientStorageAdapter);
        foodStorage.getIngredientStorageFromDatabase();

        final FloatingActionButton addIngredientButton = findViewById(R.id.add_new_ingredient_button);

        addIngredientButton.setOnClickListener((e) -> {

        });
    }
}