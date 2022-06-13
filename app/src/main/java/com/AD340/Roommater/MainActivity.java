package com.AD340.Roommater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.time.LocalDate;
import java.time.Period;

public class MainActivity extends AppCompatActivity {

    private EditText loginEmailField;
    private EditText loginPasswordField;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private AlertDialog alert;
    private static final String TAG = "EmailPassword";
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginEmailField = findViewById(R.id.login_email);
        loginPasswordField = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        loginButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                onLogin(view);
            }
        });

    }

    public void onLogin(View view){
        String email = loginEmailField.getText().toString();
        String password = loginPasswordField.getText().toString();
        //check for null
        if (email.equals("") || password.equals("") ){
            Toast.makeText(getApplicationContext(),getString(R.string.please_enter_a_valid_content),Toast.LENGTH_LONG).show();
            return;
        }
        //validate email
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(getApplicationContext(),getString(R.string.please_enter_a_valid_email),Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Log.d(TAG, "signInWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);

                    Intent intent = new Intent(getApplicationContext(),WelcomeActivity.class);
                    startActivity(intent);

                }else{
                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                    Toast.makeText(MainActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });

    }

    public void onSignUp(View view){
        Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
        startActivity(intent);
    }

    private void updateUI(FirebaseUser user) {
    }

}