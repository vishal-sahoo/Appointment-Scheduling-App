package com.example.b07projectgroup4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class PatientLoginActivity extends AppCompatActivity implements Contract.PatientView{

    private PatientPresenter presenter;
    private EditText username_edit_text;
    private EditText password_edit_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_login);
        presenter = new PatientPresenter(new PatientModel(), this);
        username_edit_text = (EditText) findViewById(R.id.PatientLoginUsername);
        password_edit_text = (EditText) findViewById(R.id.PatientLoginPassword);
    }

    /*public void login(View view){
        EditText username_edit_text = (EditText) findViewById(R.id.PatientLoginUsername);
        EditText password_edit_text = (EditText) findViewById(R.id.PatientLoginPassword);
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
    public void startNextActivity(Patient patient) {
        Intent intent = new Intent(getApplicationContext(), PatientScreenActivity.class);
        intent.putExtra("patient", patient);
        startActivity(intent);
    }

    public void signup(View view){
        Intent intent = new Intent(this, PatientSignupActivity.class);
        startActivity(intent);
    }

    public void switchToDoctor(View view){
        Intent intent = new Intent(this, DoctorLoginActivity.class);
        startActivity(intent);
    }
}