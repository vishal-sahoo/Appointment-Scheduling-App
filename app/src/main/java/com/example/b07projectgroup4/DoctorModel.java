package com.example.b07projectgroup4;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DoctorModel implements Contract.DoctorModel{

    private Doctor doctor;
    public List<Doctor> doctors = new ArrayList<>();

    public DoctorModel(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("doctors");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot child : snapshot.getChildren()){
                    doctors.add(child.getValue(Doctor.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean isFound(String username) {
        for(Doctor d: doctors){
            if(d.getUsername().equals(username)){
                doctor = d;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean validatePassword(String password) {
        if(doctor != null){
            return doctor.getPassword().equals(password);
        }
        return false;
    }

    @Override
    public Doctor getDoctor() {
        return doctor;
    }
}
