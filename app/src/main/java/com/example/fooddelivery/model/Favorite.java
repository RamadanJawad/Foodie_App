package com.example.fooddelivery.model;

public class Favorite {
    private int imageFav;
    private String NameFav;
    private String priceFav;

    public Favorite(int imageFav, String nameFav, String priceFav) {
        this.imageFav = imageFav;
        this.NameFav = nameFav;
        this.priceFav = priceFav;
    }

    public Favorite() {

    }

    public int getImageFav() {
        return imageFav;
    }

    public void setImageFav(int imageFav) {
        this.imageFav = imageFav;
    }

    public String getNameFav() {
        return NameFav;
    }

    public void setNameFav(String nameFav) {
        NameFav = nameFav;
    }

    public String getPriceFav() {
        return priceFav;
    }

    public void setPriceFav(String priceFav) {
        this.priceFav = priceFav;
    }
}
