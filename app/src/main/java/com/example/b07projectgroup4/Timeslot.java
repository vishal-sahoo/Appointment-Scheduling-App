package com.example.b07projectgroup4;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Timeslot implements Serializable {
    private String time;
    private String is_available = "true";
    private String patient_name = "Available";

    public Timeslot(){

    }

    public Timeslot(String time) {
        this.time = time;
    }

    public Date convertToDate(){
        //Can return null, please check
        String string = time.split(" - ")[0];
        SimpleDateFormat format = new SimpleDateFormat("E, dd MMM yyyy hh:00 a");
        Date date = null;
        try {
            date = format.parse(string);
        } catch (ParseException e) {
            return null;
        }
        return date;
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
