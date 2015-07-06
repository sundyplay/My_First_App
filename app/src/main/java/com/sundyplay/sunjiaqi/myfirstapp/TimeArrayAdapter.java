package com.sundyplay.sunjiaqi.myfirstapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sunjiaqi on 06/07/2015.
 */
class TimeArrayAdapter extends ArrayAdapter<DateAndTime> {

    Context context;
    List<DateAndTime> objects;

    public TimeArrayAdapter(Context context, int resource, List<DateAndTime> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DateAndTime dateAndTime = objects.get(position);
        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(MyActivity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_item, null);


        TextView tv = (TextView) view.findViewById(R.id.tvTitle);
        tv.setText(dateAndTime.getDate()
                + "  Work Hours: " + dateAndTime.getTime()
                + "  Your wage: " + dateAndTime.getWage() +"/h" + "\n"
                + "Michelle have to pay Sandy " + dateAndTime.getTime() * dateAndTime.getWage() + " Dollars!");


        ImageView iv = (ImageView) view.findViewById(R.id.imageView);
        int res = context.getResources().getIdentifier
                ("image_" + dateAndTime.getDay(), "drawable", context.getPackageName());
        iv.setImageResource(res);

        return view;
    }
}
