package com.example.vegcart.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vegcart.R;
import com.example.vegcart.models.Util;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {
    TextInputEditText Name, Email, Phone, Password, City;
    Spinner SpinnerArea;
    ArrayList<String> AreaList;
    StringRequest stringRequest;
    String SelectedSpinnerPincode;
    RequestQueue requestQueue;
    TextView Area, AlreadyUser;
    Button CreateAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Name = findViewById(R.id.name);
        Email = findViewById(R.id.email);
        Phone = findViewById(R.id.phone);
        Password = findViewById(R.id.password);
        City = findViewById(R.id.city);
        CreateAccount = findViewById(R.id.createAccount);
        AlreadyUser = findViewById(R.id.alreadyUser);
        AlreadyUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this, SignIn.class));
            }
        });
        CreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewUser();
            }
        });
        SpinnerArea = findViewById(R.id.spinnerArea);
        AreaList = new ArrayList<>();
        AreaList.add("--Select Area--");
        SpinnerArea.setAdapter(new ArrayAdapter<>(SignUp.this,R.layout.support_simple_spinner_dropdown_item,
                AreaList));
        requestQueue = Volley.newRequestQueue(this);
        FetchArea();

        SpinnerArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SelectedSpinnerPincode = SpinnerArea.getItemAtPosition(SpinnerArea.getSelectedItemPosition()).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void FetchArea() {
         stringRequest = new StringRequest(Request.Method.POST, Util.Fetch_Area, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray ja = new JSONArray(response);
                    if(ja.length()>0) {

                        int k = ja.length();


                        for (int i = 0; i < k; i++) {
                            JSONObject jo = ja.getJSONObject(i);
                            AreaList.add(jo.getString("Area"));
                            SpinnerArea.setAdapter(new ArrayAdapter<>(SignUp.this,
                                    R.layout.support_simple_spinner_dropdown_item, AreaList));

                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(SignUp.this, "Some exeception", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

//                Toast.makeText(MainActivity.this, "Some error", Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(stringRequest);

    }



    void CreateNewUser(){
        stringRequest =new StringRequest(Request.Method.POST, Util.New_User,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("resss",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.i("jobj",response);
                            String message = jsonObject.getString("message");
                            Log.i("msg",message);
                            if (message.contains("Record Inserted Sucessfully")){
                                Toast.makeText(SignUp.this, "Record insert",
                                        Toast.LENGTH_SHORT).show();
                                //finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(SignUp.this, "some exception " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SignUp.this, "some error "+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("NAME",Name.getText().toString());
                Log.i("NAME",Name.getText().toString());
                map.put("EMAIL",Email.getText().toString());
                Log.i("EMAIL",Email.getText().toString());
                map.put("PHONE",Phone.getText().toString());
                Log.i("PHONE",Phone.getText().toString());
                map.put("PASSWORD",Password.getText().toString());
                Log.i("PASSWORD",Password.getText().toString());
                map.put("AREASELECTED",SelectedSpinnerPincode);
                Log.i("AREASELECTED",SelectedSpinnerPincode);
                return map;
            }
        };
        requestQueue.add(stringRequest);

    }
}