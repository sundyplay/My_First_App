package com.sundyplay.sunjiaqi.myfirstapp;

/**
 * Created by sunjiaqi on 01/07/2015.
 */
public class DateAndTime {
    long id;
    String date;
    double time;
    private int wage;
    int day;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public int getWage() {
        return wage;
    }

    public void setWage(int wage) {
        this.wage = wage;

    }

    @Override
    public String toString() {
        return id+"  "+date+"   "+time;
    }
}
