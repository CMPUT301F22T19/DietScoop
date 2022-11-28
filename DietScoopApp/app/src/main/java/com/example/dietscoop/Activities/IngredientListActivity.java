package com.example.dietscoop.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.dietscoop.Fragments.IngredientAddFragment;
import com.example.dietscoop.Data.Ingredient.IngredientInStorage;
import com.example.dietscoop.Database.IngredientStorage;
import com.example.dietscoop.Adapters.IngredientStorageAdapter;
import com.example.dietscoop.R;
import com.google.firebase.auth.FirebaseAuth;
import java.util.ArrayList;

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

    /**
     * This enum contains fields by which the user can select to sort the list of ingredients
     */
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
        // RECYCLER
        ingredientStorageAdapter = new IngredientStorageAdapter(this, foodStorage.getIngredientStorage());
        ingredientListView.setAdapter(ingredientStorageAdapter);
        foodStorage.setupIngredientSnapshotListener(ingredientStorageAdapter);
        foodStorage.getIngredientStorageFromDatabase();

        nameSort = findViewById(R.id.name_sort);
        categorySort = findViewById(R.id.category_sort);
        bestBeforeSort = findViewById(R.id.bestbefore_sort);
        locationSort = findViewById(R.id.location_sort);

        ingredientStorageAdapter.setItemClickListener(this);

        // clickable TextViews for sorting by each element
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

    /** This method is called when the user clicks on a column heading to sort the list
     *
     * @param sortBy enum selection that specifies which element to sort the list by
     */
    public void onSortSelection(sortSelection sortBy) {

        if (this.sortingBy == sortBy) {
            return;
        }

        foodStorage.sortBy(sortBy);
        ingredientStorageAdapter.notifyDataSetChanged();
        setSortingItem(sortBy);
    }

    /** This method sets the element that the user chose to sort the list by, as well as update the UI
     * to indicate which element is the chosen one for sorting
     *
     * @param sortBy user-selected element to sort by
     */
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

    /** This item
     *
     * @param view current view
     * @param position index of clicked item in list
     */
    @Override
    public void onItemClick(View view, int position) {
        ArrayList<IngredientInStorage> ingredients = foodStorage.getIngredientStorage();
        IngredientInStorage ingredient = ingredients.get(position);
        new IngredientAddFragment(ingredient).show(getSupportFragmentManager(), "EDIT_INGREDIENT");
    }

    /**
     * This method sets up the top bar that contains a log out button and an add button
     */
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

    /**
     * This method logs out the currently logged in user
     */
    private void logout() {
        FirebaseAuth.getInstance().signOut();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * This method opens a dialog fragment where the user can fill out details of a new ingredient
     */
    void onAddClicked() {
        new IngredientAddFragment().show(getSupportFragmentManager(), "ADD_INGREDIENT");
    }
}