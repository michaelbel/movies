package org.michaelbel.moviemade.ui.adapter.pagination;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import org.michaelbel.moviemade.rest.TmdbObject;
import org.michaelbel.moviemade.rest.model.Movie;
import org.michaelbel.moviemade.ui.adapter.recycler.Holder;
import org.michaelbel.moviemade.ui.adapter.recycler.LoadingHolder;
import org.michaelbel.moviemade.ui.view.LoadingView;
import org.michaelbel.moviemade.ui.view.movie.MovieViewListBig;
import org.michaelbel.moviemade.ui.view.movie.MovieViewPoster;
import org.michaelbel.moviemade.util.AndroidUtils;
import org.michaelbel.moviemade.util.DateUtils;

import java.util.ArrayList;
import java.util.List;

public class PaginationMoviesAdapter extends RecyclerView.Adapter {

    private final int ITEM = 0;
    private final int ITEM_POSTER = 1;
    private final int LOADING = 2;

    private List<TmdbObject> movies;
    private boolean isLoadingAdded = false;

    public PaginationMoviesAdapter() {
        movies = new ArrayList<>();
    }

    public List<TmdbObject> getMovies() {
        return movies;
    }

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
        Movie movie = (Movie) movies.get(position);

        if (getItemViewType(position) == ITEM) {
            MovieViewListBig view = (MovieViewListBig) ((Holder) holder).itemView;
            view.setPoster(movie.posterPath)
                .setTitle(movie.title)
                .setRating(String.valueOf(movie.voteAverage))
                .setVoteCount(String.valueOf(movie.voteCount))
                .setReleaseDate(movie.releaseDate != null ? DateUtils.getMovieReleaseDate(movie.releaseDate) : "")
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

    @Override
    public int getItemCount() {
        return movies != null ? movies.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == movies.size() - 1 && isLoadingAdded) ? LOADING : AndroidUtils.viewType() == 0 ? ITEM : ITEM_POSTER;
    }

//--------------------------------------------------------------------------------------------------

    public void add(TmdbObject movie) {
        movies.add(movie);
        notifyItemInserted(movies.size() - 1);
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

    public void remove(TmdbObject movie) {
        int position = movies.indexOf(movie);

        if (position > -1) {
            movies.remove(position);
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
        add(new Movie());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = movies.size() - 1;
        TmdbObject result = getItem(position);

        if (result != null) {
            movies.remove(position);
            notifyItemRemoved(position);
        }
    }

    private TmdbObject getItem(int position) {
        return movies.get(position);
    }
}