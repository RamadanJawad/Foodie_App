package com.example.fooddelivery.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import com.example.fooddelivery.Adapter.MyFragmentAdapter;
import com.example.fooddelivery.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;

public class LoginIn extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager;
    MyFragmentAdapter adapter;
    Dialog dialog;
    private MaterialButton btn_retry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_in);

        tabLayout=findViewById(R.id.tab_layout);
        viewPager=findViewById(R.id.view_pager);
        tabLayout=findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Sign In"));
        tabLayout.addTab(tabLayout.newTab().setText("Sign Up"));

        FragmentManager fragmentManager=getSupportFragmentManager();
        adapter=new MyFragmentAdapter(fragmentManager,getLifecycle());
        viewPager.setAdapter(adapter);

        if(!isConnected()){
            dialog=new Dialog(this);
            dialog.setContentView(R.layout.internet_custom);
            dialog.getWindow().setBackgroundDrawableResource(
                    android.R.color.transparent);
            btn_retry=dialog.findViewById(R.id.btn_retry);
            btn_retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                }
            });
            dialog.show();
        }


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

    }


    private boolean isConnected(){
        ConnectivityManager cm=(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network=cm.getActiveNetworkInfo();
        if(network==null||!network.isAvailable()||!network.isConnected()){
            return  false;
        }
        return true;
    }
}