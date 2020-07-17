package com.example.vegcart.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.vegcart.R;

import java.util.zip.Inflater;

public class DetailActivity extends AppCompatActivity {
    ImageView Plus, Minus, BigImage;
    TextView showCount, BigVegName, BigVegPrice, IncreasePrice;
    double num = 0.0;
    TextView textCartItemCount;
    Button AddToCart;
    String pro_name, pro_price, img_url;
    SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Plus = findViewById(R.id.cart_plus_img);
        Minus = findViewById(R.id.cart_minus_img);
        showCount = findViewById(R.id.cart_product_quantity_tv);
        BigImage = findViewById(R.id.bigImage);
        BigVegName = findViewById(R.id.bigvegName);
        BigVegPrice = findViewById(R.id.bigvegPrice);
        AddToCart = findViewById(R.id.addToCart);
        textCartItemCount = findViewById(R.id.totalItemCount);
        AddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showCount.getText().toString().contains("0.0 kg")) {
                    Toast.makeText(DetailActivity.this, "Please add quantity", Toast.LENGTH_SHORT).show();
                    AddToCart.setBackgroundColor(Color.RED);
                    return;
                }
                AddToCart.setText("Added To Cart" );
                AddToCart.setBackgroundColor(getTheme().getResources().getColor(R.color.recovered));
            }
        });
        getData();
        setData();
        Plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = num + 0.5;
                showCount.setText(num + " kg");
            }
        });

        Minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num < 0) {
                    num = 0;
                    showCount.setText(num + " kg");
                }
                if (num > 0) {
                    num = num - 0.5;
                    showCount.setText(num + " kg");

                }
            }
        });
    }



    void getData(){
  pro_name = getIntent().getExtras().getString("veg1");
  pro_price = getIntent().getExtras().getString("price1");
  img_url = getIntent().getExtras().getString("image");
    }

    void setData(){

    BigVegName.setText(pro_name);

     BigVegPrice.setText(pro_price);

    Glide.with(this).load(img_url).into(BigImage);
    }

    public void NextToCart(View view) {
        startActivity(new Intent(DetailActivity.this, AddToCart.class));
    }
}
