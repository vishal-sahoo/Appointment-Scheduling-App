package com.example.b07projectgroup4;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Calendar;

public class Session implements Serializable {
    private Calendar start_time;
    private Calendar end_time;

    public Session(){

    }

    public Session(Calendar start_time, Calendar end_time) {
        this.start_time = start_time;
        this.end_time = end_time;
    }

    @NonNull
    @Override
    public String toString() {
        return "Session{" +
                "start_time=" + start_time +
                ", end_time=" + end_time +
                '}';
    }

    public Calendar getStart_time() {
        return start_time;
    }

    public void setStart_time(Calendar start_time) {
        this.start_time = start_time;
    }

    public Calendar getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Calendar end_time) {
        this.end_time = end_time;
    }
}
