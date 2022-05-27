package com.example.fooddelivery.model;

public class Order {
     String Name;
     String price;
     String numberOrder;
     int imageCart;
     int total;
     String documentID;

    public Order(String name, String price, String numberOrder, int total,int imageCart,String documentID) {
        Name = name;
        this.price = price;
        this.numberOrder = numberOrder;
        this.total = total;
        this.imageCart=imageCart;
        this.documentID=documentID;
    }

    public Order() {

    }

    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    public int getImageCart() {
        return imageCart;
    }

    public void setImageCart(int imageCart) {
        this.imageCart = imageCart;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNumberOrder() {
        return numberOrder;
    }

    public void setNumberOrder(String numberOrder) {
        this.numberOrder = numberOrder;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
