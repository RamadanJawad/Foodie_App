package com.example.fooddelivery.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooddelivery.R;
import com.example.fooddelivery.model.Order;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.FoodHolder>{

    ArrayList<Order> foodOrders;
    Context context;
    int totalPrice=0;

    public OrderAdapter(ArrayList<Order> foodOrders) {
        this.foodOrders = foodOrders;
    }

    @NonNull
    @Override
    public FoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.order_card,parent,false);
        return new FoodHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodHolder holder, int position) {
    Order order=foodOrders.get(position);
    holder.type.setImageResource(order.getImageCart());
    holder.name.setText(order.getName());
    holder.price.setText(order.getPrice());
    holder.totalQua.setText(order.getNumberOrder());
    totalPrice=totalPrice+order.getTotal();
    Intent intent=new Intent("MyTotalAmount");
    intent.putExtra("totalAmount",totalPrice);
    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
}
    @Override
    public int getItemCount() {
        return foodOrders.size();
    }

    class FoodHolder extends RecyclerView.ViewHolder{
        private TextView name,price,totalQua;
        private RoundedImageView type;
            public FoodHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.cart_foodName);
            price=itemView.findViewById(R.id.cart_price);
            totalQua=itemView.findViewById(R.id.cart_total);
            type=itemView.findViewById(R.id.cart_image);
        }
    }
}
