package com.example.fooddelivery.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.fooddelivery.Adapter.OrderAdapter;
import com.example.fooddelivery.R;
import com.example.fooddelivery.model.Order;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ShopFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shop, container, false);
    }

    private RecyclerView rec_order;
    private TextView total;
    private Button check;
    private OrderAdapter adapter;
    private ArrayList<Order> item;
    private FirebaseFirestore firestore;
    private FirebaseAuth auth;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        total = view.findViewById(R.id.tv_totalPrice);
        LocalBroadcastManager.getInstance(getActivity())
                .registerReceiver(receiver, new IntentFilter("MyTotalAmount"));
        rec_order = view.findViewById(R.id.rec_order);
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        rec_order.setLayoutManager(new LinearLayoutManager(getActivity()));
        item = new ArrayList<>();
        adapter = new OrderAdapter(item);
        rec_order.setAdapter(adapter);
        firestore.setFirestoreSettings(new FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).build());
        firestore.collection("Cart")
                .whereEqualTo("UidCart", auth.getCurrentUser().getUid())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot snapshot : task.getResult().getDocuments()) {
                        String documentID = snapshot.getId();
                        Order foodOrder = snapshot.toObject(Order.class);
                        foodOrder.setDocumentID(documentID);
                        item.add(foodOrder);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
    public BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int totalBill=intent.getIntExtra("totalAmount",0);
            total.setText("Total is :"+"$"+totalBill);
        }
    };
}