package com.example.calendarapp;

import java.io.Serializable;
import java.util.Date;

public class Event implements Serializable {
    private int id;
    private String title;
    private String description;
    private Date date;
    private int hour;
    private int minute;
    private boolean hasNotification;

    public Event() {}

    public Event(String title, String description, Date date, int hour, int minute, boolean hasNotification) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.hour = hour;
        this.minute = minute;
        this.hasNotification = hasNotification;
    }

    public Event(int id, String title, String description, Date date, int hour, int minute, boolean hasNotification) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.hour = hour;
        this.minute = minute;
        this.hasNotification = hasNotification;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public int getHour() { return hour; }
    public void setHour(int hour) { this.hour = hour; }

    public int getMinute() { return minute; }
    public void setMinute(int minute) { this.minute = minute; }

    public boolean hasNotification() { return hasNotification; }
    public void setHasNotification(boolean hasNotification) { this.hasNotification = hasNotification; }
}