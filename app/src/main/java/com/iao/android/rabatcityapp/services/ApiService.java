package com.iao.android.rabatcityapp.services;

import com.iao.android.rabatcityapp.models.Hotel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    //https://run.mocky.io/v3/669927d5-7d34-4281-bf7b-f1b8c42363c7
    @GET("v3/669927d5-7d34-4281-bf7b-f1b8c42363c7")
    Call<List<Hotel>> getHotels();
}
