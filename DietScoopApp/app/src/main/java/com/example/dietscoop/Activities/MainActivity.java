package com.example.dietscoop.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;

import com.example.dietscoop.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends NavigationActivity {

    ActionBar topBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authorize();

        setContentView(R.layout.activity_main);
        initNavigationActivity();
        setUpActionBar();
    }

    private void authorize() {

        FirebaseAuth auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            Log.i("PROGRESS", "Starting Login");
            startActivity(intent);
        }

    }

    private void setUpActionBar() {

        topBar = getSupportActionBar();
        topBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        topBar.setDisplayShowCustomEnabled(true);
        topBar.setCustomView(R.layout.top_bar_no_add_layout);

        View topBarView = topBar.getCustomView();

        ImageButton logout = topBarView.findViewById(R.id.logout_button);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}