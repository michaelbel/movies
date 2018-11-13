package org.michaelbel.moviemade.modules_beta.review;

import android.view.ViewGroup;

import org.michaelbel.material.widget.Holder;
import org.michaelbel.moviemade.data.TmdbObject;
import org.michaelbel.moviemade.rest.model.v3.Review;
import org.michaelbel.moviemade.modules_beta.view.ReviewView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ReviewsAdapter extends RecyclerView.Adapter {

    private List<TmdbObject> reviews;

    public ReviewsAdapter() {
        reviews = new ArrayList<>();
    }

    public void addReviews(List<TmdbObject> results) {
        reviews.addAll(results);
        notifyItemRangeInserted(reviews.size() + 1, results.size());
    }

    public List<TmdbObject> getReviews() {
        return reviews;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(new ReviewView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        Review review = (Review) reviews.get(position);

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