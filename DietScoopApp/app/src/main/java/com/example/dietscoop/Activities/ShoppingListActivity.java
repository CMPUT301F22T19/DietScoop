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
import com.example.dietscoop.Data.Ingredient.IngredientInRecipe;
import com.example.dietscoop.Data.Ingredient.IngredientInStorage;
import com.example.dietscoop.Database.ShoppingListInfo;
import com.example.dietscoop.Fragments.pickUpIngredientFragment;
import com.example.dietscoop.R;
import com.google.firebase.auth.FirebaseAuth;

/**
 * This class handles the activity where the user views their shopping list
 */
public class ShoppingListActivity extends NavigationActivity implements RecyclerItemClickListener, pickUpIngredientFragment.OnFragmentInteractionListener {
    RecyclerView shoppingListView;

    IngredientRecipeAdapter ingListAdapter;
    TextView descriptionSort, categorySort;

    ActionBar topBar;

    ShoppingListInfo shoppingListInfo;
    sortSelection sortingBy;


    /**
     * This method is called when a user clicks on an item in their shopping list. This method opens
     * a dialog fragment that allows the user to add the selected ingredient to their ingredient storage.
     * @param view current view
     * @param position index of item clicked
     */
    @Override
    public void onItemClick(View view, int position) {

        IngredientInRecipe current = shoppingListInfo.getShoppingList().get(position);
        new pickUpIngredientFragment(current).show(getSupportFragmentManager(), "PICKUP SHOPPING ITEM");

    }

    /**
     * This method is called from within the dialog fragment allowing the user to confirm that they want to add
     * the selected item to their ingredient storage
     * @param ingredient ingredient to be added to storage
     */
    @Override
    public void onAddPressed(IngredientInStorage ingredient) {
        shoppingListInfo.addItemToStorage(ingredient);
    }

    /**
     * qualities to sort shopping list by
     */
    public enum sortSelection {
        DESCRIPTION,
        CATEGORY
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
        initNavigationActivity();
        navBar.setSelectedItemId(R.id.shopping);
        setUpActionBar();

        shoppingListView = findViewById(R.id.shopping_list);
        shoppingListView.setHasFixedSize(false);
        shoppingListView.setLayoutManager(new LinearLayoutManager(this));

        shoppingListInfo = new ShoppingListInfo();
        ingListAdapter = new IngredientRecipeAdapter(this, shoppingListInfo.getShoppingList());
        ingListAdapter.setItemClickListener(this);

        shoppingListInfo.setUpSnapshotListeners(ingListAdapter);
        shoppingListView.setAdapter(ingListAdapter);

        descriptionSort = findViewById(R.id.shopping_description_text);
        categorySort = findViewById(R.id.shopping_category_text);

        descriptionSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSortSelection(sortSelection.DESCRIPTION);
            }
        });

        categorySort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSortSelection(sortSelection.CATEGORY);
            }
        });

    }

    /**
     * Sorts the shopping list by the selected element
     * @param selection element to sort by
     */
    private void onSortSelection(sortSelection selection) {

        if (this.sortingBy == selection) {
            return;
        }

        shoppingListInfo.sortBy(selection);
        ingListAdapter.notifyDataSetChanged();
        setSortingItem(selection);

    }

    /**
     * This method sets the element that the user selected to sort the list by, and updates the UI
     * @param selection element that the user selected to sort by
     */
    private void setSortingItem(sortSelection selection) {

        this.sortingBy = selection;

        switch(this.sortingBy) {
            case CATEGORY:
                descriptionSort.setText("Name\n━");
                categorySort.setText("Category\n▼");
                break;
            case DESCRIPTION:
                descriptionSort.setText("Name\n▼");
                categorySort.setText("Category\n━");
                break;
        }
    }

    /**
     * Sets up the top bar with a logout button but no add button
     */
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

    /**
     * Logs out the currently logged in user
     */
    private void logout() {
        FirebaseAuth.getInstance().signOut();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    
}
