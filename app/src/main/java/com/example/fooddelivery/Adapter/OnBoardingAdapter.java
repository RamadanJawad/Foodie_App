package com.example.fooddelivery.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.fooddelivery.R;
import com.example.fooddelivery.model.onBoarding;

import java.util.ArrayList;
import java.util.List;

public class OnBoardingAdapter extends PagerAdapter {

    Context context;
    ArrayList<onBoarding> item;

    public OnBoardingAdapter(Context context, ArrayList<onBoarding> item) {
        this.context = context;
        this.item = item;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @Override
    @NonNull
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutScreen = inflater.inflate(R.layout.slide_layout,null);
        ImageView imgSlide = layoutScreen.findViewById(R.id.img);
        TextView title = layoutScreen.findViewById(R.id.tv1);
        TextView description = layoutScreen.findViewById(R.id.tv2);
        title.setText(item.get(position).getTitle());
        description.setText(item.get(position).getDescription());
        imgSlide.setImageResource(item.get(position).getImage());
        container.addView(layoutScreen);
        return layoutScreen;

    }
}
