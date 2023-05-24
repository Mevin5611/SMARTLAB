package com.example.smartlab_and;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class single_screen extends AppCompatActivity {
    ImageView im;
    SharedPreferences sh;
    String ip, url ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_screen);
        im=(ImageView)findViewById(R.id.imageView3);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ip = sh.getString("url", "");
        url = ip + sh.getString("screen_url", "");
        Picasso.with(getApplicationContext()).load(url).into(im);
    }
}