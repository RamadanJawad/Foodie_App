package com.example.fooddelivery.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fooddelivery.R;
import com.example.fooddelivery.UI.LoginIn;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

import maes.tech.intentanim.CustomIntent;

public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    private TextView text_user;
    private EditText edit_username,edit_email,edit_phone;
    private FirebaseFirestore firestore;
    private FirebaseAuth auth;
    private ImageView logout;
    private SharedPreferences preferences;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        text_user = view.findViewById(R.id.userName);
        edit_username=view.findViewById(R.id.ed_username);
        edit_email=view.findViewById(R.id.ed_email);
        edit_phone=view.findViewById(R.id.ed_phone);
        firestore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
        firestore.setFirestoreSettings(new FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).build());
        firestore.collection("users")
                .whereEqualTo("uid",auth.getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(QueryDocumentSnapshot snapshot:task.getResult()){
                            text_user.setText(snapshot.getData().get("username").toString());
                            edit_username.setText(snapshot.getData().get("username").toString());
                            edit_phone.setText(snapshot.getData().get("number").toString());
                            edit_email.setText(snapshot.getData().get("email").toString());
                        }
                    }
                });
        logout=view.findViewById(R.id.logout1);
        preferences=getActivity().getSharedPreferences("data",MODE_PRIVATE);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor=preferences.edit();
                editor.clear();
                editor.apply();
                startActivity(new Intent(getActivity(), LoginIn.class));
                CustomIntent.customType(getActivity(),"right-to-left");
                getActivity().finish();
            }
        });
    }
}
