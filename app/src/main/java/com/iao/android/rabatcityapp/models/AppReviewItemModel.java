package com.iao.android.rabatcityapp.models;

import java.io.Serializable;

public class AppReviewItemModel {
    public String comment;
    public String username;
    public float rating;
    public String date;

    public AppReviewItemModel(){};
    public AppReviewItemModel(String userName, String userReview, float rating, String date) {
        this.username = userName;
        this.comment = userReview;
        this.rating = rating;
        this.date = date;
    }

}