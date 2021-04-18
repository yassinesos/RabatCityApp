package com.iao.android.rabatcityapp;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.iao.android.rabatcityapp.models.Hotel;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    FusedLocationProviderClient client;
    SupportMapFragment mapFragment;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
         mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        i = getIntent();

        mapFragment.getMapAsync(this);
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
         mMap = googleMap;
//        // Add a marker in Sydney and move the camera
        i = getIntent();
        Double lat = i.getDoubleExtra("lat",0);
        Double lon = i.getDoubleExtra("lon",0);
        Hotel hotel = (Hotel)i.getSerializableExtra("hotelMap");
        LatLng latLang = new LatLng(lat,lon);
        LatLng latLangHotel = new LatLng(hotel.getCoordinate().getLat(),hotel.getCoordinate().getLon());

        MarkerOptions options = new MarkerOptions().position(latLang).title("Your Location");
        MarkerOptions optionsHotel = new MarkerOptions().position(latLangHotel).title(hotel.getName());

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLang, 10));
        mMap.addMarker(options);
        mMap.addMarker(optionsHotel);
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
