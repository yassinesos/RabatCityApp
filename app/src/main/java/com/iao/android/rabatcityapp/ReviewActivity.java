package com.iao.android.rabatcityapp;

import android.app.AlertDialog;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.iao.android.rabatcityapp.adapters.ReviewsAdapter;
import com.iao.android.rabatcityapp.models.AppReviewItemModel;

import java.util.ArrayList;

public class ReviewActivity  extends AppCompatActivity {
    private RecyclerView appReviewsRecyclerView;
    private LinearLayout openAppLayout;
    private FirebaseAuth auth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private ReviewsAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        appReviewsRecyclerView = findViewById(R.id.my_recycler_review);
        RecyclerView.LayoutManager verticalLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        appReviewsRecyclerView.setLayoutManager(verticalLayoutManager);
        appReviewsRecyclerView.setHasFixedSize(true);
        loadAppReviewsDataAndSetAdapter();
        saveReviewFireStore();
    }

    private void saveReviewFireStore() {
        Button publish = findViewById(R.id.publish);
        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void loadAppReviewsDataAndSetAdapter() {

       // String userId  =auth.getCurrentUser().getUid();
        FirebaseFirestore dbFireStore = FirebaseFirestore.getInstance();

        ArrayList<AppReviewItemModel> appReviews = new ArrayList<>();
        dbFireStore.collection("hotels").document("Hotel1").collection("reviews")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult() != null) {
                                ArrayList<AppReviewItemModel> appReviewItemModel = (ArrayList<AppReviewItemModel>) task.getResult().toObjects(AppReviewItemModel.class);
                                appReviewsRecyclerView.setAdapter(new ReviewsAdapter(appReviewItemModel, ReviewActivity.this));
                            }
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Error getting documents: "+ task.getException(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });


    }
}