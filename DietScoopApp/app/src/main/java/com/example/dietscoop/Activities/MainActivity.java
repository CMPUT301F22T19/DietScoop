package com.example.dietscoop.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.example.dietscoop.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends NavigationActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authorize();

        setContentView(R.layout.activity_main);
        initNavigationActivity();

    }

    private void authorize() {

        FirebaseAuth auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            Log.i("PROGRESS", "Starting Login");
            startActivity(intent);
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.add_button:
                Log.i("MENU BAR", "ADDING");
                break;
            case R.id.logout_button:
                Log.i("MENU BAR", "LOGOUT");
                break;
        }
        return true;
    }

    @Override
    void onAddClicked() {
        // Does nothing on main screen
        // main screen eventually removed?
    }
}