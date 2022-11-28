package com.example.dietscoop.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.example.dietscoop.R;

public class LoginActivity extends AppCompatActivity {

    EditText email_ET, password_ET;
    Button confirm_BT;
    TextView signup_BT;

    FirebaseAuth auth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.i("PROGRESS", "STARTING LOGIN");

        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() != null){
            Intent intent = new Intent(this, IngredientListActivity.class);
            startActivity(intent);
        }

        email_ET = findViewById(R.id.login_email_address);
        password_ET = findViewById(R.id.login_password);

        confirm_BT = findViewById(R.id.login_button);
        signup_BT = findViewById(R.id.sign_up);

        confirm_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = email_ET.getText().toString();
                String password = password_ET.getText().toString();
                signInEmailPassword(email, password);
            }
        });

        signup_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSignUp();
            }
        });
    }

    private void signInEmailPassword(String email, String password) {

        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Log.i("AUTH", "LOGGED IN");
                openApp();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("AUTH", "LOG IN FAILED");
            }
        });

    }

    /**
     * Launches app on successful login
     */
    private void openApp() {
        Intent intent = new Intent(this, IngredientListActivity.class);
        startActivity(intent);
    }

    private void startSignUp() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}
