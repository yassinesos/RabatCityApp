package com.iao.android.rabatcityapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iao.android.rabatcityapp.R;
import com.iao.android.rabatcityapp.models.AppReviewItemModel;

import java.util.ArrayList;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.AppViewHolder>{
    private final ArrayList<AppReviewItemModel> appReviewsList;
    private final Context mContext;
    public ReviewsAdapter(ArrayList<AppReviewItemModel> appReviewsList, Context context) {
        this.appReviewsList = appReviewsList;
        this.mContext = context;

    }

    @NonNull
    @Override
    public ReviewsAdapter.AppViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        return new AppViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsAdapter.AppViewHolder holder, int position) {
        holder.userName.setText(appReviewsList.get(position).username);
        holder.userReview.setText(appReviewsList.get(position).comment);
        holder.userReviewRating.setRating(appReviewsList.get(position).rating);
        holder.userReviewDate.setText(appReviewsList.get(position).date);
    }

    @Override
    public int getItemCount() {
        return appReviewsList.size();
    }

    class AppViewHolder extends RecyclerView.ViewHolder {

        final TextView userName;
        final TextView userReview;
        final TextView userReviewDate;
        final RatingBar userReviewRating;

        AppViewHolder(View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.tv_user_name);
            userReview = itemView.findViewById(R.id.tv_user_review);
            userReviewDate = itemView.findViewById(R.id.tv_user_review_date);
            userReviewRating = itemView.findViewById(R.id.ratingBarReview);
        }
    }
}
