package com.iao.android.rabatcityapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.iao.android.rabatcityapp.R;
import com.iao.android.rabatcityapp.SecondActivity;
import com.iao.android.rabatcityapp.models.Hotel;
import com.iao.android.rabatcityapp.models.modelInterface;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter<T extends modelInterface> extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private static final int TYPE = 1;
    private final Context context;
    private final List<T> listRecyclerItem;
    private final List<T> listRecyclerItemFull;

    public RecyclerAdapter(Context context, List<T> listRecyclerItem) {
        this.context = context;
        this.listRecyclerItem = listRecyclerItem;
        listRecyclerItemFull = new ArrayList<T>(listRecyclerItem);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView name, ratingText, reviewNumber, price;
        private RatingBar ratingBar;
        private TextView date;
        private CardView cardview;
        private Animation anim_from_button;
        private Intent secondActivity;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
            ratingText = (TextView) itemView.findViewById(R.id.ratingText);
            //reviewNumber = (TextView) itemView.findViewById(R.id.reviewNumber);
            imageView = (ImageView) itemView.findViewById(R.id.view);
            price = itemView.findViewById(R.id.price);
            cardview = itemView.findViewById(R.id.cardView);
            anim_from_button = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.anim_from_bottom);
            cardview.setAnimation(anim_from_button);
             secondActivity = new Intent(cardview.getContext(), SecondActivity.class);
            cardview.setOnClickListener(view -> {
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

                T item = listRecyclerItem.get(position);
                String name = item.getName();
                float starRating = item.getStarRating();
                String startRatingText = String.valueOf(item.getStarRating());
                String Image = item.getPhotos().get(0);
                itemViewHolder.name.setText(name);
                itemViewHolder.ratingBar.setRating(starRating);
                itemViewHolder.ratingText.setText(startRatingText);

                itemViewHolder.price.setText(item.getPrice());
                Glide.with(itemViewHolder.imageView.getContext())
                        .load(Image)
                        .centerCrop()
                        .placeholder(R.drawable.image_one)
                        .into(itemViewHolder.imageView);
                //adding informations needed for the second activity
                itemViewHolder.secondActivity.putExtra("hotel", (Serializable) item);
        }
    }

    @Override
    public int getItemCount() {
        return listRecyclerItem.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter(){

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<T> filteredList = new ArrayList<>();
            
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(listRecyclerItemFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (T item : listRecyclerItemFull) {
                    if (item.getName().toLowerCase().contains(filterPattern) || item.getAbout().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listRecyclerItem.clear();
            listRecyclerItem.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
