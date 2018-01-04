package org.michaelbel.application.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import org.michaelbel.application.rest.model.Review;
import org.michaelbel.application.ui.view.ReviewView;

import java.util.List;

@SuppressWarnings("all")
public class ReviewsAdapter extends RecyclerView.Adapter {

    private List<Review> reviews;

    public ReviewsAdapter(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(new ReviewView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Review review = reviews.get(position);

        ReviewView view = (ReviewView) holder.itemView;
        view.setAuthor(review.author)
            .setContent(review.content)
            .setDivider(position != reviews.size() - 1);
    }

    @Override
    public int getItemCount() {
        return reviews != null ? reviews.size() : 0;
    }
}