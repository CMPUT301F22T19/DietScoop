package com.example.dietscoop.Fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dietscoop.Activities.MealPlanActivity;
import com.example.dietscoop.Adapters.MealDayRecyclerAdapter;
import com.example.dietscoop.Data.FoodItem;
import com.example.dietscoop.Data.Ingredient.Ingredient;
import com.example.dietscoop.Data.Ingredient.IngredientCategory;
import com.example.dietscoop.Data.Ingredient.IngredientInMealDay;
import com.example.dietscoop.Data.Ingredient.IngredientInStorage;
import com.example.dietscoop.Data.Meal.MealDay;
import com.example.dietscoop.Data.Recipe.Recipe;
import com.example.dietscoop.Data.Recipe.RecipeInMealDay;
import com.example.dietscoop.R;

import org.checkerframework.checker.units.qual.A;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

/**
 * Generic Fragment that handles the events
 * to do with: Viewing, Adding and editing a MealDay.
 */
public class MealDayFragment  extends Fragment{
    Boolean editMealDay = false;

    View container;
    EditText enterDateText;

    RecyclerView mealRecycler;
    MealDayRecyclerAdapter mealRecyclerAdapter;

    DatePickerDialog datePicker;
    LocalDate mealDayDate;

//    Button addRecipeButton;
//    Button addIngredientButton;
    Button addFoodItemButton;
    //Major action buttons:
    Button mealDayConfirm;
    Button mealDayCancel;

//    ArrayList<Recipe> recipesForAdding;
//    ArrayList<IngredientInStorage> ingredientsForAdding;
    ArrayList<FoodItem> allFoodItems;

    String currentDescription; //Holds the desc.
    String currentFoodItemType;


    //Containers:
    int indexOfDay; //Only if editing:
    MealDay currentMealDay = null;

    public MealDayFragment() {
        super(R.layout.meal_day_fragment_v2);
        currentMealDay = new MealDay(LocalDate.now());
        currentMealDay.setId(UUID.randomUUID().toString());
        editMealDay = false;
    }

    public MealDayFragment(Bundle mealDayToEdit) {
        super(R.layout.meal_day_fragment_v2);
        this.editMealDay = true;
        this.currentMealDay = (MealDay) mealDayToEdit.getSerializable("mealdaytoedit");
        this.indexOfDay = mealDayToEdit.getInt("dayindex");
    }

    @NonNull
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstance) {
        container = view;

        //Binding Views:
        initializeViews();

        //Showing a pre-existing date if applicable:
        if (editMealDay) {
            enterDateText.setText(currentMealDay.getDate().toString());
        }


        //Getting options:
//        recipesForAdding = ((MealPlanActivity)getActivity()).getRecipesList();
//        ingredientsForAdding = ((MealPlanActivity)getActivity()).getIngredientsList();
        allFoodItems = ((MealPlanActivity)getActivity()).getAllFoodItems();

        //Setting Adapters:
        mealRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mealRecyclerAdapter = new MealDayRecyclerAdapter(getActivity(), currentMealDay.getFoodItems());
        mealRecycler.setAdapter(mealRecyclerAdapter);

        //Listeners:
        /**
         * Queries user to specify date for current mealday.
         */
        enterDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                //Instancing the dialog view:
                datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        setMealDayDate(day, month, year);
                        enterDateText.setText(mealDayDate.toString());
                    }
                }, year, month, day);
                datePicker.show();
            }
        });

//        addIngredientButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                currentFoodItemType = "Ingredient";
//                // Create an instance of the dialog fragment and show it
//                AddFoodItemFragment dialog = new AddFoodItemFragment(getThisFragment(), ingredientsForAdding);
//                dialog.show(getParentFragmentManager(), "NoticeDialogFragment");
//            }
//        });
//
        addFoodItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an instance of the dialog fragment and show it:
                AddFoodItemFragment dialog = new AddFoodItemFragment(getThisFragment(), allFoodItems);
                dialog.show(getParentFragmentManager(), "NoticeDialogFragment");
            }
        });

        mealDayConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editMealDay) {
                    MealPlanActivity access = ((MealPlanActivity)getActivity());
                    access.makeChangeToDay(indexOfDay, currentMealDay);
                    access.changeToMealPlan();
                } else {
                    MealPlanActivity access = ((MealPlanActivity)getActivity());
                    access.mealDayAdd(currentMealDay);
                    access.changeToMealPlan();
                }
            }
        });

        mealDayCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MealPlanActivity)getActivity()).changeToMealPlan();
            }
        });

    }

    public Fragment getThisFragment() {
        return this;
    }

    public void setMealDayDate(int day, int month, int year) {
        this.mealDayDate = LocalDate.of(year, month, day);
    }

    public void initializeViews() {
        //Initializing Views:
        enterDateText = (EditText) container.findViewById(R.id.meal_day_date_enter);
        mealRecycler = (RecyclerView) container.findViewById(R.id.recycler_in_add_meal_day);
//        addRecipeButton = (Button) container.findViewById(R.id.add_recipe_meal);
//        addIngredientButton = (Button) container.findViewById(R.id.add_ingredient_meal);
        addFoodItemButton = (Button) container.findViewById(R.id.add_food_item_button);
        mealDayConfirm = (Button) container.findViewById(R.id.meal_day_confirm);
        mealDayCancel = (Button) container.findViewById(R.id.meal_day_cancel);
    }

    public void updateAdapter() {
        mealRecyclerAdapter.notifyDataSetChanged();
    }

    /**
     * Adds a FoodItem to our MealDay.
     */
    public void addMeal(int selectedFoodItem, double scale) {
        if (currentFoodItemType.equals("Ingredient")) {
            IngredientInStorage tempHolder = (IngredientInStorage) (allFoodItems.get(selectedFoodItem));
            IngredientInMealDay tempMeal = new IngredientInMealDay(tempHolder);
            tempMeal.setAmount(scale);
            tempMeal.setId(UUID.randomUUID().toString());
            currentMealDay.addIngredientInMealDay(tempMeal);
        } else if (currentFoodItemType.equals("Recipe")) {
            Recipe tempHolder = (Recipe) allFoodItems.get(selectedFoodItem);
            RecipeInMealDay tempMeal = new RecipeInMealDay(tempHolder);
            tempMeal.setScalingFactor(scale/(tempHolder.getNumOfServings()));
            tempMeal.setId(UUID.randomUUID().toString());
            currentMealDay.addRecipeInMealDay(tempMeal);
        }

        //Updating Adapter:
        this.mealRecyclerAdapter.notifyDataSetChanged();

    }

    public void setCurrentFoodItemType(String type) {
        this.currentFoodItemType = type; //TODO: finish this connection on the Dialog side.
    }

}
