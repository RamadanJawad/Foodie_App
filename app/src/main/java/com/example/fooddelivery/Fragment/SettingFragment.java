package com.example.fooddelivery.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.fooddelivery.R;
import com.example.fooddelivery.UI.LoginIn;
import com.example.fooddelivery.UI.OnboardingScreen;
import com.google.firebase.auth.FirebaseAuth;

import maes.tech.intentanim.CustomIntent;

import static android.content.Context.MODE_PRIVATE;

public class SettingFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }
    private ImageView logout;
    private SharedPreferences preferences;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logout=view.findViewById(R.id.logout);
        preferences=getActivity().getSharedPreferences("data",MODE_PRIVATE);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor=preferences.edit();
                editor.clear();
                editor.apply();
                startActivity(new Intent(getActivity(),LoginIn.class));
                CustomIntent.customType(getActivity(),"right-to-left");
                getActivity().finish();
            }
        });
        }
}