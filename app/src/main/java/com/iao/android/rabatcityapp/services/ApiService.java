package com.iao.android.rabatcityapp.services;

import com.iao.android.rabatcityapp.models.Hotel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    //https://run.mocky.io/v3/bd64871a-457f-40cc-ab09-494b0121b8c0
    @GET("v3/bd64871a-457f-40cc-ab09-494b0121b8c0")
    Call<List<Hotel>> getHotels();
}
