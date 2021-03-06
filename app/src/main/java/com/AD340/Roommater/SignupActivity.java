package com.AD340.Roommater;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipError;


public class SignupActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private static final String TAG = "EmailPassword";

    //private EmailPasswordActivity user;
    private EditText nameField;
    private EditText emailField;
    private EditText ageField;
    private EditText zipField;
    private EditText passwordField;
    private TextView dobTextView;
    private FirebaseFirestore db;

    private int dobYear = 0;
    private int dobMonth = 0;
    private int dobDay = 0;
    private int calculatedYear;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    //TO DO: add a progress bar for registration
    ProgressBar progressBar;
    private AlertDialog alert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //onStart(); //checks if the user is currently sign in
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        nameField = findViewById(R.id.names);
        emailField = findViewById(R.id.email);
        ageField = findViewById(R.id.age);
        zipField = findViewById(R.id.zip);
        dobTextView = findViewById(R.id.dateOfBirth);
        passwordField = findViewById(R.id.password);

        progressBar = new ProgressBar(this);
        db = FirebaseFirestore.getInstance();

    }
    public void onSubmit(View view) {

        String name = nameField.getText().toString();
        String email = emailField.getText().toString();
        String age = ""+calculatedYear;
        String zip = zipField.getText().toString();
        String password = passwordField.getText().toString();

        //check for null
        if (name.equals("") || email.equals("") || age.equals("") || zip.equals("")||
                password.equals("") ){
            Toast.makeText(getApplicationContext(),getString(R.string.please_enter_a_valid_content),Toast.LENGTH_LONG).show();
            return;
        }
        //validate email
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(getApplicationContext(),getString(R.string.please_enter_a_valid_email),Toast.LENGTH_LONG).show();
            return;
        }

        //validate age
        if(!checkAge()){
            Toast.makeText(getApplicationContext(), getString(R.string.eighteen_error), Toast.LENGTH_LONG).show();
            return;
        }


        LocalDate currentDate = LocalDate.now();
        LocalDate dateOfBirth = LocalDate.of(dobYear, dobMonth, dobDay);
        int years = Period.between(dateOfBirth, currentDate).getYears();

        if (years < 18) {
            Toast.makeText(getApplicationContext(), getString(R.string.eighteen_error),
                    Toast.LENGTH_LONG).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            Map<String, Object> newUser = new HashMap<>();
                            newUser.put("name", name);
                            newUser.put("email", email);
                            newUser.put("age", age);
                            newUser.put("zip", zip);
                            newUser.put("password", password);
                            System.out.println(user.getUid());
                            db.collection("users").add(user.getUid());
                            db.collection("users").document(user.getUid())
                                    .set(newUser)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "DocumentSnapshot successfully written!");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG, "Error writing document", e);
                                        }
                                    });



                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignupActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });


    }

    private boolean checkAge(){
        int todayDate = LocalDate.now().getYear();
        int instantDOB =   LocalDate.of(dobYear,dobMonth,dobDay).getYear();
        calculatedYear = todayDate - instantDOB;
        return calculatedYear >= 18;
    }

    public void onDobClick(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constants.AGE_KEY, ageField.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey(Constants.AGE_KEY)) {
            ageField.setText(savedInstanceState.getString(Constants.AGE_KEY));
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        nameField.setText("");
        emailField.setText("");
        ageField.setText("");
        zipField.setText("");
        passwordField.setText("");
        dobYear = 0;
        dobDay = 0;
        dobMonth = 0;
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {

        month = month + 1;
        dobYear = year;
        dobMonth = month;
        dobDay = day;
        dobTextView.setText(month + getString(R.string.text_slash_symbol)+ day + getString(R.string.text_slash_symbol)+ year);
    }

    public static class DatePickerFragment extends DialogFragment  {
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstances){
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener) getActivity(),year, month,day);
        }

    }

    private void updateUI(FirebaseUser user) {

    }
}
