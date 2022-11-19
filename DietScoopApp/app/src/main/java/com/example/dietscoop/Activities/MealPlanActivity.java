package com.example.dietscoop.Activities;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dietscoop.Fragments.AddMealDayFragment;
import com.example.dietscoop.R;

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
 *
 */
public class MealPlanActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meal_plan_activity);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.full_fragment_container_view, AddMealDayFragment.class, null)
                    .commit();
        }

    }

}
