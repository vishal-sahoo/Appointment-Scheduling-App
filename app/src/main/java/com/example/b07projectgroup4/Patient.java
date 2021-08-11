package com.example.b07projectgroup4;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Patient implements Serializable{
    private String username;
    private String password;
    private String name;
    private String gender;
    private String dob;
    private List<Appointment> previous_appointments = new LinkedList<Appointment>();
    private List<Appointment> upcoming_appointments = new LinkedList<Appointment>();
    private List<String> doctors_visited = new LinkedList<String>();

    public Patient() {

    }

    public Patient(String username, String password, String name, String gender, String dob) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.dob = dob;
    }

    public boolean addUpcomingAppointment(Appointment new_appointment){
        Date new_date = new_appointment.convertToDate();
        if(new_date == null){
            return false;
        }
        for(Appointment appointment: upcoming_appointments){
            Date date = appointment.convertToDate();
            if(date == null){
                return false;
            }
            if(new_date.compareTo(date) < 0){
                upcoming_appointments.add(upcoming_appointments.indexOf(appointment), new_appointment);
                return true;
            }
        }
        upcoming_appointments.add(new_appointment);
        return true;
    }

    public void removeUpcomingAppointment(Appointment appointment){
        upcoming_appointments.remove(upcoming_appointments.indexOf(appointment));
    }
//    public boolean addPastAppointment(Appointment old_appointment){
//        Date new_date = old_appointment.convertToDate();
//        if(new_date == null){
//            return false;
//        }
//        //Adding appointment to TOP of list (i.e first item, since it's most recent one that's passed)
//        previous_appointments.add(0,old_appointment);
//        return true;
//    }
//    public boolean addDoctorsVisited(String doctor){
//        if(doctor == null){
//            return false;
//        }
//        doctors_visited.add(doctor);
//        return true;
//    }

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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
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

    public List<String> getDoctors_visited() {
        return doctors_visited;
    }

    public void setDoctors_visited(List<String> doctors_visited) {
        this.doctors_visited = doctors_visited;
    }
}
