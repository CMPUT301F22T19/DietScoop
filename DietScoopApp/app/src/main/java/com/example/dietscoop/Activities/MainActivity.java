package com.example.dietscoop.Activities;

import androidx.appcompat.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;

import com.example.dietscoop.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends NavigationActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authorize();

        setContentView(R.layout.activity_main);
        initNavBar();

//        https://www.digitalocean.com/community/tutorials/android-custom-action-bar-example-tutorial
        ActionBar topBar = getSupportActionBar();
        topBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        topBar.setDisplayShowCustomEnabled(true);
        topBar.setCustomView(R.layout.top_bar_layout);

        View topBarView = topBar.getCustomView();

        ImageButton logout = topBarView.findViewById(R.id.logout_button);
        ImageButton addItem = topBarView.findViewById(R.id.add_button);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TOP BAR", "LOGOUT");
            }
        });

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TOP BAR", "ADDING");
            }
        });

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
}