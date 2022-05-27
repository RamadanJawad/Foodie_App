package com.example.fooddelivery.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.fooddelivery.Adapter.OnBoardingAdapter;
import com.example.fooddelivery.R;
import com.example.fooddelivery.model.onBoarding;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import java.util.ArrayList;

public class OnboardingScreen extends AppCompatActivity {
    private ViewPager pager;
    private OnBoardingAdapter adapter;
    WormDotsIndicator indicator;
    private MaterialButton getStarted;
    TextView next;
    int position = 0;
    Animation btnAnim;
    private ArrayList<onBoarding>arrayList;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding_screen);

        indicator=findViewById(R.id.dots_indicator);
        pager=(ViewPager) findViewById(R.id.viewPager_OnBoarding);
        getStarted=findViewById(R.id.btn_start);
        next=findViewById(R.id.tv_skip);
        auth=FirebaseAuth.getInstance();
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_animation);
        FirebaseUser user=auth.getCurrentUser();
//        if(user!=null){
//            startActivity(new Intent(this,HomePage.class));
//        }
        arrayList=new ArrayList<>();
        arrayList.add(new onBoarding("Discover place near you",
                "We make it simple to find your food you crave for. Enter your address and let us do the rest",
                R.drawable.splash1));
        arrayList.add(new onBoarding("Order your favourite meals",
                "When you order, we’ii  hook you up with exclusive package, and special rewards",
                R.drawable.splash2));
        arrayList.add(new onBoarding("Instant delivery and payment",
                "We made food ordering fast , simple and free, no matter if you order online or by cash",
                R.drawable.splash3));
        adapter=new OnBoardingAdapter(this,arrayList);
        pager.setAdapter(adapter);
        indicator.setViewPager(pager);

        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivity = new Intent(getApplicationContext(),LoginIn.class);
                startActivity(mainActivity);
            }
        });
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int pos, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int pos) {
                pos=pager.getCurrentItem();
                if (pos == arrayList.size()-2) {
                    getStarted.setVisibility(View.INVISIBLE);
                }
                if (pos == arrayList.size()-1) {
                    getStarted.setVisibility(View.VISIBLE);
                    getStarted.setAnimation(btnAnim);
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                position = pager.getCurrentItem();
                if (position < arrayList.size()) {
                    position++;
                    pager.setCurrentItem(position);
                }
                if (position == arrayList.size()-1) {
                    getStarted.setVisibility(View.VISIBLE);
                    getStarted.setAnimation(btnAnim);
                }
            }
        });
    }
}