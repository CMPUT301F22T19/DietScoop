package com.example.dietscoop;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class IngredientAddFragment extends DialogFragment implements AdapterView.OnItemSelectedListener {
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


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_ingredient_storage_fragment, null);
        description = view.findViewById(R.id.edit_description_ingredient_storage);
        amount = view.findViewById(R.id.edit_amount_ingredient_storage);
        category = view.findViewById(R.id.edit_category_ingredient_storage);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        return builder.create();
    }
}
