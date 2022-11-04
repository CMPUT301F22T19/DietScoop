package com.example.dietscoop;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

/**
 * Fragment handles the editing of the instructions entry in
 * the recipe view.
 */
public class EditInstructionsEntryFragmnet extends DialogFragment {

    private EditText instructionEntries;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstance) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.edit_instructions_recipe, null);
        instructionEntries = view.findViewById(R.id.edit_instructions_in_recipe);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder
                .setView(view)
                .setTitle("Edit Instructions")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ((ViewRecipe)getActivity()).updateInstructions(instructionEntries.getText().toString());
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
    }

}
