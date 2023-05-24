package com.example.smartlab_and;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class view_exam_arrangement extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner sp_course, sp_sem, sp_subject, sp_stud, sp_sys;
    Button b;
    SharedPreferences sh;
    String url,ip;
    ListView lv;
    FloatingActionButton fab;

    String c_id="";
    String[] courseid,coursename;
    String examid="";
    String[] exam_id, exam_name;
    String sem="";
    String[] semester={"Select Semester", "First Semester", "Second Semester", "Third Semester", "Fourth Semester", "Fifth Semester", "Sixth Semester"};

    public void course_load(){
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        url = sh.getString("url", "") + "/and_course_load";


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            if (jsonObj.getString("status").equalsIgnoreCase("ok")) {

                                JSONArray js = jsonObj.getJSONArray("data");//from python

                                courseid = new String[js.length()+1];
                                coursename = new String[js.length()+1];


                                for (int i = 0; i < js.length()+1; i++) {
                                    if(i==0){
                                        courseid[i] = "Select course";//dbcolumn name in double quotes
                                        coursename[i] = "Select course";
                                    } else {

                                        JSONObject u = js.getJSONObject(i-1);
                                        courseid[i] = u.getString("course_id");//dbcolumn name in double quotes
                                        coursename[i] = u.getString("course_name");
                                    }


                                }
                                ArrayAdapter ad = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, coursename);
//                                ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                sp_course.setAdapter(ad);

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
                params.put("lid", sh.getString("lid",""));//passing to python
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


    public void exam_load(){
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        url = sh.getString("url", "") + "/and_exam_load";


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            if (jsonObj.getString("status").equalsIgnoreCase("ok")) {

                                JSONArray js = jsonObj.getJSONArray("data");//from python

                                exam_id = new String[js.length()+1];
                                exam_name = new String[js.length()+1];


                                for (int i = 0; i < js.length()+1; i++) {
                                    if(i==0){
                                        exam_id[i] = "Select Exam";
                                        exam_name[i] = "Select Exam";
                                    }
                                    else{
                                        JSONObject u = js.getJSONObject(i-1);
                                        exam_id[i] = u.getString("exam_id");//dbcolumn name in double quotes
                                        exam_name[i] = u.getString("sub_name") + " - " + u.getString("date");
                                    }



                                }
                                ArrayAdapter ad_exam = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, exam_name);
//                                ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                sp_subject.setAdapter(ad_exam);

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
                params.put("cid", c_id);//passing to python
                params.put("sem", sem);//passing to python
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


    String[] sys_name, stud_name, arr_id;
    public void arrangement_load(){
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        url = sh.getString("url", "") + "/and_view_arrangement";


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            if (jsonObj.getString("status").equalsIgnoreCase("ok")) {

                                JSONArray js = jsonObj.getJSONArray("data");//from python

                                arr_id = new String[js.length()];
                                sys_name = new String[js.length()];
                                stud_name = new String[js.length()];


                                for (int i = 0; i < js.length(); i++) {

                                    JSONObject u = js.getJSONObject(i);
                                    arr_id[i] = u.getString("arr_id");//dbcolumn name in double quotes
                                    sys_name[i] = u.getString("sys_name");
                                    stud_name[i] = u.getString("name");
                                }


                                lv.setAdapter(new custom_exam_arrangement(getApplicationContext(), stud_name, sys_name, arr_id));


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
            params.put("exam_id", examid);//passing to python

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_exam_arrangement);

        sp_course=(Spinner) findViewById(R.id.spinner10);
        sp_subject=(Spinner) findViewById(R.id.spinner12);
        sp_sem=(Spinner) findViewById(R.id.spinner11);
        fab=findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ij = new Intent(getApplicationContext(), lab_exam_system_allocation.class);
                startActivity(ij);
            }
        });
        lv=findViewById(R.id.list);
        ArrayAdapter <String> ad =new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, semester);
        sp_sem.setAdapter(ad);

        sp_course.setOnItemSelectedListener(this);
        sp_sem.setOnItemSelectedListener(this);
        sp_subject.setOnItemSelectedListener(this);


        course_load();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView == sp_sem){
            sem = semester[i];
            if(!sem.equalsIgnoreCase("Select Semester")){
                exam_load();
            }

        }
        if(adapterView == sp_course){
            c_id = courseid[i];
            if(!c_id.equalsIgnoreCase("Select course")){
                exam_load();
            }
        }

        if(adapterView == sp_subject){
            examid =exam_id[i];
            if(!examid.equalsIgnoreCase("Select Exam")){
                arrangement_load();
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}