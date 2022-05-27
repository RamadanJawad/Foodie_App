package com.example.fooddelivery.UI;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fooddelivery.R;
import com.example.fooddelivery.model.FoodItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class ShowDetails extends AppCompatActivity {

    private ImageView back, add, sub, type, favorite;
    private TextView total, title, price, description, star, time, fie;
    private int numberOrder = 1;
    private int order = 0;
    private MaterialButton orderAdd;
    private FoodItem item;
    private FirebaseFirestore firestore;
    private FirebaseAuth auth;
    private DatabaseReference reference;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);

        favorite=findViewById(R.id.img_fav);
        orderAdd = findViewById(R.id.btn_add);
        back = findViewById(R.id.img_back);
        add = findViewById(R.id.img_add);
        type = findViewById(R.id.img_type);
        sub = findViewById(R.id.img_sub);
        total = findViewById(R.id.tv_total);
        title = findViewById(R.id.tv_title);
        price = findViewById(R.id.tv_price);
        description = findViewById(R.id.tv_description);
        star = findViewById(R.id.tv_star);
        time = findViewById(R.id.tv_time);
        fie = findViewById(R.id.tv_fire);
        database = FirebaseDatabase.getInstance();
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        item = (FoodItem) getIntent().getSerializableExtra("object");

        title.setText(item.getName());
        fie.setText("" + item.getCalories());
        time.setText(item.getTime());
        description.setText(item.getDescription());
        type.setImageResource(item.getImage());
        price.setText(String.format("$%d", item.getPrice()));
        total.setText(String.valueOf(numberOrder));
        order = item.getPrice() * numberOrder;

        back.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), HomePage.class)));
        add.setOnClickListener(view -> {
            numberOrder = numberOrder + 1;
            order = item.getPrice() * numberOrder;
            total.setText(String.valueOf(numberOrder));

        });
        sub.setOnClickListener(view -> {
            if (numberOrder > 1) {
                numberOrder = numberOrder - 1;
                order = item.getPrice() * numberOrder;
            }
            total.setText(String.valueOf(numberOrder));
        });

        orderAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCard();
            }
        });

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToFavorite();
            }
        });
    }

    private void addToCard() {
        FirebaseUser user1 = auth.getCurrentUser();
        if (user1 != null) {
            String uid = user1.getUid();
            final HashMap<String, Object> map = new HashMap<>();
            map.put("UidCart", uid);
            map.put("Name", item.getName());
            map.put("imageCart", item.getImage());
            map.put("price", price.getText().toString());
            map.put("numberOrder", total.getText().toString());
            map.put("total", order);
            firestore.collection("Cart")
                    .add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Toast.makeText(ShowDetails.this, "Added To a Cart", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void addToFavorite() {
        FirebaseUser user1 = auth.getCurrentUser();
        if (user1 != null) {
            String uid = user1.getUid();
            final HashMap<String, Object> data = new HashMap<>();
            data.put("UidFav", uid);
            data.put("NameFav", item.getName());
            data.put("imageFav", item.getImage());
            data.put("priceFav", price.getText().toString());
            firestore.collection("Favorite")
                    .add(data).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Toast.makeText(ShowDetails.this, "Added To a Favorite", Toast.LENGTH_SHORT).show();
                            favorite.setImageResource(R.drawable.favorite_24);
                            favorite.setTag("like");
                        }
                    });
        }
    }
}


