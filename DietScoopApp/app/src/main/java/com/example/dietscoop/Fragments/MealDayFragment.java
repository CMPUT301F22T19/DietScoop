package com.example.dietscoop.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dietscoop.Data.Meal.MealDay;
import com.example.dietscoop.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.util.Calendar;

/**
 * Generic Fragment that handles the events
 * to do with: Viewing, Adding and editing a MealDay.
 */
public class MealDayFragment  extends Fragment {

    View container;
    EditText enterDateText;
    RecyclerView mealRecycler;
    DatePickerDialog datePicker;
    LocalDate mealDayDate;
    Button addRecipeButton;
    Button addIngredientButton;

    //Containers:
    MealDay currentMealDay;

    public MealDayFragment() {
        super(R.layout.meal_day_fragment);
    }

    @NonNull
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstance) {
        container = view;

        //Initializing the main container for this activity:
        currentMealDay = new MealDay(LocalDate.now()); //Temporary placeholder date.

        //Binding Views:
        initializeViews();

//        Listeners:

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

        /**
         * Queries user to select a new ingredient into the current mealday.
         */
        addIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    public void setMealDayDate(int day, int month, int year) {
        this.mealDayDate = LocalDate.of(year, month, day);
    }

    public void initializeViews() {
        //Initializing Views:
        enterDateText = (EditText) container.findViewById(R.id.meal_day_date_enter);
        mealRecycler = (RecyclerView) container.findViewById(R.id.recycler_in_add_meal_day);
        addRecipeButton = (Button) container.findViewById(R.id.add_ingredient_meal);
        addIngredientButton = (Button) container.findViewById(R.id.add_ingredient_meal);
    }

}
