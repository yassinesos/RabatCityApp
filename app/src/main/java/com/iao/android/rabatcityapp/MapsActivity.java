package com.iao.android.rabatcityapp;

import androidx.fragment.app.FragmentActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.iao.android.rabatcityapp.models.Hotel;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    SupportMapFragment mapFragment;
    Intent i;
    Button directions;
    Hotel hotel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        i = getIntent();
        hotel = (Hotel) i.getSerializableExtra("hotelMap");

        mapFragment.getMapAsync(this);

        directions = findViewById(R.id.directions_button);

        directions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+hotel.getCoordinate().getLat()+","+hotel.getCoordinate().getLon()+"&mode=w");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });


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
        Double lat = i.getDoubleExtra("lat", 0);
        Double lon = i.getDoubleExtra("lon", 0);
        LatLng latLang = new LatLng(lat, lon);
        LatLng latLangHotel = new LatLng(hotel.getCoordinate().getLat(), hotel.getCoordinate().getLon());

        MarkerOptions options = new MarkerOptions().position(latLang).title("Your Location");
        MarkerOptions optionsHotel = new MarkerOptions().position(latLangHotel).title(hotel.getName());

        mMap.addMarker(options);
        mMap.addMarker(optionsHotel);

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLang, 10));

    }

}
