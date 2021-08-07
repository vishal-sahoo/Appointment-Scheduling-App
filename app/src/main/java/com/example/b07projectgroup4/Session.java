package com.example.b07projectgroup4;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Session implements Serializable {
    private LocalDateTime start_time;
    private Duration length;

    public Session(LocalDateTime start_time, Duration length) {
        this.start_time = start_time;
        this.length = length;
    }

    public LocalDateTime getStart_time() {
        return start_time;
    }

    public void setStart_time(LocalDateTime start_time) {
        this.start_time = start_time;
    }

    public Duration getLength() {
        return length;
    }
    public void setLength(Duration length) {
        this.length = length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return start_time.equals(session.start_time) && length.equals(session.length);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start_time, length);
    }

    @NonNull
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, dd MMM yyyy hh:00 a"); //E, dd MMM yyyy HH:mm
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("hh:00 a");
        return start_time.format(formatter) + " - " + start_time.plus(length).format(formatter2);
    }
}