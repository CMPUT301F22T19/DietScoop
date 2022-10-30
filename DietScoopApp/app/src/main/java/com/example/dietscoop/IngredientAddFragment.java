package com.example.dietscoop;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class IngredientAddFragment extends DialogFragment {
    private EditText description;
    private EditText amount;
    private EditText category;
    private EditText day;
    private EditText month;
    private EditText year;
    private EditText location;
    private EditText unit;
    private OnFragmentInteractionListener listener;
    private IngredientInStorage ingredientToBeChanged;

    public interface OnFragmentInteractionListener {
        void onOkPressed(IngredientInStorage newIngredientInStorage);
    }

    public IngredientAddFragment(IngredientInStorage newStorageIngredient) {
        ingredientToBeChanged = newStorageIngredient;
    }

    public IngredientAddFragment() {
        ingredientToBeChanged = null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_ingredient_storage_fragment, null);
        description = view.findViewById(R.id.edit_description_ingredient_storage);
        amount = view.findViewById(R.id.edit_amount_ingredient_storage);
        category = view.findViewById(R.id.edit_category_ingredient_storage);
        location = view.findViewById(R.id.edit_location_ingredient_storage);
        unit = view.findViewById(R.id.edit_unit_ingredient_storage);
        day = view.findViewById(R.id.edit_day_ingredient_storage);
        month = view.findViewById(R.id.edit_month_ingredient_storage);
        year = view.findViewById(R.id.edit_year_ingredient_storage);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        if (ingredientToBeChanged == null) {
            builder
                    .setView(view)
                    .setTitle("Add ingredient")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String strDescription = description.getText().toString();
                            String strAmount = amount.getText().toString();
                            String strCategory = category.getText().toString();
                            String strLocation = location.getText().toString();
                            String strUnit = unit.getText().toString();
                            String strDay = day.getText().toString();
                            String strMonth = month.getText().toString();
                            String strYear = year.getText().toString();
                            Location location = Location.stringToLocation(strLocation);
                            IngredientCategory ingredientCategory = IngredientCategory.stringToCategory(strCategory);
                            listener.onOkPressed(new IngredientInStorage(strDescription, strUnit,
                                    Integer.parseInt(strAmount), Integer.parseInt(strYear), Integer.parseInt(strMonth),
                                    Integer.parseInt(strDay), location, ingredientCategory));
                        }
                    });
        }
        return builder.create();
    }
}
