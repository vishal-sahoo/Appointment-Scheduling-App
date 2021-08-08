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
        Doctor doctor = (Doctor)intent.getSerializableExtra("doctor");
        passed_doctor = doctor;

        String name = doctor.getName();
        TextView doctorName = findViewById(R.id.doctorPointer);
        doctorName.append(" " + name);

        ListView display_appointments;

        List<String> appointments = new ArrayList<>();

        ArrayAdapter<String> arrayAdapter;

        display_appointments = (ListView)findViewById(R.id.display_appointment);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("doctors");
        DatabaseReference ref2 = ref.child(doctor.getUsername()).child("upcoming_appointments").getRef();
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, appointments);
        display_appointments.setAdapter(arrayAdapter);

        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                appointments.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()){
                    Appointment new_appointment = child.getValue(Appointment.class);
                    if(new_appointment == null){
                        return;
                    }
                    appointments.add(new_appointment.displayForPatient());
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

        display_appointments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                Intent intent1 = new Intent(DoctorScreenActivity.this, PatientDetailsActivity.class);
                //Appointment appointment = (Appointment) appointments.get(position);
                //intent.putExtra("appointment", selectedAppointment);
                startActivity(intent1);
            }



        });


    }

    public void view_schedule(View view){
        Intent intent = new Intent(getApplicationContext(), DoctorScheduleActivity.class);
        intent.putExtra("doctor", passed_doctor);
        startActivity(intent);
    }
}