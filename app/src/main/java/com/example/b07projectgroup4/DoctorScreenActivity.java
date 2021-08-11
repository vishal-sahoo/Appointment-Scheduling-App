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
import android.widget.AdapterView;

//import com.google.firebase.database.Query;
//import java.lang.reflect.Array;
import java.util.*;

public class DoctorScreenActivity extends AppCompatActivity {
    Doctor passed_doctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_screen);

        Intent intent = getIntent();
        passed_doctor = (Doctor)intent.getSerializableExtra("doctor");


        String name = passed_doctor.getName();
        TextView doctorName = findViewById(R.id.doctorPointer);
        doctorName.append(" " + name);

        ListView display_appointments;

        //List<String> appointments = new ArrayList<>();
        List<Appointment> appointments = new ArrayList<>();

        //ArrayAdapter<String> arrayAdapter;
        ArrayAdapter<Appointment> arrayAdapter;

        display_appointments = (ListView)findViewById(R.id.display_appointment);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("doctors");
        DatabaseReference ref2 = ref.child(passed_doctor.getUsername()).child("upcoming_appointments").getRef();
        //arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, appointments);
        arrayAdapter = new ArrayAdapter<Appointment>(this, android.R.layout.simple_list_item_1, appointments);
        display_appointments.setAdapter(arrayAdapter);

        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                appointments.clear();
                List<String> patient_names = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()){
                    Appointment new_appointment = child.getValue(Appointment.class);
                    if(new_appointment == null){
                        return;
                    }
                    Date appointment_time = new_appointment.convertToDate();
                    if(appointment_time.compareTo(new Date()) < 0){
                        String patient_name = child.child("patient_name").getValue(String.class);
                        if(patient_names.contains(patient_name)){
                            patient_names.add(patient_name);
                        }
                        continue;
                    }
                    appointments.add(new_appointment);
                }
                if(arrayAdapter != null){
                    arrayAdapter.notifyDataSetChanged();
                }
                ref2.setValue(appointments);
                addPatientsAttended(passed_doctor.getUsername(), patient_names);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("warning", "loadPost:onCancelled", databaseError.toException());
            }
        });

        display_appointments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                Intent intent1 = new Intent(view.getContext(), PatientDetailsActivity.class);
                Appointment selectedAppointment = appointments.get(position);
                intent1.putExtra("appointment", selectedAppointment);
                startActivity(intent1);
            }


        });


    }

    public void addPatientsAttended(String username, List<String> patient_names){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("doctors");
        DatabaseReference ref2 = ref.child(username).child("patients_attended");
        ref2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot child: snapshot.getChildren()){
                    String name = child.getValue(String.class);
                    if(patient_names.contains(name)){
                        patient_names.remove(name);
                    }
                }
                long index = snapshot.getChildrenCount();
                for(String patient_name: patient_names){
                    ref2.child(String.valueOf(index)).setValue(patient_name);
                    index++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void view_schedule(View view){
        Intent intent = new Intent(DoctorScreenActivity.this, DoctorScheduleActivity.class);
        intent.putExtra("doctor", passed_doctor);
        startActivity(intent);
    }
    public void logout(View view){
        Intent intent = new Intent(getApplicationContext(), PatientLoginActivity.class);
        startActivity(intent);
    }
}