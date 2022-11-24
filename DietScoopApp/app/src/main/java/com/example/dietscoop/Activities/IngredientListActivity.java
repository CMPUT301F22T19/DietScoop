package com.example.dietscoop.Activities;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.dietscoop.Database.RecipeStorage;
import com.example.dietscoop.Fragments.IngredientAddFragment;
import com.example.dietscoop.Data.Ingredient.IngredientInStorage;
import com.example.dietscoop.Database.IngredientStorage;
import com.example.dietscoop.Adapters.IngredientStorageAdapter;
import com.example.dietscoop.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class tying to Ingredient list. Associated with the activity_ingredient_list.xml layout.
 */
public class IngredientListActivity extends NavigationActivity implements IngredientAddFragment.OnFragmentInteractionListener, RecyclerItemClickListener {

    IngredientStorage foodStorage;
    IngredientStorageAdapter ingredientStorageAdapter;
    RecyclerView ingredientListView;

    TextView nameSort, categorySort, bestBeforeSort, locationSort;

    ActionBar topBar;

    sortSelection sortingBy;

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

        initNavigationActivity();
        navBar.setSelectedItemId(R.id.ingredients);

        setupActionBar();

        ingredientListView = findViewById(R.id.ingredient_list);
        ingredientListView.setHasFixedSize(false);
        ingredientListView.setLayoutManager(new LinearLayoutManager(this));

        //Main container declarations:
        foodStorage = new IngredientStorage();
        ingredientStorageAdapter = new IngredientStorageAdapter(this, foodStorage.getIngredientStorage());
        ingredientListView.setAdapter(ingredientStorageAdapter);
        foodStorage.setupIngredientSnapshotListener(ingredientStorageAdapter);
        foodStorage.getIngredientStorageFromDatabase();

        nameSort = findViewById(R.id.name_sort);
        categorySort = findViewById(R.id.category_sort);
        bestBeforeSort = findViewById(R.id.bestbefore_sort);
        locationSort = findViewById(R.id.location_sort);

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

    public void onSortSelection(sortSelection sortBy) {

        if (this.sortingBy == sortBy) {
            return;
        }

        foodStorage.sortBy(sortBy);
        ingredientStorageAdapter.notifyDataSetChanged();
        setSortingItem(sortBy);
    }

    private void setSortingItem(sortSelection sortBy) {

        this.sortingBy = sortBy;

        switch (this.sortingBy) {
            case DATE:
                nameSort.setText("Name\n━");
                categorySort.setText("Category\n━");
                locationSort.setText("Location\n━");
                bestBeforeSort.setText("Expiry\n▼");
                break;
            case NAME:
                nameSort.setText("Name\n▼");
                categorySort.setText("Category\n━");
                locationSort.setText("Location\n━");
                bestBeforeSort.setText("Expiry\n━");
                break;
            case CATEGORY:
                nameSort.setText("Name\n━");
                categorySort.setText("Category\n▼");
                locationSort.setText("Location\n━");
                bestBeforeSort.setText("Expiry\n━");
                break;
            case LOCATION:
                nameSort.setText("Name\n━");
                categorySort.setText("Category\n━");
                locationSort.setText("Location\n▼");
                bestBeforeSort.setText("Expiry\n━");
                break;
        }

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

    private void logout() {
        FirebaseAuth.getInstance().signOut();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    void onAddClicked() {
        new IngredientAddFragment().show(getSupportFragmentManager(), "ADD_INGREDIENT");
    }

}