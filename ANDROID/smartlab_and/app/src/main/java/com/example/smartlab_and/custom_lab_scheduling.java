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

public class custom_lab_scheduling extends BaseAdapter implements View.OnClickListener {
    String[] labname, date, starttime, endtime, description ;
    SharedPreferences sh;
    String url;
    private Context context;

    public custom_lab_scheduling(Context applicationContext,String[] labname, String[] date, String[] starttime, String[] endtime, String[] description) {
        this.context = applicationContext;
        this.labname = labname;
        this.date = date;
        this.starttime = starttime;
        this.endtime = endtime;
        this.description = description;
    }

    @Override
    public int getCount () { return labname.length; }

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
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflator=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if(view==null)
        {
            gridView=new View(context);
            //gridView=inflator.inflate(R.layout.customview, null);
            gridView=inflator.inflate(R.layout.activity_custom_lab_scheduling,null);//same class name

        }
        else
        {
            gridView=(View)view;

        }
        TextView tv1=(TextView)gridView.findViewById(R.id.textView52);
        TextView tv2=(TextView)gridView.findViewById(R.id.textView57);
        TextView tv3=(TextView)gridView.findViewById(R.id.textView58);
        TextView tv4=(TextView)gridView.findViewById(R.id.textView59);
        TextView tv5=(TextView)gridView.findViewById(R.id.textView60);



        tv1.setTextColor(Color.BLACK);//color setting
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);
        tv4.setTextColor(Color.BLACK);
        tv5.setTextColor(Color.BLACK);



        tv1.setText(labname[i]);
        tv2.setText(date[i]);
        tv3.setText(starttime[i]);
        tv4.setText(endtime[i]);
        tv5.setText(description[i]);
        return gridView;

    }

    @Override
    public void onClick(View view) {

    }
}