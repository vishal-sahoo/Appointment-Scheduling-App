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

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.LinkedList;

public class PatientLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_login);
    }

//    public void create2(View view){
//        Session session = new Session (LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(),LocalDateTime.now().getDayOfMonth(),17,0), Duration.ofHours(2));
//        Appointment appt = new Appointment("doctor_username", "doctor_name", "vasu", "vasu", session.toString());
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("appointments");
//        myRef.child("appt1").setValue(appt);
//
//        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot child: snapshot.getChildren()){
//                    Appointment app = child.getValue(Appointment.class);
//                    EditText username_edit_text = (EditText) findViewById(R.id.PatientLoginUsername);
//                    username_edit_text.setText("Working " + app.getSession());
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

public Doctor newDoctor(String username, String password, String name, String gender){

    LinkedList<Appointment> upcoming = new LinkedList<Appointment>();
    LinkedList<String> attended = new LinkedList<String>();
    LinkedList<String> spec = new LinkedList<String>();
    //LinkedList<String> avail = new LinkedList<String>();
    //9 to 5, 1 hour sessions
    Session session = new Session (LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(),LocalDateTime.now().getDayOfMonth() + 1,17,0), Duration.ofHours(1));
    Appointment appt = new Appointment(username, name, "vasu", "vasu", session.toString());
    upcoming.add(appt);
    //avail.add(session);
    spec.add("orthopedic");
    attended.add("vasu");

    Doctor d = new Doctor(username, password, name, gender, spec, attended, upcoming); //
    d.getAvailabilities().remove(session.toString());
    return d;
}

    public Patient newPatient(String username, String password, String name, String gender){

        LinkedList<Appointment> previous = new LinkedList<Appointment>(); //
        LinkedList<Appointment> upcoming = new LinkedList<Appointment>();
        LinkedList<String> visited = new LinkedList<String>();
        String c = "19/12/2000";
        Session session = new Session (LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(),LocalDateTime.now().getDayOfMonth() + 1,17,0), Duration.ofHours(1));
        Session session2 = new Session (LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(),LocalDateTime.now().getDayOfMonth(),17,0), Duration.ofHours(1));
        Appointment appt = new Appointment("vishal", "vishal", username, name, session.toString());
        Appointment appt2 = new Appointment("vishal", "vishal", username, name, session2.toString());;
        previous.add(appt2); upcoming.add(appt); visited.add("vishal");
        Patient p = new Patient(username, password, name, gender, c, previous, upcoming, visited);
        return p;
    }

    public void create(View view){
        //Patient p = newPatient("vasu", "password", "vasu", "male");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("doctors");
        //myRef.child("vasu").setValue(p);
        Doctor d = newDoctor("vishal", "password", "vishal", "male");
        myRef.child("vishal").setValue(d);
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