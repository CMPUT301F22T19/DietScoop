package com.example.dietscoop.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dietscoop.Data.Meal.Meal;
import com.example.dietscoop.Data.Meal.MealDay;
import com.example.dietscoop.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * DialogFragment that handles the user entry for a new mealday.
 */
public class CreateMealDayFragment extends DialogFragment {

    EditText enterDateText;
    FloatingActionButton addMeal;
    RecyclerView mealRecycler;
    DatePickerDialog datePicker;
    LocalDate mealDayDate;

    //Containers:
    MealDay currentMealDay;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //Instancing our DialogFragment:
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View fragmentView = inflater.inflate(R.layout.add_meal_day_fragment, null);

        initializeViews(fragmentView);

        //Initializing the main container for this activity:
        currentMealDay = new MealDay(LocalDate.now()); //Temporary placeholder date.

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

        //Inflating the fragment:
        builder.setView(fragmentView)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //TODO: Implement the acceptance of a meal day.
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //TODO: Implement the cancellation of a meal day.
                    }
                });

        return builder.create();
    }

    public void setMealDayDate(int day, int month, int year) {
        this.mealDayDate = LocalDate.of(year, month, day);
    }

    public void initializeViews(View view) {
        //Initializing Views:
        enterDateText = (EditText) view.findViewById(R.id.meal_day_date_enter);
        addMeal = (FloatingActionButton) view.findViewById(R.id.add_mealday_button);
        mealRecycler = (RecyclerView) view.findViewById(R.id.recycler_in_add_meal_day);
    }

}
