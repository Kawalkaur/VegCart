package com.example.vegcart.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.leo.simplearcloader.ArcConfiguration;
import com.leo.simplearcloader.SimpleArcDialog;
import com.leo.simplearcloader.SimpleArcLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class SignIn extends AppCompatActivity {
    EditText Username, Password;
    Button SignIn;
    TextView ForgotPassword, NewUser;
    StringRequest stringRequest;
    RequestQueue requestQueue;
    int id;
    String name, email, pwd, contact,area;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Username = findViewById(R.id.username);
        Password = findViewById(R.id.password);
        SignIn = findViewById(R.id.btnsignIn);
        ForgotPassword = findViewById(R.id.forgot_password);
        NewUser = findViewById(R.id.newUser);
        NewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignIn.this, SignUp.class  ));
            }
        });
        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SimpleArcDialog mDialog = new SimpleArcDialog(SignIn.this);
                mDialog.setConfiguration(new ArcConfiguration(SignIn.this));

              //  mDialog.setTitle("Loading....");
                ArcConfiguration configuration = new ArcConfiguration(SignIn.this);
                configuration.setLoaderStyle(SimpleArcLoader.STYLE.COMPLETE_ARC);
                configuration.setText("Please wait..");

// Using this configuration with Dialog
                mDialog.setConfiguration(configuration);
                mDialog.setCancelable(false);
                mDialog.show();

                long delayInMills = 1000;
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        mDialog.dismiss();
                        loginDetails();

                    }
                },delayInMills);


/*// Using this configuration with ArcLoader
                mSimpleArcLoader.refreshArcLoaderDrawable(configuration);
                */
            }
        });
        requestQueue = Volley.newRequestQueue(this);
    }
    void loginDetails(){
        stringRequest = new StringRequest(Request.Method.POST, Util.Old_User,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("resp",response);

                        try {
                            JSONObject object = new JSONObject(response);
                            Log.i("objj", " "+object);

                            JSONArray ja  =object.getJSONArray("Student");
                            Log.i("jarr", " "+ja);

                            String message = object.getString("Message");
                            Log.i("msg",message);

                            if (message.contains("Successful")){
                                for(int i =0;i<ja.length();i++) {
                                    JSONObject jo1 = ja.getJSONObject(i);
                                    id =jo1.getInt("User_ID");
                                    Log.i("id",id+"");
                                    name = jo1.getString("User_Name");
                                    email =jo1.getString("User_Email");
                                    contact =jo1.getString("User_Phone");
                                    pwd = jo1.getString("User_Password");
                                    area = jo1.getString("User_Area");
                                }

                                sh =getSharedPreferences("UserDetails", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sh.edit();
                                editor.putInt("intid",id);
                                Log.i("putint", String.valueOf(id));
                                editor.putString("name", name);
                                Log.i("name",name);
                                editor.putString("email",email);
                                Log.i("email",email);
                                editor.putString("contact",contact);
                                editor.putString("password",pwd);
                                editor.putString("area",area);
                                editor.apply();
                                startActivity(new Intent(SignIn.this, MainActivity.class));
                            }
                            else {
                                Toast.makeText(SignIn.this, "no response", Toast.LENGTH_SHORT).show();
                                incorrectValues();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            incorrectValues();
                            //Toast.makeText(LoginActivity.this, "User not found ", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SignIn.this, "Server error "+error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.i("voll", ""+error.getMessage());
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>map =new HashMap<>();
                map.put("Phone", Username.getText().toString().trim());
                Log.i("Phone", Username.getText().toString().trim());
                map.put("EmailId", Username.getText().toString().trim());
                map.put("Password",Password.getText().toString().trim());
                Log.i("Password",Password.getText().toString().trim());
                return map;
            }
        };
        // stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }

    void incorrectValues(){

        AlertDialog alertDialog = new AlertDialog.Builder(
                SignIn.this).create();
        alertDialog.setTitle("Invalid Values");
        alertDialog.setMessage("Incorrect Username or  Password ");
        // alertDialog.setIcon(R.drawable.smile);
        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        alertDialog.show();
    }
    @Override
    protected void onResume() {
        super.onResume();
        sh =getSharedPreferences("UserDetails", MODE_PRIVATE);
        String n1 =sh.getString("contact", "");
        Username.setText(n1);
        Log.i("n1",n1);
        String p1 = sh.getString("password","");
        Password.setText(p1);
        String e1 = sh.getString("email","");
        Username.setText(e1);

    }
    }
