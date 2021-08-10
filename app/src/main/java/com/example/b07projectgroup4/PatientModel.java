package com.example.b07projectgroup4;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PatientModel implements Contract.PatientModel{

    private Patient patient;
    public List<Patient> patients = new ArrayList<>();

    public PatientModel(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("patients");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot child : snapshot.getChildren()){
                    patients.add(child.getValue(Patient.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean isFound(String username){
        for(Patient p: patients){
            if(p.getUsername().equals(username)){
                patient = p;
                return true;
            }
        }
        return false;
    }

    @Override
    public Patient getPatient(){ return patient; }

    @Override
    public boolean validatePassword(String password) {
        if(patient != null){
            return patient.getPassword().equals(password);
        }
        return false;
    }
}
