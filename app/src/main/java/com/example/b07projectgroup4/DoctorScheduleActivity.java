package com.example.b07projectgroup4;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class DoctorScheduleActivity extends AppCompatActivity {
    ListView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_schedule);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        //Get Intent
        Intent intent = getIntent();
        //Get doctor from intent
        Doctor doctor = (Doctor)intent.getSerializableExtra("doctor");

        ArrayList<Timeslot> timeslots = new ArrayList<>();
        for (Timeslot ts : doctor.getAvailabilities()){
            timeslots.add(ts);
        }
        listview = findViewById(R.id.doctor_schedule);
        CustomAdapter adapter = new CustomAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        adapter.addAll(timeslots);
        listview.setAdapter(adapter);


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("doctors").child(doctor.getUsername()).child("availabilities");
        ValueEventListener listener = new ValueEventListener() {
            @Override
            //Recreate sessions arraylist when updated.
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                timeslots.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Timeslot timeslot = child.getValue(Timeslot.class);
                    timeslots.add(timeslot);
                }
                if (adapter != null) {
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home: {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}