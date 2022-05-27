package com.example.fooddelivery.Adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooddelivery.R;
import com.example.fooddelivery.model.FoodCategory;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class FoodCategoryAdapter extends RecyclerView.Adapter<FoodCategoryAdapter.CategoryHolder> {
    ArrayList<FoodCategory>foodCategories;
    Context context;
    int selectedItem=0;
    OnCategoryClick category;
    public interface OnCategoryClick{
        void onClick(int pos);
    }

    public FoodCategoryAdapter(ArrayList<FoodCategory> foodCategories,Context context,OnCategoryClick category) {
        this.foodCategories = foodCategories;
        this.context=context;
        this.category=category;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.category_holder,parent,false);
        return new CategoryHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        FoodCategory category=foodCategories.get(position);
        holder.cate_name.setText(category.getName());
        holder.cate_image.setImageResource(category.getImage());
        if(position==selectedItem){
            holder.cardView.setOutlineSpotShadowColor(context.getColor(R.color.buttonColor));
            holder.cardView.setOutlineAmbientShadowColor(context.getColor(R.color.buttonColor));
            holder.cate_name.setTextColor(context.getColor(R.color.black));
            holder.cate_image.setColorFilter(ContextCompat.getColor(context,R.color.buttonColor), PorterDuff.Mode.SRC_IN);
            holder.cardView.setStrokeWidth(2);
        }else{
            holder.cardView.setOutlineSpotShadowColor(context.getColor(R.color.gray1));
            holder.cardView.setOutlineAmbientShadowColor(context.getColor(R.color.gray));
            holder.cate_name.setTextColor(context.getColor(R.color.gray));
            holder.cate_image.setColorFilter(ContextCompat.getColor(context,R.color.gray1), PorterDuff.Mode.SRC_IN);
            holder.cardView.setStrokeWidth(0);
        }
    }
    @Override
    public int getItemCount() {
        return foodCategories.size();
    }
    class CategoryHolder extends RecyclerView.ViewHolder{
        TextView cate_name;
        ImageView cate_image;
        MaterialCardView cardView;
        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            cate_name=itemView.findViewById(R.id.category_name);
            cate_image=itemView.findViewById(R.id.category_image);
            cardView=itemView.findViewById(R.id.cardView);
            cardView.setOnClickListener(view -> {
                selectedItem=getAdapterPosition();
                if(category!=null){
                    category.onClick(selectedItem);
                }
                notifyDataSetChanged();
            });
        }
    }
}
