package com.example.smartlab_and;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public abstract class custom_view_running_process extends BaseAdapter implements View.OnClickListener {
    String[] date, time, name;
    SharedPreferences sh;
    String url;
    private Context context;

    public custom_view_running_process(Context applicationContext, String[] name, String[] date, String[] time) {
        this.context = applicationContext;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public custom_view_running_process(Context applicationContext, String[] subject, String[] date, String[] time, String[] description) {
    }

    @Override
        public int getCount () { return name.length; }

        @Override
        public Object getItem ( int i)
        {
            return null;
        }

        @Override
        public long getItemId ( int i){
            return 0;
        }

        @Override
        public View getView ( int i, View view, ViewGroup viewGroup){
            LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View gridView;

            if (view == null) {
                gridView = new View(context);
                //gridView=inflator.inflate(R.layout.customview, null);
                gridView = inflator.inflate(R.layout.activity_custom_view_running_process, null);//same class name

            } else {
                gridView = (View) view;

            }

            TextView tv1 = (TextView) gridView.findViewById(R.id.textView7);
            TextView tv2 = (TextView) gridView.findViewById(R.id.textView9);
            TextView tv3 = (TextView) gridView.findViewById(R.id.textView11);


            tv1.setTextColor(Color.RED);//color setting
            tv1.setText(date[i]);

            tv2.setTextColor(Color.RED);//color setting
            tv2.setText(time[i]);

            tv3.setTextColor(Color.RED);//color setting
            tv3.setText(name[i]);


            return gridView;

        }
    }

