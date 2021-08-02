package com.example.b07projectgroup4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.Intent;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class DoctorsAvailActivity extends AppCompatActivity {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_avail);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        //Get Intent
        Intent intent = getIntent();
        //Get doctor and patient from previous intent
        Patient patient = (Patient)intent.getSerializableExtra("patient");
        Doctor doctor = (Doctor)intent.getSerializableExtra("doctor");
        //Create listview
        listView = findViewById(R.id.listview);
        //Create ArrayList of sessions
        ArrayList<String> sessions = new ArrayList<>();
        //Append doctor availabilities to sessions
//        for (String s : doctor.getAvailabilities()){
//            sessions.add(s);
//        }
        //Create adapter
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,sessions);
        listView.setAdapter(adapter);
        //Create OnItemClick Listener to pass session doctor and patient to the confirmation screen
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(DoctorsAvailActivity.this, ConfirmAppointmentActivity.class);
                intent.putExtra("session",sessions.get(i));
                intent.putExtra("doctor",doctor);
                intent.putExtra("patient",patient);
                startActivity(intent);
            }
        });

        //Updating Data when appointments are booked
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("doctors").child(doctor.getUsername()).child("availabilities");
        ValueEventListener listener= new ValueEventListener() {
            @Override
            //Recreate sessions arraylist when updated.
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sessions.clear();
                for(DataSnapshot child: dataSnapshot.getChildren()){
                    String session = child.getValue(String.class);
                    sessions.add(session);
                }
                if(adapter != null){
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("warning", "loadPost:onCancelled", databaseError.toException());
            }
        };
        ref.addValueEventListener(listener);
    }
}