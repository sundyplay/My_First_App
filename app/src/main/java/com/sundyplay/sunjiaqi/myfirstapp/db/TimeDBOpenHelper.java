package com.sundyplay.sunjiaqi.myfirstapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.sundyplay.sunjiaqi.myfirstapp.TimeTransfer;

import java.sql.Time;

/**
 * Created by sunjiaqi on 01/07/2015.
 */
public class TimeDBOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "time.db";
    private static final int DATABASE_VERSION = 3;

    public static final String TABLE_TIMESHEET = "time";
    public static final String COLUMN_ID = "timeId";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_HOUR = "hours";
    public static final String COLUMN_WAGE = "wage";
    public static final String COLUMN_DAY = "day";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_TIMESHEET + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_DATE + " TEXT, " +
                    COLUMN_HOUR + " REAL, " +
                    COLUMN_WAGE + " INTEGER, " +
                    COLUMN_DAY + " INTEGER " +
                    ")";

    public TimeDBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TimeTransfer.DEBUG, "Table will be created");
        db.execSQL(TABLE_CREATE);
        Log.i(TimeTransfer.DEBUG, "Table has been created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIMESHEET);
        Log.i(TimeTransfer.DEBUG, "Table has been updated");
        onCreate(db);
    }


}
