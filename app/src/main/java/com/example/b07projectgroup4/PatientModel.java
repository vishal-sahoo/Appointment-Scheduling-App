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

public class PatientModel implements Contract.Model{

    private Patient patient;
    private boolean is_found;
    private boolean is_called;

    @Override
    public void find(String username) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        Query check = myRef.child("patients").orderByChild("username").equalTo(username);

        check.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    for (DataSnapshot child : snapshot.getChildren()) {
                        patient = child.getValue(Patient.class);
                        setIs_Found(true);
                    }
                }else{
                    setIs_Found(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("warning", "Failed to read value.", error.toException());
            }
        });
    }

    public void setIs_Called(){
        is_called = true;
    }
    public boolean getIs_Called(){
        return is_called;
    }

    @Override
    public boolean getIs_Found() {
        return is_found;
    }

    public void setIs_Found(boolean bool) {
        is_found = bool;
        setIs_Called();
    }

    @Override
    public Helper getField(){ return patient; }

    @Override
    public boolean validatePassword(String password) {
        if(patient != null){
            return patient.getPassword().equals(password);
        }
        return false;
    }
}
