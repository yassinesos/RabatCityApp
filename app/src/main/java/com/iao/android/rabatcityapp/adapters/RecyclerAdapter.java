package com.iao.android.rabatcityapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.iao.android.rabatcityapp.Holidays;
import com.iao.android.rabatcityapp.R;
import com.iao.android.rabatcityapp.SecondActivity;
import com.iao.android.rabatcityapp.models.Hotel;

import java.util.List;

public class RecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE = 1;
    private final Context context;
    private final List<T> listRecyclerItem;

    public RecyclerAdapter(Context context, List<T> listRecyclerItem) {
        this.context = context;
        this.listRecyclerItem = listRecyclerItem;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView name, ratingText, reviewNumber;
        private RatingBar ratingBar;
        private TextView date;
        private CardView cardview;
        private Animation anim_from_button;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
            ratingText = (TextView) itemView.findViewById(R.id.ratingText);
            //reviewNumber = (TextView) itemView.findViewById(R.id.reviewNumber);
            imageView = (ImageView) itemView.findViewById(R.id.view);
            cardview = itemView.findViewById(R.id.cardView);
            anim_from_button = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.anim_from_bottom);
            cardview.setAnimation(anim_from_button);
            cardview.setOnClickListener(view -> {
                Intent secondActivity = new Intent(view.getContext(), SecondActivity.class);
                view.getContext().startActivity(secondActivity);
            });
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE:

            default:

                View layoutView = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.list_offers, parent, false);

                return new ItemViewHolder((layoutView));
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        int viewType = getItemViewType(position);

        switch (viewType) {
            case TYPE:
            default:

                ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
                Hotel hotel = (Hotel) listRecyclerItem.get(position);
                itemViewHolder.name.setText(hotel.getName());
                itemViewHolder.ratingBar.setRating(hotel.getStarRating());
                itemViewHolder.ratingText.setText(String.valueOf(hotel.getStarRating()));
                Glide.with(itemViewHolder.imageView.getContext())
                        .load(hotel.getPhotos().get(0))
                        .centerCrop()
                        .placeholder(R.drawable.image_one)
                        .into(itemViewHolder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return listRecyclerItem.size();
    }
}