package com.example.dietscoop;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class IngredientListActivity extends AppCompatActivity implements IngredientAddFragment.OnFragmentInteractionListener {

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
            new IngredientAddFragment().show(getSupportFragmentManager(), "ADD_INGREDIENT");
        });

        ingredientListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                new IngredientAddFragment(foodStorage.getIngredientStorage().get(i)).show(getSupportFragmentManager(), "MODIFY_INGREDIENT");
            }
        });
    }

    @Override
    public void onOkPressed(IngredientInStorage newIngredientInStorage) {
        ingredientStorageAdapter.addIngredientStorage(newIngredientInStorage);
    }
}