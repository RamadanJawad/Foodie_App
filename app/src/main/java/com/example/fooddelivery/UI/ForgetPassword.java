package com.example.fooddelivery.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fooddelivery.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {
    Dialog dialog;
    private MaterialButton btn_retry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        ImageView back = findViewById(R.id.back_icon);
        MaterialButton rest = findViewById(R.id.rest_pass);
        EditText editText = findViewById(R.id.textInputLayout);

        if(!isConnected()){
            dialog=new Dialog(this);
            dialog.setContentView(R.layout.internet_cutom);
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

        back.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), LoginIn.class);
            startActivity(intent);
        });
        rest.setOnClickListener(view -> {
            String email = editText.getText().toString();
            if (email.isEmpty()) {
                editText.setError("Email is Required");
                editText.requestFocus();
            } else {
                auth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });
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