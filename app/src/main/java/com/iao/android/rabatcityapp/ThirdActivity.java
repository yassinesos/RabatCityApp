package com.iao.android.rabatcityapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iao.android.rabatcityapp.models.Hotel;

public class ThirdActivity extends AppCompatActivity {
    ImageView down_arrow, headerImage;
    ScrollView third_scrollview;
    Animation from_bottom;
    TextView thirdTitle, thirdRatingNumber,aboutText;
    RatingBar thirdRattingbar;
    Intent i;
    Hotel hotel;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);


        headerImage = findViewById(R.id.header_background);
        thirdTitle = findViewById(R.id.third_title);
        thirdRatingNumber = findViewById(R.id.third_rating_number);
        thirdRattingbar = findViewById(R.id.third_ratingbar);
        aboutText = findViewById(R.id.about_text);
        down_arrow = findViewById(R.id.down_arrow);
        third_scrollview = findViewById(R.id.third_scrillview);

        i = getIntent();
        hotel = (Hotel) i.getSerializableExtra("hotelActi3");

        thirdTitle.setText(hotel.getName());
        thirdRatingNumber.setText(String.valueOf(hotel.getStarRating()));
        thirdRattingbar.setRating(hotel.getStarRating());
        aboutText.setText(hotel.getAbout());
        Glide.with(headerImage.getContext())
                .load(hotel.getPhotos().get(0))
                .centerCrop()
                .placeholder(R.drawable.header_background)
                .into(headerImage);

        from_bottom = AnimationUtils.loadAnimation(this, R.anim.anim_from_bottom);
        down_arrow.setAnimation(from_bottom);
        third_scrollview.setAnimation(from_bottom);
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
        down_arrow.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThirdActivity.this, SecondActivity.class);
                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(down_arrow, "background_image_transition");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(ThirdActivity.this, pairs);
                intent.putExtra("hotel",hotel);
                startActivity(intent, options.toBundle());
            }
        });
    }
}