package org.michaelbel.moviemade.ui.modules.reviews.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.data.entity.Review;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.internal.DebouncingOnClickListener;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder> {

    private OnReviewClickListener reviewClickListener;
    private ArrayList<Review> reviews = new ArrayList<>();

    public ReviewsAdapter(OnReviewClickListener listener) {
        this.reviewClickListener = listener;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> results) {
        reviews.addAll(results);
        notifyItemRangeInserted(reviews.size() + 1, results.size());
    }

    @NonNull
    @Override
    public ReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_review, parent, false);
        ReviewsViewHolder holder = new ReviewsViewHolder(view);
        view.setOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doClick(View v) {
                int pos = holder.getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    reviewClickListener.onReviewClick(reviews.get(pos), v);
                }
            }
        });
        return holder;
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

        AppCompatTextView authorName;
        AppCompatTextView reviewText;

        private ReviewsViewHolder(View view) {
            super(view);
            authorName = view.findViewById(R.id.author_name);
            reviewText = view.findViewById(R.id.review_text);
        }
    }
}