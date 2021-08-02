package com.example.b07projectgroup4;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

public class ConfirmAppointmentActivity extends AppCompatActivity {
    Button bookButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_confirm_appointment);

        //Get Intent
        Intent intent = getIntent();
        //Get session object from intent and display session in TextView
        Session session = (Session) intent.getSerializableExtra("session");
        TextView date = findViewById(R.id.session);
        date.setText(session.toString());

        //Get doctor object from intent and display doctor info in TextView
        Doctor doctor = (Doctor) intent.getSerializableExtra("doctor");
        TextView doctor_name = findViewById(R.id.doctor_name);
        doctor_name.setText("Bob"); //Placeholder name, replace with doctor.getName()

        //Get patient object and display patient name in TextView
        Patient patient = (Patient) intent.getSerializableExtra("patient");
        TextView patient_name = findViewById(R.id.patient_name);
        patient_name.setText("Joe");//Placeholder name, replace with patient.getName()


        //Create button and onClickListener()
        bookButton = findViewById(R.id.confirm_button);
        bookButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //Create Appointment and pass to patient screen
                Appointment appointment = new Appointment(doctor.getUsername(),doctor.getName(),patient.getUsername(),patient.getName(),session);
                //Booking Successful Alert
                AlertDialog.Builder success = new AlertDialog.Builder(ConfirmAppointmentActivity.this);
                success.setTitle("Appointment Booked!");
                success.setMessage("You've successfully booked the appointment.");
                success.setCancelable(false);
                success.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        Intent intent = new Intent(ConfirmAppointmentActivity.this, DoctorsAvailActivity.class);
                        intent.putExtra("appointment",appointment);
                        intent.putExtra("patient",patient);
                        intent.putExtra("doctor",doctor);
                        startActivity(intent);
                    }
                });
                AlertDialog alertDialog = success.create();
                alertDialog.show();

            }

        });
    }
}