package com.example.dietscoop;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * Class tying to Ingredient list. Associated with the activity_ingredient_list.xml layout.
 */
public class IngredientListActivity extends AppCompatActivity implements IngredientAddFragment.OnFragmentInteractionListener, sortIngredientByFragment.OnFragmentInteractionListener, RecyclerItemClickListener {

    IngredientStorage foodStorage;
    IngredientStorageAdapter ingredientStorageAdapter;
    RecyclerView ingredientListView;

    Button ingredientButton;
    Button recipesButton;
    Button mealsButton;
    Button shoppingButton;
    Button sortButton;

    @RequiresApi(api = Build.VERSION_CODES.O)
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
//        //IngredientStorage sampleIngredientStorage = new IngredientStorage();
//        IngredientInStorage sampleIngredient = new IngredientInStorage("Poop", "kg",
//                5, 2022, 4, 25, Location.freezer, Category.meat);
//        //sampleIngredientStorage.setupIngredientSnapshotListener(); //TODO: need to add the pass value.
//        //sampleIngredientStorage.addIngredientToStorage(sampleIngredient);
//        foodStorage.addIngredientToStorage(sampleIngredient);
//
//        sampleIngredient = new IngredientInStorage("Roop", "kg",
//                5, 2022, 5, 25, Location.fridge, Category.meat);
//        foodStorage.addIngredientToStorage(sampleIngredient);
//
//        sampleIngredient = new IngredientInStorage("Toop", "kg",
//                5, 2022, 6, 25, Location.fridge, Category.fruit);
//        foodStorage.addIngredientToStorage(sampleIngredient);
//
//        sampleIngredient = new IngredientInStorage("Scoop", "kg",
//                5, 2022, 6, 25, Location.pantry, Category.vegetable);
//        foodStorage.addIngredientToStorage(sampleIngredient);

        ingredientButton = findViewById(R.id.ingr_nav);
        recipesButton = findViewById(R.id.recipes_nav);
        mealsButton = findViewById(R.id.meals_nav);
        shoppingButton = findViewById(R.id.shopping_nav);
        sortButton = findViewById((R.id.sort_button));

        ingredientButton.setBackgroundColor(Color.rgb(252, 186, 3));

        final FloatingActionButton addIngredientButton = findViewById(R.id.add_new_ingredient_button);
        addIngredientButton.setOnClickListener((v) -> {
            new IngredientAddFragment().show(getSupportFragmentManager(), "ADD_INGREDIENT");
        });

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

        ingredientStorageAdapter.setItemClickListener(this);

    }

    /**
     * handler for switching to Recipe activity.
     */
    private void switchToRecipes() {
        // TODO: add bundled info
        Intent switchActivityIntent = new Intent(this, RecipeListActivity.class);
        startActivity(switchActivityIntent);
        final FloatingActionButton addIngredientButton = findViewById(R.id.add_new_ingredient_button);

        addIngredientButton.setOnClickListener((e) -> {
            new IngredientAddFragment().show(getSupportFragmentManager(), "ADD_INGREDIENT");
        });

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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onSortSelection(sortIngredientByFragment.selection sortBy) {
        foodStorage.sortBy(sortBy);
        ingredientStorageAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, int position) {

        ArrayList<IngredientInStorage> ingredients = foodStorage.getIngredientStorage();
        IngredientInStorage ingredient = ingredients.get(position);
        new IngredientAddFragment(ingredient).show(getSupportFragmentManager(), "EDIT_INGREDIENT");
    }

    // POSSIBLE TODO: could be used to fix animations
//    @Override
//    public void finish() {
//        super.finish();
//        overridePendingTransition(androidx.navigation.ui.R.anim.nav_default_exit_anim, androidx.navigation.ui.R.anim.nav_default_enter_anim);
//    }

}