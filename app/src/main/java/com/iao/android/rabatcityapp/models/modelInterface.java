package com.iao.android.rabatcityapp.models;

import java.util.List;

public interface modelInterface {
     int getId();
     String getName();
     float getStarRating();
     String getAddress();
     String getPrice();
     String getAbout();
     List<String> getPhotos();
}
