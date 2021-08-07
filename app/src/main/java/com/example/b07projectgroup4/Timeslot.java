package com.example.b07projectgroup4;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Timeslot implements Serializable {
    private String time;
    private String is_available = "true";
    private String patient_name = "Available";

    public Timeslot(){

    }

    public Timeslot(String time) {
        this.time = time;
    }

    @NonNull
    @Override
    public String toString(){
        return time;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIs_available() {
        return is_available;
    }

    public void setIs_available(String is_available) {
        this.is_available = is_available;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }
}
