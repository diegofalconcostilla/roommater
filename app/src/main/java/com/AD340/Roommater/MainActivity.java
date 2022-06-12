package com.AD340.Roommater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

    }

    public void onLogin(View view){
        String email = loginEmailField.getText().toString();
        String password = loginPasswordField.getText().toString();

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    //alert.dismiss();
                    //TO DO send user to other activity
                    Toast.makeText(MainActivity.this,"Logged in successfully.", Toast.LENGTH_SHORT);
                }else{
                    //alert.dismiss();
                    Toast.makeText(MainActivity.this,""+task.getException(), Toast.LENGTH_SHORT);

                }
            }
        });
        Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
        startActivity(intent);
    }

    public void onSignUp(View view){
        Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
        startActivity(intent);
    }
}