package com.sundyplay.sunjiaqi.myfirstapp;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sundyplay.sunjiaqi.myfirstapp.db.TimeDataSource;

import java.util.List;

/**
 * Created by sunjiaqi on 06/07/2015.
 */
public class MyFragment extends ListFragment{

    List<DateAndTime> dateAndTimeList = new TimeDataSource(getActivity()).findAll();


    public MyFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TimeArrayAdapter adapter = new TimeArrayAdapter(getActivity(), R.id.list_item, dateAndTimeList);
        setListAdapter(adapter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_item_list, container, false);
        return rootView;
    }
}
