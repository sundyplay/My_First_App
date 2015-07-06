package com.sundyplay.sunjiaqi.myfirstapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.sundyplay.sunjiaqi.myfirstapp.DateAndTime;
import com.sundyplay.sunjiaqi.myfirstapp.TimeTransfer;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunjiaqi on 01/07/2015.
 */
public class TimeDataSource {
    public static final String DEBUG = "jiaqisun";

    SQLiteOpenHelper dbhelper;
    SQLiteDatabase database;

    private static final String[] allColumns = {
            TimeDBOpenHelper.COLUMN_ID,
            TimeDBOpenHelper.COLUMN_DATE,
            TimeDBOpenHelper.COLUMN_HOUR,
            TimeDBOpenHelper.COLUMN_WAGE,
            TimeDBOpenHelper.COLUMN_DAY
    };

    public TimeDataSource(Context context) {
        dbhelper = new TimeDBOpenHelper(context);
    }

    public void open() {
        Log.i(TimeTransfer.DEBUG, "Database opened");
        database = dbhelper.getWritableDatabase();
    }

    public void close() {
        Log.i(TimeTransfer.DEBUG, "Database closed");
        dbhelper.close();
    }

    public DateAndTime create(DateAndTime dateAndTime) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TimeDBOpenHelper.COLUMN_DATE, dateAndTime.getDate());
        contentValues.put(TimeDBOpenHelper.COLUMN_HOUR, dateAndTime.getTime());
        contentValues.put(TimeDBOpenHelper.COLUMN_WAGE, dateAndTime.getWage());
        contentValues.put(TimeDBOpenHelper.COLUMN_DAY, dateAndTime.getDay());
        long insertId = database.insert(TimeDBOpenHelper.TABLE_TIMESHEET, null, contentValues);
        dateAndTime.setId(insertId);
        return dateAndTime;
    }

    public void update(DateAndTime dateAndTime) {
        ContentValues contentValues = new ContentValues();
        long rowId = dateAndTime.getId();
        contentValues.put(TimeDBOpenHelper.COLUMN_HOUR, dateAndTime.getTime());
        Log.i(DEBUG, String.valueOf(dateAndTime.getId()));
        String selection = TimeDBOpenHelper.COLUMN_ID + "=?";
        String[] selectionArgs = {String.valueOf(rowId)};
        database.update(TimeDBOpenHelper.TABLE_TIMESHEET, contentValues, selection, selectionArgs);
    }

    public void delete(long rowId) {
        String select = TimeDBOpenHelper.COLUMN_ID + "=?";
        String[] selectionArgs = {String.valueOf(rowId)};
        database.delete(TimeDBOpenHelper.TABLE_TIMESHEET,select,selectionArgs);
    }

    public List<DateAndTime> findAll() {

        List<DateAndTime> dateAndTimes = new ArrayList<>();

        Cursor cursor = database.query(TimeDBOpenHelper.TABLE_TIMESHEET, allColumns,
                null, null, null, null, TimeDBOpenHelper.COLUMN_ID+ " desc");

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                DateAndTime dateAndTime = new DateAndTime();
                dateAndTime.setId(cursor.getLong(cursor.getColumnIndex(TimeDBOpenHelper.COLUMN_ID)));
                dateAndTime.setDate(cursor.getString(cursor.getColumnIndex(TimeDBOpenHelper.COLUMN_DATE)));
                dateAndTime.setTime(cursor.getDouble(cursor.getColumnIndex(TimeDBOpenHelper.COLUMN_HOUR)));
                dateAndTime.setWage(cursor.getInt(cursor.getColumnIndex(TimeDBOpenHelper.COLUMN_WAGE)));
                dateAndTime.setDay(cursor.getInt(cursor.getColumnIndex(TimeDBOpenHelper.COLUMN_DAY)));
                dateAndTimes.add(dateAndTime);
            }
        }
        return dateAndTimes;
    }

    public List<DateAndTime> findFiltered(String selection) {
        List<DateAndTime> dateAndTimes = new ArrayList<>();

        Cursor cursor = database.query(TimeDBOpenHelper.TABLE_TIMESHEET,allColumns,
                selection,null,null,null,TimeDBOpenHelper.COLUMN_ID+ " desc");
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                DateAndTime dateAndTime = new DateAndTime();
                dateAndTime.setId(cursor.getLong(cursor.getColumnIndex(TimeDBOpenHelper.COLUMN_ID)));
                dateAndTime.setDate(cursor.getString(cursor.getColumnIndex(TimeDBOpenHelper.COLUMN_DATE)));
                dateAndTime.setTime(cursor.getDouble(cursor.getColumnIndex(TimeDBOpenHelper.COLUMN_HOUR)));
                dateAndTime.setWage(cursor.getInt(cursor.getColumnIndex(TimeDBOpenHelper.COLUMN_WAGE)));
                dateAndTime.setDay(cursor.getInt(cursor.getColumnIndex(TimeDBOpenHelper.COLUMN_DAY)));
                dateAndTimes.add(dateAndTime);
            }
        }
        return dateAndTimes;


    }
}
