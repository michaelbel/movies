package org.michaelbel.moviemade.ui.adapter.pagination;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import org.michaelbel.moviemade.rest.TmdbObject;
import org.michaelbel.moviemade.rest.model.v3.Review;
import org.michaelbel.moviemade.ui.adapter.pagination.base.PaginationAdapter;
import org.michaelbel.moviemade.ui.adapter.recycler.Holder;
import org.michaelbel.moviemade.ui.adapter.recycler.LoadingHolder;
import org.michaelbel.moviemade.ui.view.LoadingView;
import org.michaelbel.moviemade.ui.view.ReviewView;

import java.util.List;

public class PaginationReviewsAdapter extends PaginationAdapter {

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
        Review review = (Review) objectList.get(position);

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