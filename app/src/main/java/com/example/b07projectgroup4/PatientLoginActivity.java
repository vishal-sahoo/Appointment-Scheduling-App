package com.example.b07projectgroup4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class PatientLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_login);
    }

    public void login(View view){
        EditText username_edit_text = (EditText) findViewById(R.id.PatientLoginUsername);
        EditText password_edit_text = (EditText) findViewById(R.id.PatientLoginPassword);
        String username = username_edit_text.getText().toString();
        String password = password_edit_text.getText().toString();
        if(username.isEmpty()) {
            username_edit_text.setError("Username cannot be empty");
            return;
        }
        if(password.isEmpty()){
            password_edit_text.setError("Password cannot be empty");
            return;
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        Query check = myRef.child("patients").orderByChild("username").equalTo(username);

        check.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    for (DataSnapshot child : snapshot.getChildren()) {
                        Patient patient = child.getValue(Patient.class);
                        if(patient != null){
                            if (patient.getPassword().equals(password)) {
                                Intent intent = new Intent(getApplicationContext(), PatientScreenActivity.class);
                                intent.putExtra("patient", patient);
                                startActivity(intent);
                            } else {
                                //Wrong Password
                                password_edit_text.setError("Incorrect Password");
                            }
                        }else{
                            //Unexpected
                            username_edit_text.setError("Unexpected error");
                        }
                    }
                }else{
                    //Username does not exist
                    username_edit_text.setError("Username not found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("warning", "Failed to read value.", error.toException());
            }
        });
    }
}