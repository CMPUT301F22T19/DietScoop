package com.example.dietscoop.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.media.ExifInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.dietscoop.Activities.ViewRecipeActivity;
import com.example.dietscoop.Data.Ingredient.IngredientCategory;
import com.example.dietscoop.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddIngredientToRecipeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddIngredientToRecipeFragment extends DialogFragment {
    Spinner categorySpinner;
    EditText description;
    EditText amount;
    EditText unit;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_add_ingredient_to_recipe, null);

        categorySpinner = (Spinner) view.findViewById(R.id.categorySpinner);
        categorySpinner.setAdapter(new ArrayAdapter<IngredientCategory>(this.getActivity(),
                android.R.layout.simple_spinner_dropdown_item, IngredientCategory.values()));

        description = view.findViewById(R.id.editTextDescription);
        amount = view.findViewById(R.id.editTextAmount);
        unit = view.findViewById(R.id.editTextUnit);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder
                .setView(view)
                .setTitle("End ME")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        ((ViewRecipeActivity)getActivity()).updateInstructions(instructionEntries.getText().toString());

                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
    }

    //    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public AddIngredientToRecipeFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment AddIngredientToRecipeFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static AddIngredientToRecipeFragment newInstance(String param1, String param2) {
//        AddIngredientToRecipeFragment fragment = new AddIngredientToRecipeFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_add_ingredient_to_recipe, container, false);
//    }
}