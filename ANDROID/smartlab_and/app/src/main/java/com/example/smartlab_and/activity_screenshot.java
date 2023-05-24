package com.example.smartlab_and;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class activity_screenshot extends BaseAdapter {
    String[] date, image;
    SharedPreferences sh;
    String url;
    private Context context;

    public activity_screenshot(Context applicationContext, String[] date, String[] image) {
        this.context = applicationContext;

        this.date = date;
        this.image = image;
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
        LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (view == null) {
            gridView = new View(context);
            //gridView=inflator.inflate(R.layout.customview, null);
            gridView = inflator.inflate(R.layout.activity_screenshot2, null);//same class name

        } else {
            gridView = (View) view;

        }

        TextView tv1 = (TextView) gridView.findViewById(R.id.textView27);
       ImageView ik = (ImageView) gridView.findViewById(R.id.imageView2);
//        TextView tv3 = (TextView) gridView.findViewById(R.id.textView24);


        tv1.setTextColor(Color.RED);//color setting
        tv1.setText(date[i]);
        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
        String ip = sh.getString("url", "");
        String url =ip + image[i];
        Picasso.with(context).load(url).into(ik);

        return gridView;


    }
}