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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class lab_exam_system_allocation extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    Spinner sp_course, sp_sem, sp_subject, sp_stud, sp_sys;
    Button b;
    SharedPreferences sh;
    String url,ip;

    String c_id="";
    String[] courseid,coursename;
    String examid="";
    String[] exam_id, exam_name;
    String sem="";
    String[] semester={"First Semester", "Second Semester", "Third Semester", "Fourth Semester", "Fifth Semester", "Sixth Semester"};
    String stu_id="";
    String[] studentid,studentname;
    String sy_id="";
    String[] systemid,systemname;


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

                                courseid = new String[js.length()];
                                coursename = new String[js.length()];


                                for (int i = 0; i < js.length(); i++) {
                                    JSONObject u = js.getJSONObject(i);
                                    courseid[i] = u.getString("course_id");//dbcolumn name in double quotes
                                    coursename[i] = u.getString("course_name");


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



    public void student_load(){
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        url = sh.getString("url", "") + "/and_student_load";


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

                                studentid = new String[js.length()+1];
                                studentname = new String[js.length()+1];


                                for (int i = 0; i < js.length()+1; i++) {
                                    if(i==0){
                                        studentid[i] = "Select student";//dbcolumn name in double quotes
                                        studentname[i] = "Select student";
                                    }
                                    else{
                                        JSONObject u = js.getJSONObject(i-1);
                                        studentid[i] = u.getString("student_id");//dbcolumn name in double quotes
                                        studentname[i] = u.getString("name");
                                    }


                                }
                                ArrayAdapter ad_stud = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, studentname);
//                                ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                sp_stud.setAdapter(ad_stud);

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



    public void system_load(){
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        url = sh.getString("url", "") + "/and_system_load";


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

                                systemid = new String[js.length()+1];
                                systemname = new String[js.length()+1];


                                for (int i = 0; i < js.length()+1; i++) {
                                    if(i==0){
                                        systemid[i] = "Select system";
                                        systemname[i] = "Select system";
                                    }
                                    else
                                    {
                                        JSONObject u = js.getJSONObject(i-1);
                                        systemid[i] = u.getString("sys_id");//dbcolumn name in double quotes
                                        systemname[i] = u.getString("lab_name") + " - " + u.getString("sys_name");

                                    }


                                }
                                ArrayAdapter ad_sys = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, systemname);
//                                ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                sp_sys.setAdapter(ad_sys);

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
                params.put("lid", sh.getString("lid", ""));//passing to python
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
        setContentView(R.layout.activity_lab_exam_system_allocation);

        sp_course=findViewById(R.id.spinner);
        sp_subject=findViewById(R.id.spinner2);
        sp_sem=findViewById(R.id.spinner3);
        sp_stud=findViewById(R.id.spinner4);
        sp_sys=findViewById(R.id.spinner5);
        b=findViewById(R.id.button15);
        b.setOnClickListener(this);

        ArrayAdapter <String> ad =new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, semester);
        sp_sem.setAdapter(ad);

        sp_course.setOnItemSelectedListener(this);
        sp_sem.setOnItemSelectedListener(this);
        sp_subject.setOnItemSelectedListener(this);
        sp_stud.setOnItemSelectedListener(this);
        sp_sys.setOnItemSelectedListener(this);

        course_load();
        system_load();


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView == sp_sem){
            sem = semester[i];
            student_load();
            exam_load();
        }
        if(adapterView == sp_course){
            c_id = courseid[i];
            student_load();
            exam_load();
        }

        if(adapterView == sp_subject){
            examid =exam_id[i];
        }

        if(adapterView == sp_stud){
            stu_id = studentid[i];
        }

        if(adapterView == sp_sys){
            sy_id = systemid[i];
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        url = sh.getString("url", "") + "/and_insert_system_allocation";


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            if (jsonObj.getString("status").equalsIgnoreCase("ok")) {

                                Toast.makeText(lab_exam_system_allocation.this, "System allocated", Toast.LENGTH_SHORT).show();
                                Intent ij =new Intent(getApplicationContext(), view_exam_arrangement.class);
                                startActivity(ij);
                            } else if (jsonObj.getString("status").equalsIgnoreCase("sys")) {
                                Toast.makeText(getApplicationContext(), "System already allocated to another student", Toast.LENGTH_LONG).show();
                            } else if (jsonObj.getString("status").equalsIgnoreCase("stud")) {
                                Toast.makeText(getApplicationContext(), "System already allocated for this student", Toast.LENGTH_LONG).show();
                            } else if (jsonObj.getString("status").equalsIgnoreCase("both")) {
                                Toast.makeText(getApplicationContext(), "System and student previously allocated", Toast.LENGTH_LONG).show();
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
                params.put("examid", examid);//passing to python
                params.put("studid", stu_id);//passing to python
                params.put("sysid", sy_id);//passing to python
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