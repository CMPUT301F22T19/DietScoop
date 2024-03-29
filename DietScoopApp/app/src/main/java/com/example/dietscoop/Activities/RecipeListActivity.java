package com.example.dietscoop.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.dietscoop.R;
import com.example.dietscoop.Adapters.RecipeListAdapter;
import com.example.dietscoop.Database.RecipeStorage;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Class associated with the activity_recipe_list.xml file.
 * This class handles the relation of Recipe list to display
 */
public class RecipeListActivity extends NavigationActivity implements RecyclerItemClickListener {
    RecyclerView recipeListView;
    RecipeStorage recipeStorage;
    RecipeListAdapter recipeListAdapter;

    TextView titleSort, prepTimeSort, servingSort, categorySort;
    
    ActionBar topBar;

    sortSelection sortingBy;

    /**
     * This enum contains elements that the user can specify to sort the list by
     */
    public enum sortSelection {
        TITLE,
        PREPTIME,
        SERVING,
        CATEGORY
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        initNavigationActivity();
        navBar.setSelectedItemId(R.id.recipes);

        setupActionBar();

        recipeListView = findViewById(R.id.recipe_list);
        recipeListView.setHasFixedSize(false);
        recipeListView.setLayoutManager(new LinearLayoutManager(this));

        recipeStorage = new RecipeStorage();
        recipeListAdapter = new RecipeListAdapter(this, recipeStorage.getRecipeStorage());
        recipeListView.setAdapter(recipeListAdapter);

        recipeStorage.setupRecipeSnapshotListener(recipeListAdapter);
        recipeStorage.getRecipeStorageFromDatabase();

        titleSort = findViewById(R.id.title_sort);
        prepTimeSort = findViewById(R.id.preptime_sort);
        servingSort = findViewById(R.id.serving_sort);
        categorySort = findViewById(R.id.category_sort);

        recipeListAdapter.setItemClickListener(this);

        titleSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortRecipesBy(sortSelection.TITLE);
            }
        });

        prepTimeSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortRecipesBy(sortSelection.PREPTIME);
            }
        });

        servingSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortRecipesBy(sortSelection.SERVING);
            }
        });

        categorySort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortRecipesBy(sortSelection.CATEGORY);
            }
        });

    }

    /**
     * This method navigates to the activity where the user can add a new recipe.
     */
    private void addNewRecipe() {
        Intent intent = new Intent(this, ViewRecipeActivity.class);
        intent.putExtra("ADDING", true);
        startActivity(intent);
    }

    /**
     * This method navigates to the activity where the user can edit an existing recipe
     * @param view current view
     * @param position index of recipe that the user has selected to modify
     */
    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, ViewRecipeActivity.class);
        intent.putExtra("ADDING", false);
        intent.putExtra("RECIPE", recipeStorage.getRecipeStorage().get(position));
        startActivity(intent);
    }

    /**
     * This method sorts the list by the element specified by the user
     * @param sortBy enum element corresponding to column that the user has chosen to sort by
     */
    public void sortRecipesBy(RecipeListActivity.sortSelection sortBy) {

        if (sortBy == this.sortingBy) {
            return;
        }

        recipeStorage.sortBy(sortBy);
        recipeListAdapter.notifyDataSetChanged();

        setSortSelection(sortBy);
    }

    /**
     * This method sets the element that the user has chosen to sort by and updates the UI accordingly
     * @param sortBy
     */
    private void setSortSelection(sortSelection sortBy) {

        this.sortingBy = sortBy;

        switch(this.sortingBy) {
            case TITLE:
                titleSort.setText("Title\n▼");
                prepTimeSort.setText("Prep Time\n━");
                servingSort.setText("Servings\n━");
                categorySort.setText("Category\n━");
                break;
            case SERVING:
                titleSort.setText("Title\n━");
                prepTimeSort.setText("Prep Time\n━");
                servingSort.setText("Servings\n▼");
                categorySort.setText("Category\n━");
                break;
            case CATEGORY:
                titleSort.setText("Title\n━");
                prepTimeSort.setText("Prep Time\n━");
                servingSort.setText("Servings\n━");
                categorySort.setText("Category\n▼");
                break;
            case PREPTIME:
                titleSort.setText("Title\n━");
                prepTimeSort.setText("Prep Time\n▼");
                servingSort.setText("Servings\n━");
                categorySort.setText("Category\n━");
                break;
        }

    }

    /**
     * This method sets up the top bar with an add button and logout button
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
     * This method is calles when the add button in the top bar is pushed, and navigates the user to the add recipe activity
     */
    void onAddClicked() {
        addNewRecipe();
    }

    /**
     * This method logs out the currently logged in user
     */
    private void logout() {
        FirebaseAuth.getInstance().signOut();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}