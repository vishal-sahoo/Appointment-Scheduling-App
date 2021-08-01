package com.example.b07projectgroup4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.*;


public class ListDoctorsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_doctors);

        Intent intent = getIntent();
        Patient patient = (Patient)intent.getSerializableExtra("patient");

        ListView listView;
        listView = (ListView) findViewById(R.id.listview);

        List<Doctor> doctors = new ArrayList<Doctor>();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, doctors);
        listView.setAdapter(arrayAdapter);

        DatabaseReference refRead = FirebaseDatabase.getInstance().getReference("doctors");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    doctors.add(postSnapshot.getValue(Doctor.class));
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
        refRead.addValueEventListener(postListener);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                        Intent intent = new Intent(view.getContext(), DoctorsAvailActivity.class);
                        Doctor doctor = (Doctor)doctors.get(i);
                        intent.putExtra("doctor", doctor);
                        intent.putExtra("patient", patient);
                        startActivity(intent);
                    }
                }
        );
    }
}
