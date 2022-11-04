package com.example.dietscoop;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ModifyRecipeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ModifyRecipeFragment extends DialogFragment {

    private EditText recipeName, recipePrepTime, recipeServings, recipeCategory;
    private Recipe recipeToModify;
    private OnFragmentInteractionListener listener;

    ModifyRecipeFragment(){}

    ModifyRecipeFragment(Recipe recipeToModify){
        this.recipeToModify = recipeToModify;
    }

    public interface OnFragmentInteractionListener {
        void onOkPressed(Recipe recipeToChange, String newName, Integer newPrepTime, Integer newServings, recipeCategory newCategory);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + "must implement OnFragmentInteractionListener");
        }
    }

    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_modify_recipe, null);
        recipeName = view.findViewById(R.id.editRecipeName);
        recipePrepTime = view.findViewById(R.id.editRecipePrepTime);
        recipeServings = view.findViewById(R.id.editRecipeServings);
        recipeCategory = view.findViewById(R.id.editRecipeCategory);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Modify Recipe")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String recipeNewName = recipeName.getText().toString();
                        String recipeNewPrepTime = recipePrepTime.getText().toString();
                        String recipeNewServings = recipeServings.getText().toString();
                        String recipeNewCategory = recipeCategory.getText().toString();
                        listener.onOkPressed(recipeToModify, recipeNewName, Integer.valueOf(recipeNewPrepTime), Integer.valueOf(recipeNewServings), com.example.dietscoop.recipeCategory.stringToRecipeCategory(recipeNewCategory));
                    }
                }).create();
    }
}