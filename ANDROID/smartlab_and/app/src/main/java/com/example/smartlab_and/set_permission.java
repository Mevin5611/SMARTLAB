package com.example.smartlab_and;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class set_permission extends AppCompatActivity {
    EditText e1;
    Button b;
    SharedPreferences sh;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_permission);
        e1=findViewById(R.id.editTextTextPersonName7);
        b=findViewById(R.id.button5);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String appname=e1.getText().toString();
                int flg=0;
                final String appname=e1.getText().toString();
                if(appname.equalsIgnoreCase("")){
                 e1.setError("*");
                 flg++;
                }
if(flg==0){

//        Toast.makeText(this, feedback+",",Toast.LENGTH_SHORT).show();
    sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    sh.getString("ip","");
    sh.getString("url","");
    url=sh.getString("url","")+"/set_permission";
    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
    StringRequest postRequest = new StringRequest(Request.Method.POST, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                    try {
                        JSONObject jsonObj = new JSONObject(response);
                        if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
                            Toast.makeText(set_permission.this, "added succesfully", Toast.LENGTH_SHORT).show();
                            Intent i =new Intent(getApplicationContext(),Home.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
                        }

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // error
                    Toast.makeText(getApplicationContext(), "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
                }
            }
    ) {

        //                value Passing android to python
        @Override
        protected Map<String, String> getParams() {
            SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            Map<String, String> params = new HashMap<String, String>();

            params.put("name", appname);//passing to python
//                        params.put("macid", );//passing to python
//                        params.put("p", "1");       // change to sharedpreference
            params.put("lb", sh.getString("lid",""));//passing to python



            return params;
        }
    };


    int MY_SOCKET_TIMEOUT_MS = 100000;

    postRequest.setRetryPolicy(new DefaultRetryPolicy(
            MY_SOCKET_TIMEOUT_MS,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    requestQueue.add(postRequest);


}

            }
        });
    }
}