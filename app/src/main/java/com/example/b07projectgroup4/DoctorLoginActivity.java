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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DoctorLoginActivity extends AppCompatActivity implements Contract.DoctorView {

    private DoctorPresenter presenter;
    private EditText username_edit_text;
    private EditText password_edit_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login);
        presenter = new DoctorPresenter(new DoctorModel(), this);
        username_edit_text = (EditText) findViewById(R.id.DoctorLoginUsername);
        password_edit_text = (EditText) findViewById(R.id.DoctorLoginPassword);
    }

    /*public void login(View view){
        EditText username_edit_text = (EditText) findViewById(R.id.DoctorLoginUsername);
        EditText password_edit_text = (EditText) findViewById(R.id.DoctorLoginPassword);
        String username = username_edit_text.getText().toString();
        String password = password_edit_text.getText().toString();

        Pattern valid_username = Pattern.compile("\\w+");
        Matcher username_matcher = valid_username.matcher(username);

        if(!username_matcher.matches()) {
            username_edit_text.setError("Invalid Username Entered");
            return;
        }
        if(password.isEmpty()){
            password_edit_text.setError("Password Cannot Be Empty");
            return;
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        Query check = myRef.child("doctors").orderByChild("username").equalTo(username);

        check.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    for (DataSnapshot child : snapshot.getChildren()) {
                        Doctor doctor = child.getValue(Doctor.class);
                        if(doctor != null){
                            if (doctor.getPassword().equals(password)) {
                                Intent intent = new Intent(getApplicationContext(), DoctorScreenActivity.class);
                                intent.putExtra("doctor", doctor);
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
                    username_edit_text.setError("Username Not Found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("warning", "Failed to read value.", error.toException());
            }
        });
    }*/

    public void login(View view){
        presenter.login();
    }

    @Override
    public String getUsername() {
        return username_edit_text.getText().toString();
    }

    @Override
    public String getPassword() {
        return password_edit_text.getText().toString();
    }

    @Override
    public void setUsernameError(String error) {
        username_edit_text.setError(error);
    }

    @Override
    public void setPasswordError(String error) {
        password_edit_text.setError(error);
    }

    @Override
    public void startNextActivity(Doctor doctor) {
        Intent intent = new Intent(getApplicationContext(), DoctorScreenActivity.class);
        intent.putExtra("doctor", doctor);
        startActivity(intent);
    }

    public void signup(View view){
        Intent intent = new Intent(this, DoctorSignupActivity.class);
        startActivity(intent);
    }
}