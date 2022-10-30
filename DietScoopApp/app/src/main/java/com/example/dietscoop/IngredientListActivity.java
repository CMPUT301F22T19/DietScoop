package com.example.dietscoop;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.widget.ListView;


public class IngredientListActivity extends AppCompatActivity {

    IngredientStorage foodStorage;
    IngredientStorageAdapter ingredientStorageAdapter;
    ListView ingredientListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_list);
        ingredientListView = findViewById(R.id.ingredient_list);

        //Main container declarations:
        foodStorage = new IngredientStorage();
        ingredientStorageAdapter = new IngredientStorageAdapter(this, foodStorage.getIngredientStorage());
        ingredientListView.setAdapter(ingredientStorageAdapter);
        foodStorage.setupIngredientSnapshotListener(ingredientStorageAdapter);
        foodStorage.getIngredientStorageFromDatabase();

    }
}