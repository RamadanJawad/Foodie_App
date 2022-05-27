package com.example.fooddelivery.Fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fooddelivery.UI.ForgetPassword;
import com.example.fooddelivery.R;
import com.example.fooddelivery.UI.HomePage;
import com.example.fooddelivery.UI.LoginIn;
import com.example.fooddelivery.UI.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignInFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    ProgressDialog dialog;
    TextView forget;
    EditText email,pass;
    MaterialButton log,retry;
    boolean isRemembered;
    SharedPreferences preferences;
    CheckBox remember;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews();

        preferences=getActivity().getSharedPreferences("data",Context.MODE_PRIVATE);
        isRemembered=preferences.getBoolean("CHECKED",false);
        if(isRemembered){
            startActivity(new Intent(getContext(),HomePage.class));
            getActivity().finish();
        }
        FirebaseAuth firebase=FirebaseAuth.getInstance();
        forget.setOnClickListener(view1 -> {
            Intent intent=new Intent(getContext(), ForgetPassword.class);
            startActivity(intent);
        });
        log.setOnClickListener(view12 -> {
            String edit_email=email.getText().toString();
            String edit_pass=pass.getText().toString();
            boolean checked=remember.isChecked();
            SharedPreferences.Editor editor=preferences.edit();
            editor.putString("email",edit_email);
            editor.putString("password",edit_pass);
            editor.putBoolean("CHECKED",checked);
            editor.apply();
            if(edit_email.isEmpty()){
                email.setError("Email is required");
                email.requestFocus();
            }else if (edit_pass.isEmpty()){
                pass.setError("Password is required");
                pass.requestFocus();
            }else
                firebase.signInWithEmailAndPassword(edit_email,edit_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            dialog=new ProgressDialog(getContext());
                            dialog.show();
                            dialog.setContentView(R.layout.progress_custom);
                            dialog.getWindow().setBackgroundDrawableResource(
                                    android.R.color.transparent);
                            Intent intent=new Intent(getContext(), HomePage.class);
                            startActivity(intent);
                        }else{
                            if(!isConnected()){
                                dialog=new ProgressDialog(getContext());
                                dialog.show();
                                dialog.setContentView(R.layout.internet_cutom);
                                dialog.getWindow().setBackgroundDrawableResource(
                                        android.R.color.transparent);
                                retry=dialog.findViewById(R.id.btn_retry);
                                retry.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                                    }
                                });
                            }else{
                                dialog.dismiss();
                            }
                        }
                    }
                });
        });
    }
    private boolean isConnected(){
        ConnectivityManager cm=(ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network=cm.getActiveNetworkInfo();
        return network!=null;
    }
   private void initViews(){
       forget=getView().findViewById(R.id.textPassword);
       email=getView().findViewById(R.id.et_email);
       pass=getView().findViewById(R.id.et_password);
       log=getView().findViewById(R.id.btn_login);
       remember=getView().findViewById(R.id.checkbox_remember);
   }
}
