package com.example.b07projectgroup4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.executor.DefaultTaskExecutor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

//import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.Query;
//import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;


public class PatientScreenActivity extends AppCompatActivity {
    Patient passed_patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_screen);

        Intent intent = getIntent();
        Patient patient = (Patient)intent.getSerializableExtra("patient");
        passed_patient = patient;

        String name = patient.getName();
        TextView patientName = findViewById(R.id.PatientPointer);
        patientName.append(" " + name);

        ListView displayAppointments;

        List<String> appointments = new ArrayList<>();
        List<Appointment> update = new ArrayList<>();
        ArrayAdapter<String> arrayAdapter;

        displayAppointments = (ListView)findViewById(R.id.display_appointment);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("patients");
        DatabaseReference ref2 = ref.child(patient.getUsername()).child("upcoming_appointments").getRef();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, appointments);
        displayAppointments.setAdapter(arrayAdapter);
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                appointments.clear();
                update.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()){
                    Appointment new_appointment = child.getValue(Appointment.class);
                    Date appointment_time = new_appointment.convertToDate();
                    if(appointment_time.compareTo(new Date()) < 0){
                        addPastAppointment(patient.getUsername(), new_appointment);
                        addDoctorsVisited(patient.getUsername(), child.child("doctor_name").getValue(String.class));
                        String doctor_username = child.child("doctor_username").getValue(String.class);
                        addPatientsAttended(doctor_username, patient.getName());
                        removeFromDoctor(doctor_username, new_appointment);
                        continue;
                    }
                    appointments.add(new_appointment.displayForPatient());
                    update.add(new_appointment);
                }
                if(arrayAdapter != null){
                    arrayAdapter.notifyDataSetChanged();
                }
                ref2.setValue(update);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("warning", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }

    public void addPastAppointment(String username, Appointment appointment){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("patients");
        DatabaseReference ref2 = ref.child(username).child("previous_appointments");
        ref2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ref2.child(String.valueOf(snapshot.getChildrenCount())).setValue(appointment);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addDoctorsVisited(String username, String doctor_name){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("patients");
        DatabaseReference ref2 = ref.child(username).child("doctors_visited");
        ref2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot child: snapshot.getChildren()){
                    String name = child.getValue(String.class);
                    if(name.equals(doctor_name));
                    return;
                }
                ref2.child(String.valueOf(snapshot.getChildrenCount())).setValue(doctor_name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addPatientsAttended(String username, String patient_name){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("doctors");
        DatabaseReference ref2 = ref.child(username).child("patients_attended");
        ref2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot child: snapshot.getChildren()){
                    String name = child.getValue(String.class);
                    if(name.equals(patient_name));
                    return;
                }
                ref2.child(String.valueOf(snapshot.getChildrenCount())).setValue(patient_name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void removeFromDoctor(String username, Appointment appointment){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("doctors");
        DatabaseReference ref2 = ref.child(username).getRef();

        ref2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Doctor doctor = snapshot.getValue(Doctor.class);
                if(doctor == null){
                    return;
                }
                doctor.removeUpcomingAppointment(appointment);
                ref2.child("upcoming_appointments").setValue(doctor.getUpcoming_appointments());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void book(View view){
        Intent intent = new Intent(getApplicationContext(), ListDoctorsActivity.class);
        intent.putExtra("patient", passed_patient);
        startActivity(intent);
    }
    public void logout(View view){
        Intent intent = new Intent(getApplicationContext(), PatientLoginActivity.class);
        startActivity(intent);
    }
}