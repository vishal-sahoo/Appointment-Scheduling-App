package com.example.b07projectgroup4;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class Patient implements Serializable {
    private String username;
    private String password;
    private String name;
    private String gender;
    private Calendar dob;
    private List<Appointment> previous_appointments = new LinkedList<Appointment>();
    private List<Appointment> upcoming_appointments = new LinkedList<Appointment>();
    private List<Doctor> doctors_visited = new LinkedList<Doctor>();

    public Patient() {

    }

    public Patient(String username, String password, String name, String gender, Calendar dob) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.dob = dob;
    }

    @NonNull
    @Override
    public String toString() {
        return "Patient{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", dob=" + dob +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Calendar getDob() {
        return dob;
    }

    public void setDob(Calendar dob) {
        this.dob = dob;
    }

    public List<Appointment> getPrevious_appointments() {
        return previous_appointments;
    }

    public void setPrevious_appointments(List<Appointment> previous_appointments) {
        this.previous_appointments = previous_appointments;
    }

    public List<Appointment> getUpcoming_appointments() {
        return upcoming_appointments;
    }

    public void setUpcoming_appointments(List<Appointment> upcoming_appointments) {
        this.upcoming_appointments = upcoming_appointments;
    }

    public List<Doctor> getDoctors_visited() {
        return doctors_visited;
    }

    public void setDoctors_visited(List<Doctor> doctors_visited) {
        this.doctors_visited = doctors_visited;
    }
}
