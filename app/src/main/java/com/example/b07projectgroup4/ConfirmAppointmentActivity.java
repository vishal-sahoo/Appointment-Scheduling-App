package com.example.b07projectgroup4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ConfirmAppointmentActivity extends AppCompatActivity {
    Button bookButton;
    Patient selected_patient;
    Doctor selected_doctor;
    String selected_session;
    Appointment new_appointment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_confirm_appointment);

        //Get Intent
        Intent intent = getIntent();
        //Get session object from intent and display session in TextView
        String session = (String) intent.getStringExtra("session");
        selected_session = session;
        TextView date = findViewById(R.id.session);
        date.setText(session);

        //Get doctor object from intent and display doctor info in TextView
        Doctor doctor = (Doctor) intent.getSerializableExtra("doctor");
        selected_doctor = doctor;
        TextView doctor_name = findViewById(R.id.doctor_name);
        doctor_name.setText(doctor.getName()); //Placeholder name, replace with doctor.getName()

        //Get patient object and display patient name in TextView
        Patient patient = (Patient) intent.getSerializableExtra("patient");
        selected_patient = patient;
        TextView patient_name = findViewById(R.id.patient_name);
        patient_name.setText(patient.getName());//Placeholder name, replace with patient.getName()

        new_appointment = new Appointment(doctor.getUsername(),doctor.getName(),patient.getUsername(),patient.getName(),session.toString());

        //Create button and onClickListener()
//        bookButton = findViewById(R.id.confirm_button);
//        bookButton.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                //Create Appointment and pass to patient screen
//                Appointment appointment = new Appointment(doctor.getUsername(),doctor.getName(),patient.getUsername(),patient.getName(),session.toString());
//                //Booking Successful Alert
//
//
//            }
//        });
    }

    public void addAppointmentToPatient(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("patients");
        DatabaseReference ref = myRef.child(selected_patient.getUsername()).child("upcoming_appointments").getRef();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int index = (int) snapshot.getChildrenCount();
                ref.child(String.valueOf(index)).setValue(new_appointment);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("warning", "Failed to read value.", error.toException());
            }
        });
    }

    public void addAppointmentToDoctor(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("doctors");
        DatabaseReference ref = myRef.child(selected_doctor.getUsername()).child("upcoming_appointments").getRef();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int index = (int) snapshot.getChildrenCount();
                ref.child(String.valueOf(index)).setValue(new_appointment);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("warning", "Failed to read value.", error.toException());
            }
        });
    }

    public void finalBook(View view){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("doctors");
        DatabaseReference ref = myRef.child(selected_doctor.getUsername()).child("availabilities").getRef();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot child: snapshot.getChildren()){
                    String session = child.getValue(String.class);
                    if(session.equals(selected_session)){
                        ref.child(child.getKey()).removeValue();
                        addAppointmentToDoctor();
                        addAppointmentToPatient();
                        //Display successful message
                        alert("passed");
                        return;
                    }
                }
                alert("failed");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("warning", "Failed to read value.", error.toException());
            }
        });
    }

    public void alert(String status){
        AlertDialog.Builder alert = new AlertDialog.Builder(ConfirmAppointmentActivity.this);
        if(status.equals("failed")){
            alert.setTitle("Booking Failed!"); //Session is no longer available
            alert.setMessage("Selected Session is no longer available");
            alert.setCancelable(false);
            alert.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                    Intent intent = new Intent(ConfirmAppointmentActivity.this, PatientScreenActivity.class);
                    //intent.putExtra("appointment",appointment);
                    intent.putExtra("patient",selected_patient);
                    //intent.putExtra("doctor",doctor);
                    startActivity(intent);
                }
            });
        }else if(status.equals("passed")){
            alert.setTitle("Appointment Booked!");
            alert.setMessage("You've successfully booked the appointment.");
            alert.setCancelable(false);
            alert.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                    Intent intent = new Intent(ConfirmAppointmentActivity.this, PatientScreenActivity.class);
                    //intent.putExtra("appointment",appointment);
                    intent.putExtra("patient",selected_patient);
                    //intent.putExtra("doctor",doctor);
                    startActivity(intent);
                }
            });
        }
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }
}