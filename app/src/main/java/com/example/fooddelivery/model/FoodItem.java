package com.example.fooddelivery.model;

import java.io.Serializable;

public class FoodItem implements Serializable {
    private String name;
    private int price;
    private int image;
    private  int description;
    private String time;
    private int calories;
    private String IdFood;

    public FoodItem() {
    }

    public FoodItem(String name, int price, int image, int description, String time, int calories,String IdFood) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.description = description;
        this.time = time;
        this.calories = calories;
        this.IdFood=IdFood;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getDescription() {
        return description;
    }

    public void setDescription(int description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String getIdFood() {
        return IdFood;
    }

    public void setIdFood(String idFood) {
        IdFood = idFood;
    }
}
