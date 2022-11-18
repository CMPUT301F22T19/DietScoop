package com.example.dietscoop.Fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dietscoop.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * DialogFragment that handles the user entry for a new mealday.
 */
public class CreateMealDayFragment extends DialogFragment {

    EditText enterDateText;
    FloatingActionButton addMeal;
    RecyclerView mealRecycler;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //Instancing our DialogFragment:
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //Fetching Layout Inflater:
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        //Inflating the fragment:
        builder.setView(inflater.inflate(R.layout.add_meal_day_fragment, null))
                .setPositiveButton(R.id.)



    }

}
