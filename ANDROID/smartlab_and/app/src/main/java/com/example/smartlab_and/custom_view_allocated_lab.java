package com.example.smartlab_and;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class custom_view_allocated_lab extends BaseAdapter {
    private final Context context;
    String[] labid,roomno,labname;

    public custom_view_allocated_lab(Context applicationContext,String[] labid, String[] roomno, String[] labname) {

        this.context = applicationContext;
        this.labid =labid;
        this.roomno =roomno;
        this.labname=labname;

    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_custom_view_allocated_lab);
//    }

    @Override
    public int getCount() {
        return roomno.length;
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
            LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View gridView;
            if (view == null) {
                gridView = new View(context);
                //gridView=inflator.inflate(R.layout.customview, null);
                gridView = inflator.inflate(R.layout.activity_custom_view_allocated_lab, null);//same class name

            } else {
                gridView = (View) view;

            }
            TextView tv1 = (TextView) gridView.findViewById(R.id.textView3);
            TextView tv2 = (TextView) gridView.findViewById(R.id.textView5);
            Button b1 = (Button) gridView.findViewById(R.id.button6);
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor ed = sh.edit();
                    ed.putString("labid", labid[i]);
                    ed.commit();
                    Intent i=new Intent(context,view_system.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                }
            });


            tv1.setTextColor(Color.BLACK);//color setting
            tv2.setTextColor(Color.BLACK);



            tv1.setText(roomno[i]);
            tv2.setText(labname[i]);


//
            return gridView;
        }

    }
