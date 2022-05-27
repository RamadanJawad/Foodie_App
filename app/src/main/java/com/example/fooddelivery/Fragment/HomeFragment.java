package com.example.fooddelivery.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.fooddelivery.Adapter.FoodAdapter;
import com.example.fooddelivery.Adapter.FoodCategoryAdapter;
import com.example.fooddelivery.R;
import com.example.fooddelivery.UI.LoginIn;
import com.example.fooddelivery.model.FoodCategory;
import com.example.fooddelivery.model.FoodItem;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import maes.tech.intentanim.CustomIntent;
import nl.joery.animatedbottombar.AnimatedBottomBar;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    private EditText search;
    private RecyclerView recyclerView1,recyclerView2;
    private ArrayList<FoodCategory>categories;
    private FoodCategoryAdapter adapter;
    private ArrayList<FoodItem>foodItems;
    private FoodAdapter foodAdapter;
    private ImageSlider slider;
    private ArrayList<SlideModel>models;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView1=view.findViewById(R.id.rec_category);
        recyclerView2=view.findViewById(R.id.rec_food);
        search=view.findViewById(R.id.et_search);
        slider=view.findViewById(R.id.image_slider);
        models=new ArrayList<>();
        models.add(new SlideModel(R.drawable.slid1, ScaleTypes.FIT));
        models.add(new SlideModel(R.drawable.slid2, ScaleTypes.FIT));
        models.add(new SlideModel(R.drawable.slid3, ScaleTypes.FIT));
        models.add(new SlideModel(R.drawable.slid4, ScaleTypes.FIT));
        models.add(new SlideModel(R.drawable.slid5, ScaleTypes.FIT));
        models.add(new SlideModel(R.drawable.slid6, ScaleTypes.FIT));
        slider.setImageList(models,ScaleTypes.FIT);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        setItemCategory();
        setItemFood(0);
    }
    private void filter(String text) {
        ArrayList<FoodItem>Items=new ArrayList<>();
        for(FoodItem item:foodItems){
            if(item.getName().toLowerCase().contains(text.toLowerCase())){
                Items.add(item);
            }
        }
        foodAdapter.filterList(Items);
    }
    private void setItemFood(int position){
        foodItems=new ArrayList<>();
        switch (position){
            case 3:
                foodItems.add(new FoodItem("Tea",2,R.drawable.tea,R.string.description2,"2-3",10,"1"));
                foodItems.add(new FoodItem("Coffee",3,R.drawable.coffee,R.string.description2,"4",15,"2"));
                foodItems.add(new FoodItem("Natural Juices",5,R.drawable.natural_juices,R.string.description2,"4-5",50,"3"));
                foodItems.add(new FoodItem("CocaCola",5,R.drawable.coca_cola,R.string.description2,"1",17,"4"));
                break;
            case 2:
                foodItems.add(new FoodItem("Vegetable Pizza",10,R.drawable.pizza1,R.string.description2,"10-15",80,"5"));
                foodItems.add(new FoodItem("Chicken Pizza",13,R.drawable.pizza2,R.string.description2,"10-15",70,"6"));
                foodItems.add(new FoodItem("Corn Pizza",11,R.drawable.pizza3,R.string.description2,"10-15",59,"7"));
                foodItems.add(new FoodItem("Meat Pizza",12,R.drawable.pizza4,R.string.description2,"10-15",120,"8"));
                break;
            case 1:
                foodItems.add(new FoodItem("Broasted",18,R.drawable.broasted,R.string.description2,"15-17",67,"9"));
                foodItems.add(new FoodItem("Fillet",25,R.drawable.filet,R.string.description2,"12-15",80,"10"));
                foodItems.add(new FoodItem("Chicken Steak",27,R.drawable.grill_chicken_2,R.string.description2,"20",122,"11"));
                foodItems.add(new FoodItem("Grills",30,R.drawable.grills,R.string.description2,"15-20",180,"12"));
                break;
            case 0:
                foodItems.add(new FoodItem("Burger",5,R.drawable.burger11,R.string.description2,"4-5",145,"13"));
                foodItems.add(new FoodItem("Shawarma",7,R.drawable.shawerma,R.string.description2,"4-5",145,"14"));
                foodItems.add(new FoodItem("Kebab",15,R.drawable.kabab,R.string.description2,"4-5",145,"15"));
                foodItems.add(new FoodItem("Shish Tawook",12,R.drawable.shish_tawouk,R.string.description2,"4-5",145,"16"));
                foodItems.add(new FoodItem("Golden Pie",14,R.drawable.food5,R.string.description2,"4-5",145,"17"));
                foodItems.add(new FoodItem("Calzone",16,R.drawable.calzone,R.string.description2,"4-5",145,"18"));
                break;
        }
        foodAdapter=new FoodAdapter(foodItems);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        recyclerView2.setAdapter(foodAdapter);
        foodAdapter.notifyDataSetChanged();
    }
    private void setItemCategory(){
        categories=new ArrayList<>();
        categories.add(new FoodCategory(R.string.category1,R.drawable.hamburger));
        categories.add(new FoodCategory(R.string.category2,R.drawable.ic_chicken));
        categories.add(new FoodCategory(R.string.category3,R.drawable.ic_pizza));
        categories.add(new FoodCategory(R.string.category4,R.drawable.drink));
        adapter=new FoodCategoryAdapter(categories, getContext(), new FoodCategoryAdapter.OnCategoryClick() {
            @Override
            public void onClick(int pos) {
                setItemFood(pos);
            }
        });
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        recyclerView1.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}