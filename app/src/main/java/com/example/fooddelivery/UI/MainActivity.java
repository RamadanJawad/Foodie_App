package com.example.fooddelivery.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fooddelivery.R;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT=6000;
    private ImageView logo;
    float v=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logo=findViewById(R.id.logo1);
            new Handler().postDelayed(() -> {
                Intent intent=new Intent(getApplicationContext(),OnboardingScreen.class);
                startActivity(intent);
            },SPLASH_TIME_OUT);
            logo.setTranslationY(300);
            logo.setAlpha(v);
            logo.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        }
    }

