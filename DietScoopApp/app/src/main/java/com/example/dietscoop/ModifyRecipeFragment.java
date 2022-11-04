package com.example.dietscoop;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ModifyRecipeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ModifyRecipeFragment extends Fragment {

    private EditText recipeName, recipePrepTime, recipeServings, recipeCategory;
    Recipe recipeToModify;

    public ModifyRecipeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ModifyRecipeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ModifyRecipeFragment newInstance(String param1, String param2) {
        ModifyRecipeFragment fragment = new ModifyRecipeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recipeName =
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_modify_recipe, container, false);
    }
}