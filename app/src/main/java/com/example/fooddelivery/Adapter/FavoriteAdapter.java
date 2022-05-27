package com.example.fooddelivery.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooddelivery.R;
import com.example.fooddelivery.UI.LoginIn;
import com.example.fooddelivery.model.Favorite;
import com.example.fooddelivery.model.FoodItem;
import com.example.fooddelivery.model.Order;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FoodHolder>{

    ArrayList<Favorite> favorites;
    Context context;

    public FavoriteAdapter(ArrayList<Favorite> favorites) {
        this.favorites = favorites;
    }

    @NonNull
    @Override
    public FoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.fav_item,parent,false);
        return new FoodHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodHolder holder, int position) {
    Favorite favorite=favorites.get(position);
    holder.name.setText(favorite.getNameFav());
    holder.price.setText(favorite.getPriceFav());
    holder.image.setImageResource(favorite.getImageFav());
}

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    class FoodHolder extends RecyclerView.ViewHolder{
        private TextView name,price;
        private ImageView image;
            public FoodHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.fav_title);
            price=itemView.findViewById(R.id.fav_price);
            image=itemView.findViewById(R.id.fav_image);
        }
    }
}
