package com.example.test_assignment.SplashScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.test_assignment.AppPreferences;
import com.example.test_assignment.MainActivity;
import com.example.test_assignment.R;

public class SplashActivity extends AppCompatActivity {
    AppPreferences appPreferences;
    private static int SPLASH_TIME_OUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        appPreferences = new AppPreferences(SplashActivity.this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}