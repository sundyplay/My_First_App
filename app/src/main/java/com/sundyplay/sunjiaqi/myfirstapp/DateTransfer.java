package com.sundyplay.sunjiaqi.myfirstapp;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by sunjiaqi on 01/07/2015.
 */
public class DateTransfer {
    TimeZone timeZone = TimeZone.getTimeZone("GMT+10");
    Date date = new Date();

    public String getDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setTimeZone(timeZone);
        String dateFormat = simpleDateFormat.format(date);

        //Log.i(TimeTransfer.DEBUG, dateFormat);
        return dateFormat;
    }

    public int getDay() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }
}
