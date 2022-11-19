package com.example.dietscoop.Fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dietscoop.Adapters.MealPlanRecyclerAdapter;
import com.example.dietscoop.Data.Meal.MealDay;
import com.example.dietscoop.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.util.ArrayList;

public class AddMealDayFragment extends Fragment {

    FloatingActionButton addMealDayButton;
    RecyclerView mealDayRecycler;
    MealPlanRecyclerAdapter mealPlanAdapter;
    ArrayList<MealDay> mealDays;

    public AddMealDayFragment() {
        super(R.layout.activity_meal_plan);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        //Handling RecyclerView: *
        mealDayRecycler = (RecyclerView) view.findViewById(R.id.recycler_for_meal_plans);
        mealDayRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        //TESTING!!!!:******************************************************************

        //Instancing our sample day:
        LocalDate now = LocalDate.now();
        MealDay testDay = new MealDay(LocalDate.now());

        mealDays = new ArrayList<>();

        mealDays.add(testDay);

        //TESTING!!!!:******************************************************************

        //Testing our adapter: TODO: Keep most of the following lines as they work.
        mealPlanAdapter = new MealPlanRecyclerAdapter(getActivity(), mealDays);
        mealDayRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mealDayRecycler.setAdapter(mealPlanAdapter);

        //Handling Button: *
        addMealDayButton = (FloatingActionButton) view.findViewById(R.id.add_mealday_button);

        /**
         * This onclick method will handle the dialog fragment-chain for adding mealdays.
         */
        addMealDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment addMealDayFragment = new CreateMealDayFragment();
                addMealDayFragment.show(getActivity().getSupportFragmentManager(), "Add Meal Day");
            }
        });

    }

}
