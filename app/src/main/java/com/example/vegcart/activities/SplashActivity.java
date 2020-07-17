package com.example.vegcart.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.vegcart.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handler.sendEmptyMessageDelayed(101, 3000);

    }
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 101) {
                Intent intent = new Intent(SplashActivity.this, SignIn.class);
                Bundle bd = new Bundle();
                bd.putString("code", "0");
                intent.putExtra("bd", bd);
                startActivity(intent);
                finish();
            }
        }
    };

}
