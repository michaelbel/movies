package org.michaelbel.moviemade.modules_beta.review;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ViewGroup;

import org.michaelbel.material.widget.Holder;
import org.michaelbel.tmdb.TmdbObject;
import org.michaelbel.moviemade.rest.model.v3.Review;
import org.michaelbel.moviemade.ui.base.PaginationAdapter;
import org.michaelbel.moviemade.modules_beta.view.ReviewView;

import java.util.List;

public class PaginationReviewsAdapter extends PaginationAdapter {

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == ITEM_BACKDROP) {
            viewHolder = new Holder(new ReviewView(parent.getContext()));
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Review review = (Review) objectList.get(position);

        if (getItemViewType(position) == ITEM_BACKDROP) {
            ReviewView view = (ReviewView) ((Holder) holder).itemView;
            view.setAuthor(review.author)
                .setContent(review.content)
                .setDivider(true);
        }
    }

    public void addAll(List<TmdbObject> reviews) {
        for (TmdbObject review : reviews) {
            add(review);
        }
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Review());
    }
}