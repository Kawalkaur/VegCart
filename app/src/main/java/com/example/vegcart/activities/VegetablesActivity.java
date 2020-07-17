package com.example.vegcart.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vegcart.adapters.MyAdapter;
import com.example.vegcart.R;
import com.example.vegcart.models.Gmart;
import com.example.vegcart.models.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VegetablesActivity extends AppCompatActivity {
    StringRequest stringRequest;
    RequestQueue requestQueue;
    String vegNameStr, vegPriceStr;
    List<Gmart> lstgmart;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    MyAdapter myAdapter;
    /* RecyclerView recyclerView;
    String s1[], s2[];
    int images [] = {R.drawable.cucumber,R.drawable.cauliflower,R.drawable.onion,R.drawable.broccoli
            ,R.drawable.cucumber,R.drawable.cucumber,R.drawable.cucumber,R.drawable.cucumber
            ,R.drawable.cucumber,R.drawable.cucumber};*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vegetables);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressBar = findViewById(R.id.progressbar);
        recyclerView = findViewById(R.id.recyclerView);
        lstgmart = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        myAdapter =new MyAdapter(this,lstgmart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);
        /*recyclerView = findViewById(R.id.recyclerView);
        s1 =getResources().getStringArray(R.array.VegetablesName);
        s2 = getResources().getStringArray(R.array.VegetablesPrice);
        MyAdapter myAdapter =new MyAdapter(this, s1, s2, images);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));*/
        vegetablesListMethod();
    }
 /*   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cart,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.cartSetting){
            startActivity(new Intent(VegetablesActivity.this, AddToCart.class));
        }
        return super.onOptionsItemSelected(item);
    }*/

     void vegetablesListMethod() {
        progressBar.setVisibility(View.VISIBLE);
        stringRequest = new StringRequest(Request.Method.POST, Util.VegList, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject JsonObject = new JSONObject(response);
                    Log.i("resFirP", response);
                    JSONArray jsonArray = JsonObject.getJSONArray("Student");
                    String message = JsonObject.getString("Message");
                    if (message.contains("Successful")) {
                        progressBar.setVisibility(View.GONE);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            Gmart gmart = new Gmart();
                            gmart.setProductID(jsonObject1.getString("VEG_ID"));
                            gmart.setProductName(jsonObject1.getString("Veg_Name"));
                            gmart.setProductNameHindi(jsonObject1.getString("Veg_Name_Hindi"));
                            /*gmart.setProduct_weight(jsonObject1.getString("Veg_Weight"));
                            gmart.setProductPrice(jsonObject1.getString("Veg_Price"));*/
                            gmart.setImage_url(jsonObject1.getString("img"));
                            lstgmart.add(gmart);
                            Log.i("gmart",lstgmart +"");

                        }
                    } else {
                        Toast.makeText(VegetablesActivity.this,
                                "Error", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressBar.setVisibility(View.GONE);
                }

myAdapter.notifyDataSetChanged();
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(VegetablesActivity.this, "Server Error",
                        Toast.LENGTH_SHORT).show();
            }
        }

        ){
          /*  @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("GetVegName", vegNameStr );
                map.put("GetVegPrice", vegPriceStr);
                map.put("GetImg",)
            return map;
            }*/
        };
         requestQueue = Volley.newRequestQueue(this);
         requestQueue.add(stringRequest);    }


}
