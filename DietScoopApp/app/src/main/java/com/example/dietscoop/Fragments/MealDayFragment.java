package com.example.dietscoop.Fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dietscoop.Activities.MealPlanActivity;
import com.example.dietscoop.Activities.RecyclerItemClickListener;
import com.example.dietscoop.Adapters.MealDayRecyclerAdapter;
import com.example.dietscoop.Data.FoodItem;
import com.example.dietscoop.Data.Ingredient.IngredientInMealDay;
import com.example.dietscoop.Data.Ingredient.IngredientInStorage;
import com.example.dietscoop.Data.Ingredient.IngredientUnit;
import com.example.dietscoop.Data.Meal.MealDay;
import com.example.dietscoop.Data.Recipe.Recipe;
import com.example.dietscoop.Data.Recipe.RecipeInMealDay;
import com.example.dietscoop.R;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

/**
 * Generic Fragment that handles the events
 * to do with: Viewing, Adding and editing a MealDay.
 */
public class MealDayFragment  extends Fragment implements RecyclerItemClickListener {
    Boolean editMealDay = false;

    View container;
    TextView DateText;

    RecyclerView mealRecycler;
    MealDayRecyclerAdapter mealRecyclerAdapter;

    DatePickerDialog datePicker;
    LocalDate mealDayDate;

    Button addFoodItemButton;

    //Major action buttons:
    Button mealDayConfirm;
    Button mealDayCancel;
    Button editDate;

    ArrayList<FoodItem> allFoodItems;
    ArrayList<FoodItem> foodItemsToDelete = new ArrayList<>(); //Only valid if we are editing.

    String currentFoodItemType;

    //Containers:
    int indexOfDay; //Only if editing:
    MealDay currentMealDay = null;

    public MealDayFragment() {
        super(R.layout.meal_day_fragment_v2);
        currentMealDay = new MealDay(LocalDate.of(1800,1,1));
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

        ((MealPlanActivity) getActivity()).actionBarNoLogout();

        //Binding Views:
        initializeViews();

        //Showing a pre-existing date if applicable:
        if (editMealDay) {
            DateText.setText(currentMealDay.getDate().toString());
        }

        //Getting options:
        allFoodItems = ((MealPlanActivity)getActivity()).getAllFoodItems();

        //Setting Adapters:
        mealRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mealRecyclerAdapter = new MealDayRecyclerAdapter(getActivity(), currentMealDay.getFoodItems());
        mealRecycler.setAdapter(mealRecyclerAdapter);
        mealRecyclerAdapter.setEntryListener(this);

        //Listeners:
        /**
         * Queries user to specify date for current mealday.
         */
        editDate.setOnClickListener(new View.OnClickListener() {
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
                        setMealDayDate(day, month + 1, year);
                        DateText.setTextSize(24);
                        DateText.setError(null);
                        DateText.setText(mealDayDate.toString());
                    }
                }, year, month, day);
                datePicker.show();
            }
        });

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
                if (errorCheck()) {
                    if (editMealDay) {
                        MealPlanActivity access = ((MealPlanActivity) getActivity());
                        access.makeChangeToDay(indexOfDay, currentMealDay, foodItemsToDelete); //TODO: Test if has no id when editing:
                        access.changeToMealPlan();
                    } else {
                        MealPlanActivity access = ((MealPlanActivity) getActivity());
                        access.mealDayAdd(currentMealDay);
                        access.changeToMealPlan();
                    }
                }
            }
        });

        mealDayCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MealPlanActivity)getActivity()).changeToMealPlan();
                ((MealPlanActivity)getActivity()).setupActionBar();
            }
        });

    }

    public Fragment getThisFragment() {
        return this;
    }

    public void setMealDayDate(int day, int month, int year) {
        this.mealDayDate = LocalDate.of(year, month, day);
        this.currentMealDay.setDate(mealDayDate);
    }

    public void initializeViews() {
        //Initializing Views:
        DateText = (TextView) container.findViewById(R.id.meal_day_date);
        mealRecycler = (RecyclerView) container.findViewById(R.id.recycler_in_add_meal_day);
        addFoodItemButton = (Button) container.findViewById(R.id.add_food_item_button);
        mealDayConfirm = (Button) container.findViewById(R.id.meal_day_confirm);
        mealDayCancel = (Button) container.findViewById(R.id.meal_day_cancel);
        editDate = (Button) container.findViewById(R.id.meal_day_date_enter);
    }

    public void updateAdapter() {
        mealRecyclerAdapter.notifyDataSetChanged();
    }

    /**
     * Method adds the stated meal to the current day in view and
     * also sets it a unique UID for the database.
     * @param selectedFoodItem
     * @param scale
     */
    public void addMeal(int selectedFoodItem, Double scale, IngredientUnit ingredientUnit) {
        if (this.allFoodItems.get(selectedFoodItem).getType().equals("Ingredient")) {
            IngredientInStorage tempHolder = (IngredientInStorage) (allFoodItems.get(selectedFoodItem));
            IngredientInMealDay tempMeal = new IngredientInMealDay(tempHolder);
            tempMeal.setAmount(scale);
            tempMeal.setId(UUID.randomUUID().toString());
            tempMeal.setMealdayID(currentMealDay.getId());
            tempMeal.setMeasurementUnit(ingredientUnit);
            currentMealDay.addIngredientInMealDay(tempMeal);
        } else if (this.allFoodItems.get(selectedFoodItem).getType().equals("Recipe")) {
            Recipe tempHolder = (Recipe) allFoodItems.get(selectedFoodItem);
            RecipeInMealDay tempMeal = new RecipeInMealDay(tempHolder);
            tempMeal.setIngredientRefs(tempHolder.getIngredientRefs());
            tempMeal.setDesiredNumOfServings(scale);
            tempMeal.setId(UUID.randomUUID().toString());
            tempMeal.setMealdayID(currentMealDay.getId());
            currentMealDay.addRecipeInMealDay(tempMeal);
        }

        //Updating Adapter:
        this.mealRecyclerAdapter.notifyDataSetChanged();
    }

    public void editMeal(int selectedFoodItem, Double scale, int mealToChange, IngredientUnit ingredientUnit) {
        deleteMeal(mealToChange);
        addMeal(selectedFoodItem, scale, ingredientUnit);
    }

    // TODO: STAGE CHANGES TO DELETE FROM DATABASE
    public void deleteMeal(int mealToChange) {
        foodItemsToDelete.add(currentMealDay.getFoodItems().get(mealToChange)); //For deletion staging.
        if (this.currentMealDay.getFoodItems().get(mealToChange).getType().equals("Ingredient")) {
            currentMealDay.removeIngredientInMealDay(mealToChange);
        } else {
            currentMealDay.removeRecipeInMealDay(mealToChange);
        }

        this.mealRecyclerAdapter.notifyDataSetChanged();
    }

    public void setCurrentFoodItemType(String type) {
        this.currentFoodItemType = type; //TODO: finish this connection on the Dialog side.
    }

    /**
     * For editing a meal:
     * @param view the View fetched from the Recycler.
     * @param position the position from the recycler view fetched.
     */
    @Override
    public void onItemClick(View view, int position) {
        Double defaultQuant;
        if (this.currentMealDay.getFoodItems().get(position).getType() == "Ingredient") {
            defaultQuant = ((IngredientInMealDay)(this.currentMealDay.getFoodItems().get(position))).getAmount();
        } else {
            defaultQuant = ((RecipeInMealDay)(this.currentMealDay.getFoodItems().get(position))).getDesiredNumOfServings();
        }

        AddFoodItemFragment dialog = new AddFoodItemFragment(getThisFragment(), allFoodItems, position, defaultQuant);
        dialog.show(getParentFragmentManager(), "NoticeDialogFragment");
    }

    public int getSelectedFoodItemIndex(int mealIndex) {
        int counter = 0;
        for (FoodItem i : allFoodItems) {
            if (i.getDescription().equals(currentMealDay.getFoodItems().get(mealIndex).getDescription())) {
                return counter;
            }
            counter++;
        }

        return -1;
    }

    private boolean errorCheck() {
        boolean isAllValid = true;
        if(currentMealDay.getDate().getYear()==1800){
            isAllValid = false;
            DateText.setError("Please select a date!");
            DateText.setTextSize(16);
            DateText.setText("Please select!");
        } else {
            DateText.setError(null);
        }
        return isAllValid;
    }
}
