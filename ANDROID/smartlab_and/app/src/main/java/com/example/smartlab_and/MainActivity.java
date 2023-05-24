package com.example.smartlab_and;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText e1;
    Button b;
    SharedPreferences sh;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e1=findViewById(R.id.editTextTextPersonName);
        b=findViewById(R.id.button);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1.setText(sh.getString("ip",""));
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int flg=0;

                String ip=e1.getText().toString();
                if(ip.equalsIgnoreCase("")){
                    flg++;
                    e1.setError("*");

                }
                if(flg == 0) {
                    String url1 = "http://" + ip + ":5000/";
                    SharedPreferences.Editor ed = sh.edit();
                    ed.putString("ip", ip);
                    ed.putString("url", url1);
                    ed.commit();
                    Intent i = new Intent(getApplicationContext(), login.class);
                    startActivity(i);
                }
            }
        });
    }
}