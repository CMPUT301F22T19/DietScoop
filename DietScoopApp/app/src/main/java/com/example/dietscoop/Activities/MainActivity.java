package com.example.dietscoop.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.dietscoop.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends NavigationActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authorize();

        setContentView(R.layout.activity_main);
        initNavBar();
    }

    private void authorize() {

        FirebaseAuth auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            Log.i("PROGRESS", "Starting Login");
            startActivity(intent);
        }

    }

}