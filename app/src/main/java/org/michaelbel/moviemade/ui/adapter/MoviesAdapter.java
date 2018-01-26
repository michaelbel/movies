package org.michaelbel.moviemade.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import org.michaelbel.moviemade.rest.TmdbObject;
import org.michaelbel.moviemade.rest.model.Movie;
import org.michaelbel.moviemade.ui.adapter.recycler.Holder;
import org.michaelbel.moviemade.ui.view.movie.MovieViewListBig;
import org.michaelbel.moviemade.ui.view.movie.MovieViewPoster;
import org.michaelbel.moviemade.util.AndroidUtils;
import org.michaelbel.moviemade.util.DateUtils;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter {

    private List<TmdbObject> movies;

    public MoviesAdapter() {
        movies = new ArrayList<>();
    }

    public void addMovies(List<TmdbObject> results) {
        movies.addAll(results);
        notifyItemRangeInserted(movies.size() + 1, results.size());
    }

    public List<TmdbObject> getMovies() {
        return movies;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View view = null;

        if (type == 0) {
            view = new MovieViewListBig(parent.getContext());
        } else if (type == 1) {
            view = new MovieViewPoster(parent.getContext());
        }

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Movie movie = (Movie) movies.get(position);

        if (getItemViewType(position) == 0) {
            MovieViewListBig view = (MovieViewListBig) holder.itemView;
            view.setPoster(movie.posterPath)
                .setTitle(movie.title)
                .setRating(String.valueOf(movie.voteAverage))
                .setVoteCount(String.valueOf(movie.voteCount))
                .setReleaseDate(movie.releaseDate != null ? DateUtils.getMovieReleaseDate(movie.releaseDate) : "")
                .setOverview(movie.overview)
                .setDivider(position != movies.size() - 1);
        } else if (getItemViewType(position) == 1) {
            MovieViewPoster view = (MovieViewPoster) holder.itemView;
            view.setPoster(movie.posterPath);
        }
    }

    @Override
    public int getItemCount() {
        return movies != null ? movies.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return AndroidUtils.viewType();
    }
}