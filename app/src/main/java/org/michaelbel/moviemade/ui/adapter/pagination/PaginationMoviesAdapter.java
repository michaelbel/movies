package org.michaelbel.moviemade.ui.adapter.pagination;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import org.michaelbel.moviemade.rest.TmdbObject;
import org.michaelbel.moviemade.rest.model.Movie;
import org.michaelbel.moviemade.ui.adapter.recycler.Holder;
import org.michaelbel.moviemade.ui.adapter.recycler.LoadingHolder;
import org.michaelbel.moviemade.ui.view.LoadingView;
import org.michaelbel.moviemade.ui.view.movie.MovieViewListBig;
import org.michaelbel.moviemade.util.DateUtils;

import java.util.ArrayList;
import java.util.List;

public class PaginationMoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int ITEM = 0;
    private final int LOADING = 1;

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
        } else if (viewType == LOADING) {
            viewHolder = new LoadingHolder(new LoadingView(parent.getContext()));
        }

        // todo delete comments
        /*switch (viewType) {
            case ITEM:
                viewHolder = new Holder(new MovieViewListBig(parent.getContext()));
                break;
            case LOADING:
                viewHolder = new LoadingHolder(new LoadingView(parent.getContext()));
                break;
        }*/

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Movie movie = (Movie) movies.get(position);

        if (getItemViewType(position) == ITEM) {
            //Holder viewHolder = (Holder) holder;

            MovieViewListBig view = (MovieViewListBig) ((Holder) holder).itemView;
            view.setPoster(movie.posterPath)
                    .setTitle(movie.title)
                    .setRating(String.valueOf(movie.voteAverage))
                    .setVoteCount(String.valueOf(movie.voteCount))
                    .setReleaseDate(movie.releaseDate != null ? DateUtils.getMovieReleaseDate(movie.releaseDate) : "")
                    .setOverview(movie.overview)
                    .setDivider(true);
        }

        /*switch (getItemViewType(position)) {
            case ITEM:
                final Holder viewHolder = (Holder) holder;

                MovieViewListBig view = (MovieViewListBig) viewHolder.itemView;
                view.setPoster(movie.posterPath)
                    .setTitle(movie.title)
                    .setRating(String.valueOf(movie.voteAverage))
                    .setVoteCount(String.valueOf(movie.voteCount))
                    .setReleaseDate(movie.releaseDate != null ? DateUtils.getMovieReleaseDate(movie.releaseDate) : "")
                    .setOverview(movie.overview)
                    .setDivider(true);
            case LOADING:
                break;
        }*/
    }

    @Override
    public int getItemCount() {
        return movies != null ? movies.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == movies.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

//--------------------------------------------------------------------------------------------------

    public void add(TmdbObject movie) {
        movies.add(movie);
        notifyItemInserted(movies.size() - 1);
    }

    public void addAll(List<TmdbObject> movies) {
        for (TmdbObject movie : movies) {
            add(movie);
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

    public TmdbObject getItem(int position) {
        return movies.get(position);
    }
}