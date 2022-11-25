package com.example.dietscoop.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dietscoop.Adapters.IngredientRecipeAdapter;
import com.example.dietscoop.Data.Ingredient.Ingredient;
import com.example.dietscoop.Data.Ingredient.IngredientCategory;
import com.example.dietscoop.Data.Ingredient.IngredientInRecipe;
import com.example.dietscoop.Data.Ingredient.IngredientUnit;
import com.example.dietscoop.Database.IngredientStorage;
import com.example.dietscoop.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ShoppingListActivity extends NavigationActivity {
    RecyclerView shoppingListView;
//    TODO: populate neededIngredients using some comparison
    ArrayList<IngredientInRecipe> neededIngredients;
    IngredientRecipeAdapter ingListAdapter;
    TextView descriptionSort, categorySort;

    ActionBar topBar;

    public enum sortSelection {
        DESCRIPTION,
        CATEGORY
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        neededIngredients =  new ArrayList<IngredientInRecipe>();
        neededIngredients.add(new IngredientInRecipe("gabagool", IngredientUnit.g, 3, IngredientCategory.Meat));

        setContentView(R.layout.activity_shopping_list);

        initNavigationActivity();
        navBar.setSelectedItemId(R.id.shopping);
        setUpActionBar();

        shoppingListView = findViewById(R.id.shopping_list);
        shoppingListView.setHasFixedSize(false);
        shoppingListView.setLayoutManager(new LinearLayoutManager(this));

        ingListAdapter = new IngredientRecipeAdapter(this, neededIngredients);

        shoppingListView.setAdapter(ingListAdapter);

        descriptionSort = findViewById(R.id.description_text);
        categorySort = findViewById(R.id.category_text);



    }

    private void setUpActionBar() {

        topBar = getSupportActionBar();
        topBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        topBar.setDisplayShowCustomEnabled(true);
        topBar.setCustomView(R.layout.top_bar_no_add_layout);

        View topBarView = topBar.getCustomView();

        ImageButton logout = topBarView.findViewById(R.id.logout_button);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}
