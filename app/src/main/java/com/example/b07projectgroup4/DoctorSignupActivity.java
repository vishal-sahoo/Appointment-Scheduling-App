package com.example.b07projectgroup4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DoctorSignupActivity extends AppCompatActivity {

    private EditText username_edit_text;
    private EditText name_edit_text;
    private Spinner gender_spinner;
    private EditText spec_edit_text;
    private EditText password_edit_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_signup);
    }

    public void signup(View view){
        username_edit_text = (EditText) findViewById(R.id.DoctorSignupUsername);
        name_edit_text = (EditText) findViewById(R.id.DoctorSignupName);
        gender_spinner = (Spinner) findViewById(R.id.DoctorSignupGender);
        spec_edit_text = (EditText) findViewById(R.id.DoctorSignupSpec);
        password_edit_text = (EditText) findViewById(R.id.DoctorSignupPassword);

        String username = username_edit_text.getText().toString();
        String name = name_edit_text.getText().toString();
        String gender = String.valueOf(gender_spinner.getSelectedItem());
        String spec = spec_edit_text.getText().toString();
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

        if(spec.isEmpty()){
            spec_edit_text.setError("Specializations Cannot Be Empty");
            return;
        }

        if(password.isEmpty()){
            password_edit_text.setError("Password Cannot Be Empty");
            return;
        }

        List<String> specializations = new ArrayList<String>();
        for(String s : spec.split(",")){
            specializations.add(s);
        }

        Doctor doctor = new Doctor(username,password,name,gender,specializations);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("doctors");

        Query check = myRef.orderByChild("username").equalTo(username);
        check.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()){
                    myRef.child(username).setValue(doctor);
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
        AlertDialog.Builder alert = new AlertDialog.Builder(DoctorSignupActivity.this);
        alert.setTitle("Successful!"); //Session is no longer available
        alert.setMessage("Successfully Registered, Please Login.");
        alert.setCancelable(false);
        alert.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                username_edit_text.setText("");
                name_edit_text.setText("");
                spec_edit_text.setText("");
                password_edit_text.setText("");
            }
        });
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }
}