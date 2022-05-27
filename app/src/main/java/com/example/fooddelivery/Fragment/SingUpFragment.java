package com.example.fooddelivery.Fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fooddelivery.R;
import com.example.fooddelivery.UI.HomePage;
import com.example.fooddelivery.UI.LoginIn;
import com.example.fooddelivery.UI.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class SingUpFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sing_up, container, false);
    }

    ProgressDialog dialog;
    private FirebaseFirestore firestore;
    private DatabaseReference reference;
    private FirebaseAuth firebaseAuth;
    private EditText name,email,pass,mobile_number;
    private  MaterialButton btn_SignUp,btn_retry;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        name=view.findViewById(R.id.edit_UserName);
        email=view.findViewById(R.id.edit_Email);
        pass=view.findViewById(R.id.edit_Password);
        mobile_number=view.findViewById(R.id.edit_mobilePhone);
        btn_SignUp=view.findViewById(R.id.btn_signUp);
        firebaseAuth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        btn_SignUp.setOnClickListener(view1 -> {
            String username=name.getText().toString();
            String email_address=email.getText().toString();
            String password=pass.getText().toString();
            String number=mobile_number.getText().toString();

            if(username.isEmpty()){
                name.setError("Username is required");
                name.requestFocus();
            }else if(email_address.isEmpty()){
                email.setError("Email is required");
                email.requestFocus();
            }else if (password.isEmpty()) {
                pass.setError("Password is required");
                pass.requestFocus();
            }else if (number.isEmpty()){
                mobile_number.setError("Mobile Number is required");
                mobile_number.requestFocus();
            }else{
                firebaseAuth.createUserWithEmailAndPassword(email_address, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        dialog=new ProgressDialog(getContext());
                        dialog.show();
                        dialog.setContentView(R.layout.progress_custom);
                        dialog.getWindow().setBackgroundDrawableResource(
                                android.R.color.transparent);
                        FirebaseUser user1 =firebaseAuth.getCurrentUser();
                        if(user1!=null){
                            String uid=user1.getUid();
                            reference=FirebaseDatabase.getInstance().getReference().child("users").child(uid);
                            HashMap<String,Object>data=new HashMap<>();
                            data.put("uid",uid);
                            data.put("username",username);
                            data.put("email",email_address);
                            data.put("password",password);
                            data.put("number",number);
                            reference.setValue(data);
                            firestore.collection("users").add(data)
                                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                            Intent intent=new Intent(getContext(), HomePage.class);
                                            startActivity(intent);
                                        }
                                    });
                                }
                    } else{
                        if (!isConnected()){
                            dialog=new ProgressDialog(getContext());
                            dialog.show();
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
                        }else{
                            LayoutInflater inflater=getLayoutInflater();
                            View root=inflater.inflate(R.layout.custom_toast,(ViewGroup)view.findViewById(R.id.toastMassage) );
                            Toast toast=new Toast(getContext());
                            toast.setGravity(Gravity.BOTTOM,0,0);
                            toast.setDuration(Toast.LENGTH_LONG);
                            toast.setView(root);
                            toast.show();
                        }
                    }
                });
            }
        });
    }
    private boolean isConnected(){
        ConnectivityManager cm=(ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network=cm.getActiveNetworkInfo();
        return network!=null;
    }
}