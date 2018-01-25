package org.michaelbel.moviemade.ui.adapter.pagination;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import org.michaelbel.moviemade.rest.TmdbObject;
import org.michaelbel.moviemade.rest.model.v3.Review;
import org.michaelbel.moviemade.ui.adapter.recycler.Holder;
import org.michaelbel.moviemade.ui.adapter.recycler.LoadingHolder;
import org.michaelbel.moviemade.ui.view.LoadingView;
import org.michaelbel.moviemade.ui.view.ReviewView;

import java.util.ArrayList;
import java.util.List;

public class PaginationReviewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int ITEM = 0;
    private final int LOADING = 1;

    private List<TmdbObject> reviews;
    private boolean isLoadingAdded = false;

    public PaginationReviewsAdapter() {
        reviews = new ArrayList<>();
    }

    public List<TmdbObject> getReviews() {
        return reviews;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == ITEM) {
            viewHolder = new Holder(new ReviewView(parent.getContext()));
        } else if (viewType == LOADING) {
            viewHolder = new LoadingHolder(new LoadingView(parent.getContext()));
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Review review = (Review) reviews.get(position);

        if (getItemViewType(position) == ITEM) {
            ReviewView view = (ReviewView) ((Holder) holder).itemView;
            view.setAuthor(review.author)
                .setContent(review.content)
                .setDivider(true);
        } else if (getItemViewType(position) == LOADING) {
            LoadingView view = (LoadingView) ((LoadingHolder) holder).itemView;
            view.setMode(LoadingView.MODE_DEFAULT);
        }
    }

    @Override
    public int getItemCount() {
        return reviews != null ? reviews.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == reviews.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

//--------------------------------------------------------------------------------------------------

    public void add(TmdbObject review) {
        reviews.add(review);
        notifyItemInserted(reviews.size() - 1);
    }

    public void addAll(List<TmdbObject> reviews) {
        for (TmdbObject review : reviews) {
            add(review);
        }
    }

    public void remove(TmdbObject review) {
        int position = reviews.indexOf(review);

        if (position > -1) {
            reviews.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;

        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Review());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = reviews.size() - 1;
        TmdbObject result = getItem(position);

        if (result != null) {
            reviews.remove(position);
            notifyItemRemoved(position);
        }
    }

    public TmdbObject getItem(int position) {
        return reviews.get(position);
    }
}