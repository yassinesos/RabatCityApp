package com.iao.android.rabatcityapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.iao.android.rabatcityapp.models.Coordinate;

import java.io.Serializable;
import java.util.List;

public class Hotel implements Serializable {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("starRating")
    @Expose
    private float starRating;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("coordinate")
    @Expose
    private Coordinate coordinate;
    @SerializedName("photos")
    @Expose
    private List<String> photos = null;
    @SerializedName("about")
    @Expose
    private String about;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Hotel withId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Hotel withName(String name) {
        this.name = name;
        return this;
    }

    public float getStarRating() {
        return starRating;
    }

    public void setStarRating(int starRating) {
        this.starRating = starRating;
    }

    public Hotel withStarRating(int starRating) {
        this.starRating = starRating;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Hotel withAddress(String address) {
        this.address = address;
        return this;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Hotel withPrice(String price) {
        this.price = price;
        return this;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Hotel withCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
        return this;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public Hotel withPhotos(List<String> photos) {
        this.photos = photos;
        return this;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Hotel withAbout(String about) {
        this.about = about;
        return this;
    }
}
