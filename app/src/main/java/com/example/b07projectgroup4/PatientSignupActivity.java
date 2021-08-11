package com.example.b07projectgroup4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatientSignupActivity extends AppCompatActivity {

    private EditText username_edit_text;
    private EditText name_edit_text;
    private EditText dob_edit_text;
    private Spinner gender_spinner;
    private EditText password_edit_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_signup);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void signup(View view){
        username_edit_text = (EditText) findViewById(R.id.PatientSignupUsername);
        name_edit_text = (EditText) findViewById(R.id.PatientSignupName);
        dob_edit_text = (EditText) findViewById(R.id.PatientSignupDOB);
        gender_spinner = (Spinner) findViewById(R.id.PatientSignupGender);
        password_edit_text = (EditText) findViewById(R.id.PatientSignupPassword);

        String username = username_edit_text.getText().toString();
        String name = name_edit_text.getText().toString();
        String dob = dob_edit_text.getText().toString();
        String gender = String.valueOf(gender_spinner.getSelectedItem());
        String password = password_edit_text.getText().toString();

        Pattern valid_username = Pattern.compile("\\w+");
        Matcher username_matcher = valid_username.matcher(username);

        if(!username_matcher.matches()) {
            username_edit_text.setError("Only Word Characters Allowed");
            return;
        }

        if(name.isEmpty()){
            name_edit_text.setError("Name Cannot Be Empty");
            return;
        }

        if(dob.isEmpty()){
            dob_edit_text.setError("DOB Cannot Be Empty");
            return;
        }

        if(password.isEmpty()){
            password_edit_text.setError("Password Cannot Be Empty");
            return;
        }

        Patient patient = new Patient(username, password, name, gender, dob);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("patients");

        Query check = myRef.orderByChild("username").equalTo(username);
        check.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()){
                    myRef.child(username).setValue(patient);
                    alert();
                }else{
                    username_edit_text.setError("Username already exists");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void alert(){
        AlertDialog.Builder alert = new AlertDialog.Builder(PatientSignupActivity.this);
        alert.setTitle("Successful!"); //Session is no longer available
        alert.setMessage("Successfully Registered, Please Login.");
        alert.setCancelable(false);
        alert.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                username_edit_text.setText("");
                name_edit_text.setText("");
                dob_edit_text.setText("");
                password_edit_text.setText("");
                gotoLogin();
            }
        });
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }

    private void gotoLogin() {
        Intent intent = new Intent(this, PatientLoginActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home: {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}