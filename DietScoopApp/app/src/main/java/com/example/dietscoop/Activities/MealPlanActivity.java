package com.example.dietscoop.Activities;


import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dietscoop.Adapters.MealPlanRecyclerAdapter;
import com.example.dietscoop.Data.Meal.MealDay;
import com.example.dietscoop.Fragments.CreateMealDayFragment;
import com.example.dietscoop.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * This activity will handle the MealPlanner and will go through instantiating a new
 * MealPlan Object to contain the meals per day for each day.
 *
 * 1. Need a means to add mealdays.
 * 2. Needs a means to generate fragments.
 * 3. Need to store everything back in this activity and send any updates to the firestore database.
 * 4. Need to add the event for the floating button to query the user to add a new mealday.
 *
 * Workflow:
 *
 *  1. Query Database to check if mealplan already exists.
 *  2. If exists -> fetch it and update the days that have passed. (Delete them)
 *  3. If not exists -> query the user to create a new mealplan.
 *  4. User can select the first date 
 *
 */
public class MealPlanActivity extends AppCompatActivity {

    FloatingActionButton addMealDayButton;
    RecyclerView mealDayRecycler;
    MealPlanRecyclerAdapter mealPlanAdapter;
    ArrayList<MealDay> mealDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_plan);

        //TESTING!!!!:

        //Handling RecyclerView: *
        mealDayRecycler = (RecyclerView) findViewById(R.id.recycler_for_meal_plans);
        mealDayRecycler.setLayoutManager(new LinearLayoutManager(this));

        //Instancing our sample day:
        LocalDate now = LocalDate.now();
        MealDay testDay = new MealDay(LocalDate.now());

        mealDays = new ArrayList<>();

        mealDays.add(testDay);

        //Testing our adapter: TODO: Keep most of the following lines as they work.
        mealPlanAdapter = new MealPlanRecyclerAdapter(this, mealDays);
        mealDayRecycler.setLayoutManager(new LinearLayoutManager(this));
        mealDayRecycler.setAdapter(mealPlanAdapter);

        //Handling Button: *
        addMealDayButton = (FloatingActionButton) findViewById(R.id.add_mealday_button);

        /**
         * This onclick method will handle the fragment-chain for adding mealdays.
         */
        addMealDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment addMealDayFragment = new CreateMealDayFragment();
                addMealDayFragment.show(getSupportFragmentManager(), "Add Meal Day");
            }
        });

    }

}
