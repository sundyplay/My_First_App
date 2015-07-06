package com.sundyplay.sunjiaqi.myfirstapp;

import android.util.Log;

import java.text.DecimalFormat;

/**
 * Created by sunjiaqi on 01/07/2015.
 */
public class TimeTransfer {
    public static final String DEBUG = "jiaqisun";
    public String timeOrigin;
    private double hour =0.;
    private double minute =0.;
    private double startMinute;
    private double endMinute;
    public static final char COLON = ':';

    public void setTimeOrigin(String timeOrigin) {
        this.timeOrigin = timeOrigin;
    }

    public double returnDouble() {
        double value = Double.valueOf(timeOrigin);
        return value;
    }
    public double getHour(){
        int index = 0;
        do {
                String buffer = timeOrigin.substring(0, index+1);
                hour = Double.parseDouble(buffer);

            index++;

        } while (timeOrigin.charAt(index) != COLON);
        //Log.i("jiaqisun", "TEST!!!");
        return hour;
    }

    public double getMinute() {
        int index = 0;
        int numberOfCharacters = timeOrigin.length();
        while (index < numberOfCharacters) {
            if (timeOrigin.charAt(index) == COLON) {
                String buffer = timeOrigin.substring(index+1, timeOrigin.length());
                minute = Double.parseDouble(buffer);
                //Log.i("jiaqisun", buffer);
            }
            index++;
        } ;
        return minute;
    }


    public double timeCalculator(double startHour,double startMinute,double endHour,double endMinute) {
        double hour;

        this.startMinute = startMinute / 60.0;
        this.endMinute = endMinute / 60.0;
        hour = (endHour + this.endMinute) - (startHour + this.startMinute);
        return hour;
    }


}
