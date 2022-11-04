package com.example.dietscoop;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

/**
 * Fragment class for prompting user with sorting options for ingredients
 */
public class sortIngredientByFragment extends DialogFragment {
    private Button name, location, category, date;
    private OnFragmentInteractionListener listener;

    public interface OnFragmentInteractionListener {
        void onSortSelection(selection sortBy);
    }

    public enum selection {
        NAME,
        LOCATION,
        CATEGORY,
        DATE
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
            + " must implement the interface method(s)");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_sort_by, null);
        name = view.findViewById(R.id.sort_name);
        location = view.findViewById(R.id.sort_location);
        category = view.findViewById(R.id.sort_category);
        date = view.findViewById(R.id.sort_date);

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSortSelection(selection.NAME);
            }
        });

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSortSelection(selection.LOCATION);
            }
        });

        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSortSelection(selection.CATEGORY);
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSortSelection(selection.DATE);
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        return builder
                .setView(view)
                .setTitle("Sort By")
                .setNegativeButton("Close", null)
                .create();

    }

}
