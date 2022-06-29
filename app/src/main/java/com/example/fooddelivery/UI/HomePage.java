package com.example.fooddelivery.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainer;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.fooddelivery.Adapter.FoodAdapter;
import com.example.fooddelivery.Adapter.FoodCategoryAdapter;
import com.example.fooddelivery.Fragment.FavoriteFragment;
import com.example.fooddelivery.Fragment.HomeFragment;
import com.example.fooddelivery.Fragment.ProfileFragment;
import com.example.fooddelivery.Fragment.ShopFragment;
import com.example.fooddelivery.R;
import com.example.fooddelivery.model.FoodCategory;
import com.example.fooddelivery.model.FoodItem;
import com.example.fooddelivery.model.Order;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class HomePage extends AppCompatActivity {

    private ChipNavigationBar navigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        navigationBar=findViewById(R.id.bottom_menu);
        HomeFragment homeFragment=new HomeFragment();
        FragmentManager manager=getSupportFragmentManager();
        manager.
                beginTransaction().
                replace(R.id.fragment_container,homeFragment).
                commit();
        navigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment=null;
                switch (i){
                    case R.id.home:
                        fragment=new HomeFragment();
                        break;
                    case R.id.profile:
                        fragment=new ProfileFragment();
                        break;
                    case R.id.shop:
                        fragment=new ShopFragment();
                        navigationBar.showBadge(R.id.shop);

                        break;
                    case R.id.favorites:
                        fragment=new FavoriteFragment();
                        break;
//                    case R.id.settings:
//                        fragment=new SettingFragment();
//                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            }
        });
    }
}
