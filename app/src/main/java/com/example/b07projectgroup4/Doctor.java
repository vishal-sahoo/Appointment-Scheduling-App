package com.example.b07projectgroup4;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Doctor implements Serializable {
    private String username;
    private String password;
    private String name;
    private String gender;
    private List<String> specializations = new LinkedList<String>();
    private List<String> patients_attended = new LinkedList<String>();
    private List<Session> availabilities = new LinkedList<Session>();
    private List<Appointment> upcoming_appointments = new LinkedList<Appointment>();

    public Doctor(){

    }

    public Doctor(String username, String password, String name, String gender) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.gender = gender;
    }

    @NonNull
    @Override
    public String toString() {
        return "Doctor{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", specializations=" + specializations +
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

    public List<String> getSpecializations() {
        return specializations;
    }

    public void setSpecializations(List<String> specializations) {
        this.specializations = specializations;
    }

    public List<String> getPatients_attended() {
        return patients_attended;
    }

    public void setPatients_attended(List<String> patients_attended) {
        this.patients_attended = patients_attended;
    }

    public List<Session> getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(List<Session> availabilities) {
        this.availabilities = availabilities;
    }

    public List<Appointment> getUpcoming_appointments() {
        return upcoming_appointments;
    }

    public void setUpcoming_appointments(List<Appointment> upcoming_appointments) {
        this.upcoming_appointments = upcoming_appointments;
    }
}
