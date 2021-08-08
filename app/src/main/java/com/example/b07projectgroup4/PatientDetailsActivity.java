package com.example.b07projectgroup4;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class PatientDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);

        Intent intent = getIntent();
        Appointment appointment = (Appointment)intent.getSerializableExtra("appointment");

        List<String> doctors_visited = new LinkedList<>();
        String patientUsername = appointment.getPatient_username();
        ListView listView;
        listView = (ListView) findViewById(R.id.listview);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, doctors_visited);
        listView.setAdapter(arrayAdapter);

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("patients");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                doctors_visited.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    if (postSnapshot.getValue(Patient.class).getUsername().equals(patientUsername)) {
                        TextView usernameTextView = (TextView) findViewById(R.id.username);
                        usernameTextView.setText(postSnapshot.getValue(Patient.class).getUsername());
                        TextView nameTextView = (TextView) findViewById(R.id.name);
                        nameTextView.setText(postSnapshot.getValue(Patient.class).getName());
                        TextView genderTextView = (TextView) findViewById(R.id.gender);
                        genderTextView.setText(postSnapshot.getValue(Patient.class).getGender());
                        TextView dobTextView = (TextView) findViewById(R.id.dob);
                        dobTextView.setText(postSnapshot.getValue(Patient.class).getDob());
                        for (String p:postSnapshot.getValue(Patient.class).getDoctors_visited()) {
                            doctors_visited.add(p);
                        }
                    }
                }
                if(arrayAdapter != null){
                    arrayAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("warning", "loadPost:onCancelled", databaseError.toException());
            }
        };
        myRef.addValueEventListener(postListener);

    }
}
