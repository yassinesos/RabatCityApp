package com.iao.android.rabatcityapp.models;

import java.io.Serializable;

public class AppReviewItemModel {
    public String comment;
    public String username;
    private int userReviewRatings = 0;
    private String userReviewDate = null;
    private int userPicture = 0;

    public AppReviewItemModel(){};
    public AppReviewItemModel(String userName, String userReview, int userReviewRatings, String userReviewDate) {
        this.username = userName;
        this.comment = userReview;
        this.userReviewRatings = userReviewRatings;
        this.userReviewDate = userReviewDate;

    }


    public int getUserReviewRatings() {
        return userReviewRatings;
    }

    public void setUserReviewRatings(int userReviewRatings) {
        this.userReviewRatings = userReviewRatings;
    }

    public String getUserReviewDate() {
        return userReviewDate;
    }

    public void setUserReviewDate(String userReviewDate) {
        this.userReviewDate = userReviewDate;
    }

    public int getUserPicture() {
        return userPicture;
    }

    public void setUserPicture(int userPicture) {
        this.userPicture = userPicture;
    }
}