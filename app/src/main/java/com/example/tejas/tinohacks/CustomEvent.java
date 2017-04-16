package com.example.tejas.tinohacks;

/**
 * Created by TinoHacks on 4/16/17.
 */

public class CustomEvent {

    private String title;
    private String address;
    private String time;
    private int index;
    private int attendees;

    public CustomEvent(String title, String time, String address) {
        this.time = time;
        this.address = address;
        this.title = title;
        this.index = index;
        this.attendees = attendees;
    }

    public CustomEvent() {

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setAttendees(int attendees) {
        this.attendees = attendees;
    }

    public int getIndex() {
        return index;
    }

    public String getAddress() {
        return address;
    }

    public String getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }

    public int getAttendees() {
        return attendees;
    }

    @Override
    public String toString() {
        return time + " || " + address + " || " + attendees;
    }
}
