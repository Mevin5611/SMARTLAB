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
import android.widget.TextView;

public abstract class custom_view_key_logs extends BaseAdapter implements View.OnClickListener {
    String[] date, time, key;
    SharedPreferences sh;
    String url;
    private Context context;


    public custom_view_key_logs(Context applicationContext, String[] date, String[] time, String[] key) {
        this.context = applicationContext;

        this.date = date;
        this.time = time;
        this.key = key;
    }



    @Override
    public int getCount () { return key.length; }

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
            gridView = inflator.inflate(R.layout.activity_custom_view_key_logs, null);//same class name

        } else {
            gridView = (View) view;

        }

        TextView tv1 = (TextView) gridView.findViewById(R.id.textView19);
        TextView tv2 = (TextView) gridView.findViewById(R.id.textView21);
        TextView tv3 = (TextView) gridView.findViewById(R.id.textView24);


        tv1.setTextColor(Color.RED);//color setting
        tv1.setText(date[i]);

        tv2.setTextColor(Color.RED);//color setting
        tv2.setText(time[i]);

        tv3.setTextColor(Color.RED);//color setting
        tv3.setText(key[i]);


        return gridView;


//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_custom_view_key_logs);
    }
}