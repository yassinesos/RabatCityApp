package com.iao.android.rabatcityapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.iao.android.rabatcityapp.adapters.RecyclerAdapter;
import com.iao.android.rabatcityapp.models.Hotel;
import com.iao.android.rabatcityapp.services.ApiClient;
import com.iao.android.rabatcityapp.services.ApiService;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    SearchView searchView;
    Animation  anim_from_left;

    private RecyclerView mRecyclerView;
    private List<Hotel> hotelLists;

    private RecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressBar progressBar;
    private static final String TAG = "MainActivity";

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.idPBLoading);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager


        //intialization of retrofit Apiclient
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<Hotel>> call = apiService.getHotels();
        call.enqueue(new Callback<List<Hotel>>() {
            @Override
            public void onResponse(Call<List<Hotel>> call, Response<List<Hotel>> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    hotelLists = response.body();
                    for (int i = 0; i < hotelLists.size(); i++) {
                        mAdapter = new RecyclerAdapter(MainActivity.this, hotelLists);

                        // below line is to set layout manager for our recycler view.
                        layoutManager = new LinearLayoutManager(MainActivity.this);

                        // setting layout manager for our recycler view.
                        mRecyclerView.setLayoutManager(layoutManager);

                        // below line is to set adapter to our recycler view.
                        mRecyclerView.setAdapter(mAdapter);
                    }
                    Log.d("TAG", "Response = " + hotelLists);
                }
            }

            @Override
            public void onFailure(Call<List<Hotel>> call, Throwable t) {
                Log.d("TAG","Response = "+t.toString());
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext() , "Check your internet", Toast.LENGTH_LONG).show();
            }
        });

        // utilisation de searchView pour le filtrage
         searchView = findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });
        //Load Animations
//        anim_from_button = AnimationUtils.loadAnimation(this, R.anim.anim_from_bottom);
//        anim_from_top = AnimationUtils.loadAnimation(this, R.anim.anim_from_top);
         anim_from_left = AnimationUtils.loadAnimation(this, R.anim.anim_from_left);
        //Set Animations
//        textView5.setAnimation(anim_from_top);
          searchView.setAnimation(anim_from_left);


        //Hide status bar and navigation bar at the bottom
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
   }


}
