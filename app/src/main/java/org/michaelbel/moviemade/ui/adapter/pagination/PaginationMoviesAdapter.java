package org.michaelbel.moviemade.ui.adapter.pagination;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import org.michaelbel.moviemade.app.extensions.AndroidExtensions;
import org.michaelbel.moviemade.rest.TmdbObject;
import org.michaelbel.moviemade.rest.model.Movie;
import org.michaelbel.moviemade.ui.adapter.pagination.base.PaginationAdapter;
import org.michaelbel.moviemade.ui.adapter.recycler.Holder;
import org.michaelbel.moviemade.ui.adapter.recycler.LoadingHolder;
import org.michaelbel.moviemade.ui.view.LoadingView;
import org.michaelbel.moviemade.ui.view.movie.MovieViewListBig;
import org.michaelbel.moviemade.ui.view.movie.MovieViewPoster;
import org.michaelbel.moviemade.utils.AndroidUtils;

import java.util.List;

public class PaginationMoviesAdapter extends PaginationAdapter {

    private final int ITEM_POSTER = 2;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == ITEM) {
            viewHolder = new Holder(new MovieViewListBig(parent.getContext()));
        } else if (viewType == ITEM_POSTER) {
            viewHolder = new Holder(new MovieViewPoster(parent.getContext()));
        } else if (viewType == LOADING) {
            viewHolder = new LoadingHolder(new LoadingView(parent.getContext()));
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Movie movie = (Movie) objectList.get(position);

        if (getItemViewType(position) == ITEM) {
            MovieViewListBig view = (MovieViewListBig) ((Holder) holder).itemView;
            view.setPoster(movie.posterPath)
                .setTitle(movie.title)
                .setRating(String.valueOf(movie.voteAverage))
                .setVoteCount(String.valueOf(movie.voteCount))
                .setReleaseDate(movie.releaseDate != null ? AndroidExtensions.formatReleaseDate(movie.releaseDate) : "")
                .setOverview(movie.overview)
                .setDivider(true);
        } else if (getItemViewType(position) == ITEM_POSTER) {
            MovieViewPoster view = (MovieViewPoster) ((Holder) holder).itemView;
            view.setPoster(movie.posterPath);
        } else if (getItemViewType(position) == LOADING) {
            LoadingView view = (LoadingView) ((LoadingHolder) holder).itemView;
            view.setMode(AndroidUtils.viewType());
        }
    }

    public void addAll(List<TmdbObject> movies) {
        for (TmdbObject movie : movies) {
            if (AndroidUtils.includeAdult()) {
                add(movie);
            } else {
                if (!((Movie) movie).adult) {
                    add(movie);
                }
            }
        }
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Movie());
    }
}