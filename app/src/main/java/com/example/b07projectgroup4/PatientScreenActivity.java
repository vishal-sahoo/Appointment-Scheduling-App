package com.example.b07projectgroup4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
        patientName.append(name);

        ListView displayAppointments;

        ArrayAdapter<Appointment> arrayAdapter;

        displayAppointments = (ListView)findViewById(R.id.display_appointment);

        List<Appointment> appointments = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("patients");

        DatabaseReference ref2 = ref.child(patient.getUsername()).child("upcoming_appointments");

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, appointments);
        displayAppointments.setAdapter(arrayAdapter);

        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                appointments.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()){
                    Appointment newAppointment = child.getValue(Appointment.class);
                    appointments.add(newAppointment);
                    //Log.i("info", appointments.toString());
                }
                if(arrayAdapter != null){
                    arrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("warning", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }

    public void book(View view){
        Intent intent1 = new Intent(getApplicationContext(), ListDoctorsActivity.class);
        intent1.putExtra("patient", passed_patient);
        startActivity(intent1);
    }
}