package com.example.fooddelivery.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fooddelivery.R;
import com.example.fooddelivery.UI.ShowDetails;
import com.example.fooddelivery.model.Favorite;
import com.example.fooddelivery.model.FoodCategory;
import com.example.fooddelivery.model.FoodItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import static android.content.Context.MODE_PRIVATE;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodHolder>{

    ArrayList<FoodItem> foodItems;
    Context context;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    private FirebaseDatabase database;

    public FoodAdapter(ArrayList<FoodItem> foodItems) {
        this.foodItems = foodItems;
    }

    @NonNull
    @Override
    public FoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.food_holder,parent,false);
        return new FoodHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull FoodHolder holder, int position) {
    FoodItem item=foodItems.get(position);
    holder.title.setText(item.getName());
    holder.price.setText(String.format("$%d",item.getPrice()));
    holder.type.setImageResource(item.getImage());

    holder.add.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(holder.itemView.getContext(), ShowDetails.class);
            intent.putExtra("object",item);
            holder.itemView.getContext().startActivity(intent);
        }
    });
    isLiked(item.getIdFood(),holder.favorite);
    holder.favorite.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(holder.favorite.getTag().equals("like")){
                FirebaseDatabase.getInstance().getReference().child("likes").child(item.getIdFood()).child(firebaseUser.getUid())
                        .setValue(true);
                            database=FirebaseDatabase.getInstance();
                            String uid=firebaseUser.getUid();
                            reference=database.getReference().child("FavoriteList").child(item.getIdFood()).child(uid);
                            final HashMap<String, Object> map = new HashMap<>();
                            map.put("NameFav", item.getName());
                            map.put("priceFav",item.getPrice());
                            map.put("imageFav",item.getImage());
                            reference.setValue(map);
            }else{
                FirebaseDatabase.getInstance().getReference().child("likes").child(item.getIdFood()).child(firebaseUser.getUid())
                        .removeValue();
                FirebaseDatabase.getInstance().getReference().child("FavoriteList").child(item.getIdFood()).child(firebaseUser.getUid())
                        .removeValue();
            }
        }
    });
  }
    @Override
    public int getItemCount() {
        return foodItems.size();
    }

    public void filterList(ArrayList<FoodItem> items) {
        foodItems=items;
        notifyDataSetChanged();
    }

    class FoodHolder extends RecyclerView.ViewHolder{
        TextView price,title;
        CircleImageView type;
        ImageView add,favorite;
        public FoodHolder(@NonNull View itemView) {
            super(itemView);
            price=itemView.findViewById(R.id.food_price);
            title=itemView.findViewById(R.id.food_title);
            type=itemView.findViewById(R.id.food_type);
            add=itemView.findViewById(R.id.food_add);
            favorite=itemView.findViewById(R.id.food_fav);
        }
    }
    private void isLiked(String id,ImageView image){
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference()
                .child("likes").child(id);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(firebaseUser.getUid()).exists()){
                    image.setImageResource(R.drawable.favorite_24);
                    image.setTag("liked");
                }else{
                    image.setImageResource(R.drawable.favorite_25);
                    image.setTag("like");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
