package com.example.panels.Model;

public class Schedule {

    private String scheduleID;
    private int time;
    private String meridiem;  //AM, PM
    private String[] days;

    public void setScheduleID(String scheduleID) {
        this.scheduleID = scheduleID;
    }

    public String getScheduleID() {
        return scheduleID;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public void setMeridiem(String meridiem) {
        this.meridiem = meridiem;
    }

    public String getMeridiem() {
        return meridiem;
    }

    public void setDays(String[] days) {
        this.days = days;
    }

    public String[] getDays() {
        return days;
    }
}
