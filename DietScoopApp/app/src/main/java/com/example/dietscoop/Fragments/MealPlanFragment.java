package com.example.dietscoop.Fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dietscoop.Activities.MealPlanActivity;
import com.example.dietscoop.Adapters.MealPlanRecyclerAdapter;
import com.example.dietscoop.Data.Meal.MealDay;
import com.example.dietscoop.Data.Meal.MealPlan;
import com.example.dietscoop.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.util.ArrayList;

public class MealPlanFragment extends Fragment {

    Boolean existsMealPlan = false;

    FloatingActionButton addMealDayButton;
    RecyclerView mealDayRecycler;
    MealPlanRecyclerAdapter mealPlanAdapter;

    ArrayList<MealDay> mealDays;

    Bundle message; //Holder for information from other activies.

    public MealPlanFragment() {
        super(R.layout.meal_plan_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        message = savedInstanceState;

        if (message != null) {
            this.mealDays = (ArrayList<MealDay>) message.getSerializable("mealplan");
            existsMealPlan = true;
        } else {
            this.mealDays = new ArrayList<>();
        }

        mealDayRecycler = (RecyclerView) view.findViewById(R.id.recycler_for_meal_plans);
        mealPlanAdapter = new MealPlanRecyclerAdapter(getActivity(), mealDays);
        mealDayRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mealDayRecycler.setAdapter(mealPlanAdapter);

        //Handling Button: *
        addMealDayButton = (FloatingActionButton) view.findViewById(R.id.add_mealday_button);

        /**
         * Method for adding a new MealDay.
         */
        addMealDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MealPlanActivity) getActivity()).changeToMealDay();
            }
        });


        /**
         * This onclick method will handle the dialog fragment-chain for adding mealdays.
         */
//        addMealDayButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DialogFragment addMealDayFragment = new CreateMealDayFragment();
//                addMealDayFragment.show(getActivity().getSupportFragmentManager(), "Add Meal Day");
//            }
//        });

    }

//    @Override
    public void onResume() {
        super.onResume();
        this.mealDays = ((MealPlanActivity)getActivity()).getMealDays();
        this.mealPlanAdapter.changeDataSet(mealDays);
    }

}
