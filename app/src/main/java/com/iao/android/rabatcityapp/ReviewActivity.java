package com.iao.android.rabatcityapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.iao.android.rabatcityapp.adapters.ReviewsAdapter;
import com.iao.android.rabatcityapp.models.AppReviewItemModel;
import com.iao.android.rabatcityapp.models.Hotel;
import com.iao.android.rabatcityapp.models.User;

import java.util.ArrayList;
import java.util.Date;

public class ReviewActivity  extends AppCompatActivity {
    private RecyclerView appReviewsRecyclerView;
    private RatingBar ratingBar_Review;
    private TextView tv_user_name, tv_user_review_date, tv_user_review;
    private float ratingValue;
    private EditText commentValue;
    Intent i;
    Hotel hotel;
    Button publish;
    private ProgressBar progressBar;
    ArrayList<AppReviewItemModel> appReviewItemModel;
    private ReviewsAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    Animation from_bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        // initialisation de la recyclerView
        intRecyclerView();
        // initialisation des UI
        intilizeUI();
        // init animations
        initAnimations();
        // reterive des données depuis la base des données Firestore
        loadAppReviewsDataAndSetAdapter();
        // enregistrer des données depuis la base des données Firestore
        saveReviewFireStore();
    }

    private void initAnimations() {
        from_bottom = AnimationUtils.loadAnimation(this, R.anim.anim_from_bottom);
        commentValue.setAnimation(from_bottom);
        ratingBar_Review.setAnimation(from_bottom);
        publish.setAnimation(from_bottom);
        appReviewsRecyclerView.setAnimation(from_bottom);
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

    private void intRecyclerView() {
        appReviewsRecyclerView = findViewById(R.id.my_recycler_review);
        RecyclerView.LayoutManager verticalLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        appReviewsRecyclerView.setLayoutManager(verticalLayoutManager);
        appReviewsRecyclerView.setHasFixedSize(true);
        i = getIntent();
        hotel = (Hotel) i.getSerializableExtra("hotel");
        appReviewItemModel = new ArrayList<>();
    }

    private void intilizeUI() {
        progressBar = findViewById(R.id.idPBLoadingRev);
        ratingBar_Review = findViewById(R.id.ratingBar_Review);
        tv_user_name = findViewById(R.id.tv_user_name);
        tv_user_review_date = findViewById(R.id.tv_user_review);
        tv_user_review = findViewById(R.id.tv_user_review);
        commentValue = findViewById(R.id.comment);
        publish = findViewById(R.id.publish);

        ratingBar_Review.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    float touchPositionX = event.getX();
                    float width = ratingBar_Review.getWidth();
                    float starsf = (touchPositionX / width) * 5.0f;
                    int stars = (int)starsf + 1;
                    ratingBar_Review.setRating(stars);
                    ratingValue = starsf;
                    v.setPressed(false);
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setPressed(true);
                }

                if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                    v.setPressed(false);
                }




                return true;
            }});
    }

    private void saveReviewFireStore() {
        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    insertReviewsData();
            }
        });
    }

    private void insertReviewsData(){
            // hide the progress
            FirebaseFirestore dbFireStore = FirebaseFirestore.getInstance();
            FirebaseAuth auth=FirebaseAuth.getInstance();
            String userId =auth.getCurrentUser().getUid();
            dbFireStore.collection("users").document(userId).get().addOnCompleteListener(
                    new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                if (task.getResult() != null) {
                                    User user =  task.getResult().toObject(User.class);
                                    AppReviewItemModel review = new AppReviewItemModel(user.userName,commentValue.getText().toString(),ratingValue,new Date().toString());
                                    dbFireStore.collection("hotels").document(String.valueOf(hotel.getId()))
                                            .collection("reviews").add(review);
                                    appReviewItemModel.clear();
                                    loadAppReviewsDataAndSetAdapter();

                                }
                            }
                        }
                    }
            );

        mAdapter.notifyDataSetChanged();


    }


    private void loadAppReviewsDataAndSetAdapter() {

       // String userId  =auth.getCurrentUser().getUid();
        FirebaseFirestore dbFireStore = FirebaseFirestore.getInstance();

        ArrayList<AppReviewItemModel> appReviews = new ArrayList<>();


        DocumentReference hotelRef = dbFireStore.collection("hotels")
                .document(String.valueOf(hotel.getId()));

        hotelRef.collection("reviews")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                if (task.getResult() != null) {
                                    progressBar.setVisibility(View.GONE);
                                    appReviewItemModel = (ArrayList<AppReviewItemModel>) task.getResult().toObjects(AppReviewItemModel.class);
                                    mAdapter = new ReviewsAdapter(appReviewItemModel, ReviewActivity.this);
                                    appReviewsRecyclerView.setAdapter(mAdapter);
                                }
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Error getting Data: " + task.getException(),
                                        Toast.LENGTH_LONG)
                                        .show();
                            }
                        }
                    });


    }

}