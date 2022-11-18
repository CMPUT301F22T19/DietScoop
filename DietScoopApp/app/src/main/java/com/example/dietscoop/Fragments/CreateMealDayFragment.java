package com.example.dietscoop.Fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
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

/**
 * DialogFragment that handles the user entry for a new mealday.
 */
public class CreateMealDayFragment extends DialogFragment {

    EditText enterDateText;
    FloatingActionButton addMeal;
    RecyclerView mealRecycler;

    //Containers:
    MealDay currentMealDay;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //Instancing our DialogFragment:
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //Fetching Layout Inflater:
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        //Initializing the main container for this activity:
        currentMealDay = new MealDay(LocalDate.now()); //Temporary placeholder date.

        //Inflating the fragment:
        builder.setView(inflater.inflate(R.layout.add_meal_day_fragment, null))
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

}
