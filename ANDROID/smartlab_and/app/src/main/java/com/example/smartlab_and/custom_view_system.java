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

public class custom_view_system extends BaseAdapter{
    String[]  name, mac_id, sid;
    SharedPreferences sh;
    String url;
    Button b,b1,b2,b3,b4,b5,b6;
    private Context context;


    public custom_view_system(Context applicationContext, String[] sid, String[] name, String[] mac_id ) {
        this.context = applicationContext;
        this.name =  name;
        this.mac_id =mac_id;
        this.sid =sid;
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
        LayoutInflater inflator=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if(view==null)
        {
            gridView=new View(context);
            //gridView=inflator.inflate(R.layout.customview, null);
            gridView=inflator.inflate(R.layout.activity_custom_view_system,null);//same class name

        }
        else
        {
            gridView=(View)view;

        }
        TextView tv1=(TextView)gridView.findViewById(R.id.textView15);
        TextView tv2=(TextView)gridView.findViewById(R.id.textView17);
        Button b_process = (Button) gridView.findViewById(R.id.button8);
        Button b_screen = (Button) gridView.findViewById(R.id.button9);
        Button b_keylogs = (Button) gridView.findViewById(R.id.button10);
        Button b_shutdown = (Button) gridView.findViewById(R.id.button11);
        Button b_restart = (Button) gridView.findViewById(R.id.button12);
        Button b_newprocess = (Button) gridView.findViewById(R.id.button13);
        Button b_delete = (Button) gridView.findViewById(R.id.button14);



        b_process.setTag(i);
        b_process.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (int) view.getTag();
//                Toast.makeText(context.getApplicationContext(), "process", Toast.LENGTH_LONG).show();
                SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor ed=sh.edit();
                ed.putString("sid",sid[pos]);
                ed.commit();
                Intent ij=new Intent(context,view_running_process.class);
                ij.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(ij);
            }
        });


        b_screen.setTag(i);
        b_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (int) view.getTag();
//                Toast.makeText(context.getApplicationContext(), "process", Toast.LENGTH_LONG).show();
                SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor ed=sh.edit();
                ed.putString("sid",sid[pos]);
                ed.commit();
                Intent ij=new Intent(context,screenshot.class);
                ij.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(ij);

            }
        });

        b_keylogs.setTag(i);
        b_keylogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (int) view.getTag();
//                Toast.makeText(context.getApplicationContext(), "process", Toast.LENGTH_LONG).show();
                SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor ed=sh.edit();
                ed.putString("sid",sid[pos]);
                ed.commit();
                Intent ij=new Intent(context,view_key_logs.class);
                ij.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(ij);

            }
        });

        b_shutdown.setTag(i);
        b_shutdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (int) view.getTag();
//                Toast.makeText(context.getApplicationContext(), "process", Toast.LENGTH_LONG).show();

                sh= PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                sh.getString("ip","");
                url=sh.getString("url","")+"shutdown";

                RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
                                        Toast.makeText(context.getApplicationContext(), "shutdown", Toast.LENGTH_LONG).show();
                                        Intent ij = new Intent(context.getApplicationContext(),view_system .class);
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

                        params.put("sys_id", sid[pos]);//passing to python
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

        b_restart.setTag(i);
        b_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (int) view.getTag();
//                Toast.makeText(context.getApplicationContext(), "process", Toast.LENGTH_LONG).show();

                sh= PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                sh.getString("ip","");
                url=sh.getString("url","")+"restart";

                RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
                                        Toast.makeText(context.getApplicationContext(), "restart", Toast.LENGTH_LONG).show();
                                        Intent ij = new Intent(context.getApplicationContext(),view_system .class);
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

                        params.put("sys_id", sid[pos]);//passing to python
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

        b_newprocess.setTag(i);
        b_newprocess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    int pos = (int) view.getTag();
//                Toast.makeText(context.getApplicationContext(), "process", Toast.LENGTH_LONG).show();
                    SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor ed=sh.edit();
                    ed.putString("sid",sid[pos]);
                    ed.commit();
                    Intent ij=new Intent(context,create_process.class);
                    ij.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(ij);

            }
        });



        b_delete.setTag(i);
        b_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos=(int)view.getTag();
                sh= PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                sh.getString("ip","");
                url=sh.getString("url","")+"and_delete";

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
                                    Intent ij = new Intent(context.getApplicationContext(),view_system .class);
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

                        params.put("sys_id", sid[pos]);//passing to python
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


        tv1.setTextColor(Color.RED);//color setting
        tv2.setTextColor(Color.BLACK);


        tv1.setText(name[i]);
        tv2.setText(mac_id[i]);

        return gridView;




    }
}

