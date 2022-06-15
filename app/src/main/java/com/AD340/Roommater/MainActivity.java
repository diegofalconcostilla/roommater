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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDate;
import java.time.Period;

public class MainActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private EditText nameField;
    private EditText usernameField;
    private EditText loginEmailField;
    private EditText loginPasswordField;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private AlertDialog alert;
    private TextView dobTextView;
    private static final String TAG = "EmailPassword";
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginEmailField = findViewById(R.id.login_email);
        loginPasswordField = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login);
        db = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        loginButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                onLogin(view);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    public void onLogin(View view){
        String email = loginEmailField.getText().toString();
        String password = loginPasswordField.getText().toString();
        if (email.equals("") || password.equals("") ){
            Toast.makeText(getApplicationContext(),getString(R.string.please_enter_a_valid_content),Toast.LENGTH_LONG).show();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(getApplicationContext(),getString(R.string.please_enter_a_valid_email),Toast.LENGTH_LONG).show();
            return;
        }
        System.out.println(mUser);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    Log.d(TAG, "signInWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                    System.out.println(user.getUid());
                    DocumentReference userData = db.collection("users").document(user.getUid());
                    System.out.println(userData);
                    userData.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                    String name = (String) document.get("name");
                                    String age = (String) document.get("age");

                                } else {
                                    Log.d(TAG, "No such document");
                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                            }
                        }
                    });



                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                    Toast.makeText(MainActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
                Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                startActivity(intent);
            }
        });

    }

    public void onSignUp(View view){
        Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loginEmailField.setText("");
        loginPasswordField.setText("");
        dobTextView.setText("");
    }
    private void updateUI(FirebaseUser user) {
    }

}