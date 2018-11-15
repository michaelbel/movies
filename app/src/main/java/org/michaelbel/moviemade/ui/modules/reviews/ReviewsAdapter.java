package org.michaelbel.moviemade.ui.modules.reviews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.data.dao.Review;
import org.michaelbel.moviemade.ui.widgets.RecyclerListView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

public class ReviewsAdapter extends RecyclerView.Adapter {

    private AppCompatTextView authorName;
    private AppCompatTextView reviewText;

    public ArrayList<Review> reviews = new ArrayList<>();

    public void setReviews(List<Review> results) {
        reviews.addAll(results);
        notifyItemRangeInserted(reviews.size() + 1, results.size());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
        authorName = view.findViewById(R.id.author_name);
        reviewText = view.findViewById(R.id.review_text);
        return new RecyclerListView.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        Review review = reviews.get(position);
        authorName.setText(review.getAuthor());
        reviewText.setText(review.getContent());
    }

    @Override
    public int getItemCount() {
        return reviews != null ? reviews.size() : 0;
    }
}