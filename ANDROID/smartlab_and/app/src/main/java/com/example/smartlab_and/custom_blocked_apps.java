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

public  class custom_blocked_apps extends BaseAdapter  {
    String[] name,block_id;
    SharedPreferences sh;
    String url;
    Button b;
    private Context context;

    public custom_blocked_apps(Context applicationContext, String[] name, String[] block_id) {
        this.context = applicationContext;
        this.name = name;
        this.block_id = block_id;
    }

    @Override
    public int getCount() {
        return name.length;
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
            gridView = inflator.inflate(R.layout.activity_custom_blocked_apps, null);//same class name

        } else
            {
            gridView = (View) view;

        }
        TextView tv1=(TextView)gridView.findViewById(R.id.textView13);



        tv1.setTextColor(Color.RED);//color setting
//
        Button delete = (Button) gridView.findViewById(R.id.button7);

        tv1.setText(name[i]);

        delete.setTag(i);
        delete.setOnClickListener(new View.OnClickListener()
                                  {
                                      @Override
                                      public void onClick(View view) {
                                          int pos = (int) view.getTag();
                                          sh = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                                          sh.getString("ip", "");
                                          url = sh.getString("url", "") + "an_delete";
                                          RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
                                          StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                                                  new Response.Listener<String>() {
                                                      @Override
                                                      public void onResponse(String response) {
                                                          //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                                                          try {
                                                              JSONObject jsonObj = new JSONObject(response);
                                                              if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
                                                                  Toast.makeText(context.getApplicationContext(), "Deleted", Toast.LENGTH_LONG).show();
                                                                  Intent ij = new Intent(context.getApplicationContext(),blocked_apps .class);

                                                                  ij.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                  ij.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                                  ij.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                                  context.startActivity(ij);
                                                              } else {
                                                                  Toast.makeText(context.getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
                                                              }

                                                          } catch (Exception e) {
                                                              Toast.makeText(context.getApplicationContext(), "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                                          }
                                                      }
                                                  },
                                                  new Response.ErrorListener() {
                                                      @Override
                                                      public void onErrorResponse(VolleyError error) {
                                                          // error
                                                          Toast.makeText(context.getApplicationContext(), "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
                                                      }
                                                  }
                                          ) {

                                              //                value Passing android to python
                                              @Override
                                              protected Map<String, String> getParams() {
                                                  SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                                                  Map<String, String> params = new HashMap<String, String>();

                                                  params.put("block_id", block_id[pos]);//passing to python
//                params.put("p", pwd);


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
                                  });


                                          /* ImageView im=(ImageView) gridView.findViewById(R.id.imageView101); */


      return gridView;

  }


}



//
