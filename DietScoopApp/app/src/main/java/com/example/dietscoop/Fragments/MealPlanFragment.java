package com.example.dietscoop.Fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dietscoop.Activities.MealPlanActivity;
import com.example.dietscoop.Activities.RecyclerItemClickListener;
import com.example.dietscoop.Adapters.MealPlanRecyclerAdapter;
import com.example.dietscoop.Data.Meal.MealDay;
import com.example.dietscoop.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MealPlanFragment extends Fragment implements RecyclerItemClickListener {

    View fragmentView;
    Boolean existsMealPlan = false;

    FloatingActionButton addMealDayButton;
    RecyclerView mealDayRecycler;
    MealPlanRecyclerAdapter mealPlanAdapter;

    ArrayList<MealDay> mealDays;

    Bundle message; //Holder for information from other activies.

    /**
     * Constructor handles the creation of a mealplan clone for the user
     * to edit.
     * @param mealPlanSent Bundle
     */
    public MealPlanFragment(Bundle mealPlanSent) {
        super(R.layout.meal_plan_fragment);
        this.mealDays = (ArrayList<MealDay>) mealPlanSent.getSerializable("mealplan");
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        fragmentView = view;

        initializeViews();

        //Setting up the Listener:
        mealPlanAdapter.setEntryListener(this);

        addMealDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MealPlanActivity) getActivity()).changeToMealDayAdd();
            }
        });

        //For the recycler: //TODO: Need to fix the setOnClickListener.
        mealDayRecycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MealPlanActivity) getActivity()).changeToMealDayEdit(mealDayRecycler.getChildLayoutPosition(view));
            }
        });

    }

    public void initializeViews() {
        mealDayRecycler = (RecyclerView) fragmentView.findViewById(R.id.recycler_for_meal_plans);
        mealPlanAdapter = new MealPlanRecyclerAdapter(getActivity(), mealDays);
        mealDayRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mealDayRecycler.setAdapter(mealPlanAdapter);
        addMealDayButton = (FloatingActionButton) fragmentView.findViewById(R.id.add_mealday_button);
    }

//    @Override
    public void onResume() {
        super.onResume();
        this.mealDays = ((MealPlanActivity)getActivity()).getMealDays();
        this.mealPlanAdapter.changeDataSet(mealDays);
    }

    @Override
    public void onItemClick(View view, int position) {
        //Send over information to the MealDayFragment to edit an existing day:
        ((MealPlanActivity)getActivity()).changeToMealDayEdit(position);
    }

}
