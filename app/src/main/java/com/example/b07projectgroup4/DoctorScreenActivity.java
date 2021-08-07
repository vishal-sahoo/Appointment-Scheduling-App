package com.example.b07projectgroup4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DoctorScreenActivity extends AppCompatActivity {

    Doctor doctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_screen);


        Intent intent = getIntent();
        doctor = (Doctor) intent.getSerializableExtra("doctor");



        String name = doctor.getName();
        TextView doctorName = findViewById(R.id.doctorPointer);
        doctorName.append(name);
        ListView displayAppointments;
        ArrayAdapter<Appointment> arrayAdapter;

        displayAppointments = findViewById(R.id.display_appointment);

        List<Appointment> appointments = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("doctors");

        DatabaseReference ref2 = ref.child(doctor.getUsername()).child("upcoming_appointments");

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, appointments);
        displayAppointments.setAdapter(arrayAdapter);

        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                appointments.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()){
                    Appointment newAppointment = child.getValue(Appointment.class);
                    appointments.add(newAppointment);
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



            displayAppointments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                Intent intent1 = new Intent(DoctorScreenActivity.this, PatientDetailsActivity.class);
                Appointment selectedAppointment = (Appointment)appointments.get(position);
                intent.putExtra("appointment", selectedAppointment);
                startActivity(intent1);
            }

        });

    }


        public void view_schedule(View view){
            Intent intent2 = new Intent(getApplicationContext(), DoctorScheduleActivity.class);
            intent2.putExtra("doctor", doctor);
            startActivity(intent2);
        }
}

