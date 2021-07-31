package com.example.b07projectgroup4;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Appointment implements Serializable {
    private String doctor_username;
    private String doctor_name;
    private String patient_username;
    private String patient_name;
    private Session session;

    public Appointment(){

    }

    public Appointment(String doctor_username, String doctor_name, String patient_username, String patient_name, Session session) {
        this.doctor_username = doctor_username;
        this.doctor_name = doctor_name;
        this.patient_username = patient_username;
        this.patient_name = patient_name;
        this.session = session;
    }

    @NonNull
    @Override
    public String toString() {
        return "Appointment{" +
                "doctor_name='" + doctor_name + '\'' +
                ", patient_name='" + patient_name + '\'' +
                ", session=" + session +
                '}';
    }

    public String getDoctor_username() {
        return doctor_username;
    }

    public void setDoctor_username(String doctor_username) {
        this.doctor_username = doctor_username;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getPatient_username() {
        return patient_username;
    }

    public void setPatient_username(String patient_username) {
        this.patient_username = patient_username;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
