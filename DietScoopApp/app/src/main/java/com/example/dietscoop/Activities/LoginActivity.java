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

/**
 * This class handles logging a user into the application
 */
public class LoginActivity extends AppCompatActivity {

    EditText email_ET, password_ET;
    Button confirm_BT;
    TextView signup_BT;
    TextView errorText;

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
        errorText = findViewById(R.id.errorText);

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

    /**
     * This method signs in a user. If successfully signed in, the app opens. Otherwise, the user is notified to re-attempt login
     * @param email email address specified by user
     * @param password password entered by user
     */
    private void signInEmailPassword(String email, String password) {
        if (email.equals("") || password.equals("")) {
            password_ET.setError("Invalid login");
            email_ET.setError("Invalid login");
            errorText.setText("Please enter both an email and password");
            return;
        }
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Log.i("AUTH", "LOGGED IN");
                password_ET.setError(null);
                email_ET.setError(null);
                errorText.setText("");
                openApp();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("AUTH", "LOG IN FAILED");
                password_ET.setError("Invalid login");
                email_ET.setError("Invalid login");
                errorText.setText("Invalid email or password");

            }
        });

    }

    /**
     * Launches app
     */
    private void openApp() {
        Intent intent = new Intent(this, IngredientListActivity.class);
        startActivity(intent);
    }

    /**
     * takes user to page where they can sign up to the application
     */
    private void startSignUp() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}
