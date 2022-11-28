package com.example.dietscoop.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dietscoop.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    EditText email_ET, password_ET;
    Button confirm_BT;
    FirebaseAuth auth;

    ImageButton back_BT;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        back_BT = findViewById(R.id.back_button);

        auth = FirebaseAuth.getInstance();

        email_ET = findViewById(R.id.signup_email);
        password_ET = findViewById(R.id.signup_password);

        confirm_BT = findViewById(R.id.signup_confirm);

        back_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnToLogin();
            }
        });

        confirm_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = email_ET.getText().toString();
                String password = password_ET.getText().toString();
                signUpEmailPassword(email, password);
            }
        });

    }

    private void signUpEmailPassword(String email, String password) {

        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Log.i("AUTH", "SIGN UP SUCCEEDED");
                openApp();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("AUTH", "SIGN UP FAILED");
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

    private void returnToLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}
