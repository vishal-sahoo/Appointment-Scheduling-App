package com.example.b07projectgroup4;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Doctor implements Serializable, Helper{
    private String username;
    private String password;
    private String name;
    private String gender;
    private List<String> specializations = new LinkedList<String>();
    private List<String> patients_attended = new LinkedList<String>();
    private List<Timeslot> availabilities = new LinkedList<Timeslot>();
    private List<Appointment> upcoming_appointments = new LinkedList<Appointment>();

    public Doctor(){

    }

    public Doctor(String username, String password, String name, String gender, List<String> specializations) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.specializations = specializations;

        //set default availabilites
        for(int d = 0; d < 7; d++){
            for (int h = 9; h < 17; h++){
                if(h==12 || h==13){
                    continue;
                }
                Session session = new Session (LocalDateTime.of(LocalDateTime.now().getYear(),
                        LocalDateTime.now().getMonth(),
                        LocalDateTime.now().getDayOfMonth()+d, h,0),
                        Duration.ofHours(1));
                String time = session.toString();
                availabilities.add(new Timeslot(time));
            }
        }
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

    /*public Doctor(String username, String password, String name, String gender, List<String> specializations, List<String> patients_attended, List<String> upcoming_appointments) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.gender = gender;
        for (int d = 0; d< 7; d++){
            for (int h = 9; h <12; h++){
                Session session = new Session (LocalDateTime.of(LocalDateTime.now().getYear(),
                        LocalDateTime.now().getMonth(),
                        LocalDateTime.now().getDayOfMonth()+d, h,0),
                        Duration.ofHours(1));
                availabilities.add(session.toString());
            }
            for (int h = 14; h <17; h++){
                Session session = new Session (LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(),LocalDateTime.now().getDayOfMonth()+d,h,0), Duration.ofHours(1));
                availabilities.add(session.toString());
            }
        }
        this.specializations = specializations;
        this.patients_attended = patients_attended;
        this.upcoming_appointments = upcoming_appointments;
    }*/

    @NonNull
    @Override
    public String toString() {
        return "Dr." + name + ", " + gender + ", " + specializations;
    } //Name, gender, specializations []

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

    public List<Timeslot> getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(List<Timeslot> availabilities) {
        this.availabilities = availabilities;
    }

    public List<Appointment> getUpcoming_appointments() {
        return upcoming_appointments;
    }

    public void setUpcoming_appointments(List<Appointment> upcoming_appointments) {
        this.upcoming_appointments = upcoming_appointments;
    }
}
