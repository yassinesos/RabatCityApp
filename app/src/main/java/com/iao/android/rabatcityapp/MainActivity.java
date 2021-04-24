package com.iao.android.rabatcityapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.iao.android.rabatcityapp.adapters.RecyclerAdapter;
import com.iao.android.rabatcityapp.models.Hotel;
import com.iao.android.rabatcityapp.models.modelInterface;
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
    ImageView popupImage;
    private RecyclerView mRecyclerView;
    private List<? extends modelInterface> itemLists;

    private RecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressBar progressBar;


    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initUI
        initUI();

        //intialization of retrofit Apiclient
        getDataFromApi();

        // utilisation de searchView pour le filtrage
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

        // popup menu pour le logout
        popupMenu();

        //initialisation des animations
        initAnimations();
    }

    private void initAnimations() {
        //Load Animations
        anim_from_left = AnimationUtils.loadAnimation(this, R.anim.anim_from_left);
        //Set Animations
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

    private void getDataFromApi() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<Hotel>> call = apiService.getHotels();
        call.enqueue(new Callback<List<Hotel>>() {
            @Override
            public void onResponse(Call<List<Hotel>> call, Response<List<Hotel>> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    itemLists = response.body();
                    for (int i = 0; i < itemLists.size(); i++) {
                        mAdapter = new RecyclerAdapter(MainActivity.this, itemLists);

                        // below line is to set layout manager for our recycler view.
                        layoutManager = new LinearLayoutManager(MainActivity.this);

                        // setting layout manager for our recycler view.
                        mRecyclerView.setLayoutManager(layoutManager);

                        // below line is to set adapter to our recycler view.
                        mRecyclerView.setAdapter(mAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Hotel>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext() , "Check your internet", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initUI() {
        progressBar = findViewById(R.id.idPBLoading);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        searchView = findViewById(R.id.searchView);
        popupImage = findViewById(R.id.dotsMenu);
    }

    private void popupMenu() {
        popupImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(MainActivity.this, v, Gravity.RIGHT);
                popup.inflate(R.menu.popup_menu);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if( item.getItemId() == R.id.logout){
                            //logout code
                            SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("first", "false");
                            editor.apply();
                            // if sign-in is successful
                            // intent to home acti
                            Intent intent= new Intent(MainActivity.this,
                                    LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });
    }


}
