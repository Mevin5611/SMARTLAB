package com.example.smartlab_and;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class custom_view_attendance extends BaseAdapter {

    String[] date, student, attendance;
    private Context context;
    Button b1;

    public custom_view_attendance(Context applicationContext,String[] date, String[] student, String[] attendance) {
        this.context = applicationContext;
        this.date = date;
        this.student = student;
        this.attendance = attendance;
    }
    @Override
    public int getCount() {
        return date.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflator=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if(view==null)
        {
            gridView=new View(context);
            //gridView=inflator.inflate(R.layout.customview, null);
            gridView=inflator.inflate(R.layout.activity_custom_view_attendance,null);//same class name

        }
        else
        {
            gridView=(View)view;

        }
        TextView tv1=(TextView)gridView.findViewById(R.id.textView38);
        TextView tv2=(TextView)gridView.findViewById(R.id.textView41);
        TextView tv3=(TextView)gridView.findViewById(R.id.textView42);


        tv1.setTextColor(Color.RED);//color setting
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);


        tv1.setText(date[i]);
        tv2.setText(student[i]);
        tv3.setText(attendance[i]);
        return gridView;


    }
}