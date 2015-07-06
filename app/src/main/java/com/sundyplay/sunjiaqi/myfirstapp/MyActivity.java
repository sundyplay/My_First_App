package com.sundyplay.sunjiaqi.myfirstapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sundyplay.sunjiaqi.myfirstapp.db.TimeDBOpenHelper;
import com.sundyplay.sunjiaqi.myfirstapp.db.TimeDataSource;
import com.sundyplay.sunjiaqi.myfirstapp.util.UIHelper;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;


public class MyActivity extends ActionBarActivity {

    public static final String DEBUG = "jiaqisun";
    public static final String MARK1 = "date";
    public static final String MARK2 = "hours";
    public static final String MARK3 = "id";
    public static final String PAYMENT = "payment";

    TimeDataSource dataSource = new TimeDataSource(this);
    String date;
    double finalHourTotal;
    int day, wage;
    public List<DateAndTime> data ,filerResult;
    ListView inforView;
    DateTransfer dateTransfer;
    private SharedPreferences setPayment;
    Boolean tOrF = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        setPayment = getSharedPreferences("data",MODE_PRIVATE);

        displayRefresh();

/*        Iterator<DateAndTime> iterator = data.iterator();
        StringBuffer builder = new StringBuffer();
        while (iterator.hasNext()) {
            DateAndTime item = iterator.next();
            builder.append(item.getDate()).append("   ").append(item.getTime()).append("\n");
        }*/

        inforView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DateAndTime dateAndTime = data.get(position);
                displayDetail(dateAndTime);
            }
        });


    }

    public void addRecord(View view) {

        TimeTransfer timeTransfer = new TimeTransfer();
        dateTransfer = new DateTransfer();

        String startTime = UIHelper.getText(this, R.id.startTime);
        String endTime = UIHelper.getText(this, R.id.endTime);

        tOrF = Pattern.matches("([1-9]|1[0-9]|2[0-3]):([0-5][0-9])", startTime)
                    && Pattern.matches("([1-9]|1[0-9]|2[0-3]):([0-5][0-9])", endTime);

        if (tOrF) {
            timeTransfer.setTimeOrigin(startTime);
            double startHour = timeTransfer.getHour();
            double startMinute = timeTransfer.getMinute();

            timeTransfer.setTimeOrigin(endTime);
            double endHour = timeTransfer.getHour();
            double endMinute = timeTransfer.getMinute();

            double hourTotal = timeTransfer.timeCalculator(startHour, startMinute, endHour, endMinute);

            BigDecimal newHourTotal = new BigDecimal(hourTotal);
            finalHourTotal = newHourTotal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            date = dateTransfer.getDate();
            day = dateTransfer.getDay();
            wage = setPayment.getInt(PAYMENT, 0);
            Log.i(DEBUG, String.valueOf("here is ok!!"));

            createData();
            displayRefresh();

        }else {
            Toast.makeText(this,"Time Format is HH:MM",Toast.LENGTH_LONG).show();
        }
    }

    public void inquiryDate(View view) {

        String startDate = UIHelper.getText(this, R.id.startTime);
        String endDate = UIHelper.getText(this, R.id.endTime);

        tOrF = Pattern.matches("2105-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])", startDate)
                && Pattern.matches("2015-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])", endDate);
        Log.i(DEBUG, String.valueOf(tOrF));

        if (tOrF) {
            filerResult = dataSource.findFiltered("date between "+"'"+startDate+"'"+ " and "+ "'"+endDate+"'");
            Iterator<DateAndTime> iterator = filerResult.iterator();
            double hours = 0;
            double payment = 0;
            while (iterator.hasNext()) {
                DateAndTime item = iterator.next();
                hours += item.getTime();
                payment += item.getWage() * item.getTime();
                Log.i(DEBUG, String.valueOf(payment));
            }
            String showResult = "You have worked "+String.valueOf(hours)+" hours and got "+String.valueOf(payment)+" dollars";
            TextView tv = (TextView) findViewById(R.id.showResult);
            tv.setText(showResult);
            filterRefresh();

        }else {
            Toast.makeText(this,"Date Format is YYYY-MM-DD",Toast.LENGTH_LONG).show();

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        dataSource.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        dataSource.close();
    }

    public void createData() {
        DateAndTime dateAndTime = new DateAndTime();
        dateAndTime.setDate(date);
        dateAndTime.setTime(finalHourTotal);
        dateAndTime.setDay(day);
        dateAndTime.setWage(wage);
        dateAndTime = dataSource.create(dateAndTime);
        Toast.makeText(this,"Data created with ID " + dateAndTime.getId(),Toast.LENGTH_LONG).show();
        //Log.i(DEBUG, "data created with ID " + dateAndTime.getId());
    }

    private void displayRefresh() {
        dataSource.open();

        data = dataSource.findAll();

        ArrayAdapter<DateAndTime> recordArrayAdapter =
                new TimeArrayAdapter(this, 0, data);

        inforView= (ListView) findViewById(R.id.list);
        inforView.setAdapter(recordArrayAdapter);

    }

    private void filterRefresh() {

        ArrayAdapter<DateAndTime> recordArrayAdapter =
                new TimeArrayAdapter(this, 0, filerResult);

        inforView= (ListView) findViewById(R.id.list);
        inforView.setAdapter(recordArrayAdapter);
    }

    private void displayDetail(DateAndTime dateAndTime) {
        //Log.d(DEBUG, "Displaying item" + dateAndTime.getTime());

        Intent intent = new Intent(this, DisplayMessageActivity.class);
        intent.putExtra(MARK1, dateAndTime.getDate());
        intent.putExtra(MARK2, dateAndTime.getTime());
        intent.putExtra(MARK3, dateAndTime.getId());
        startActivity(intent);

    }

    public void actionResourceClickHandler(MenuItem item) {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {



        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
