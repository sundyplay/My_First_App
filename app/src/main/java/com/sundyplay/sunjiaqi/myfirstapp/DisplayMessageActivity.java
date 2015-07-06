package com.sundyplay.sunjiaqi.myfirstapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sundyplay.sunjiaqi.myfirstapp.db.TimeDBOpenHelper;
import com.sundyplay.sunjiaqi.myfirstapp.db.TimeDataSource;
import com.sundyplay.sunjiaqi.myfirstapp.util.UIHelper;


public class DisplayMessageActivity extends ActionBarActivity {

    public static final String DEBUG = "jiaqisun";
    public static final String PAYMENT = "payment";
    TimeDataSource dataSource = new TimeDataSource(this);
    long message3;
    EditText editText;
    private SharedPreferences setPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        setPayment = getSharedPreferences("data",MODE_PRIVATE);

        TimeDataSource dataSource = new TimeDataSource(this);
        dataSource.open();

        Intent intent = getIntent();
        String message1 = intent.getStringExtra(MyActivity.MARK1);
        Double message2 = intent.getDoubleExtra(MyActivity.MARK2, 0);
        message3 = intent.getLongExtra(MyActivity.MARK3, 0);

        TextView textView1 = (TextView) findViewById(R.id.tv1);
        textView1.setText(String.valueOf(message3));
        TextView textView2 = (TextView) findViewById(R.id.tv2);
        textView2.setText(message1);
        editText = (EditText) findViewById(R.id.et1);
        editText.setText(String.valueOf(message2));


    }



    public void updateItem(View view) {
        String timeUpdate = editText.getText().toString();

        TimeTransfer timeTransfer = new TimeTransfer();
        timeTransfer.setTimeOrigin(timeUpdate);
        double newDoubleTime = timeTransfer.returnDouble();

        DateAndTime dateAndTime = new DateAndTime();
        dateAndTime.setTime(newDoubleTime);
        dateAndTime.setId(message3);
        dataSource.open();
        dataSource.update(dateAndTime);
        Toast.makeText(this, "Data updated with ID " + dateAndTime.getId(), Toast.LENGTH_LONG).show();
    }

    public void deleteItem(View view) {
        Toast.makeText(this, "DELETE SUCCESSFULLY!!!", Toast.LENGTH_LONG).show();
        dataSource.open();
        dataSource.delete(message3);
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

    public void submitPayment(View view) {
        SharedPreferences.Editor editor = setPayment.edit();
        String preValue = UIHelper.getText(this, R.id.etNumber);
        editor.putInt(PAYMENT, Integer.valueOf(preValue));
        editor.commit();
/*        int test = setPayment.getInt(PAYMENT, 0);
        Log.i(DEBUG, String.valueOf(test));*/

    }

/*    public int getPre() {
        Log.i(DEBUG, String.valueOf(setPayment.getInt(PAYMENT, 0)));
        int test = setPayment.getInt(PAYMENT, 0);
        Log.i(DEBUG, String.valueOf(test));
        return test;
    }*/

}
