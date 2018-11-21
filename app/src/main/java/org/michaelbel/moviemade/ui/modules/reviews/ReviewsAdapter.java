package org.michaelbel.moviemade.ui.modules.reviews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.data.dao.Review;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder> {

    public ArrayList<Review> reviews = new ArrayList<>();

    public void setReviews(List<Review> results) {
        reviews.addAll(results);
        notifyItemRangeInserted(reviews.size() + 1, results.size());
    }

    @NonNull
    @Override
    public ReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
        return new ReviewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsViewHolder holder, int position) {
        Review review = reviews.get(position);
        holder.authorName.setText(review.getAuthor());
        holder.reviewText.setText(review.getContent());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    class ReviewsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.author_name) AppCompatTextView authorName;
        @BindView(R.id.review_text) AppCompatTextView reviewText;

        private ReviewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}