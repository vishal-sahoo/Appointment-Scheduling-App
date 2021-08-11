package com.example.b07projectgroup4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        presenter = new DoctorPresenter(new DoctorModel(), this);
        username_edit_text = (EditText) findViewById(R.id.DoctorLoginUsername);
        password_edit_text = (EditText) findViewById(R.id.DoctorLoginPassword);
    }

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home: {
                finish();
                Intent intent = new Intent(this, PatientLoginActivity.class);
                startActivity(intent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}