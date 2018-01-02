package org.michaelbel.application.ui.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import org.michaelbel.application.moviemade.LayoutHelper;
import org.michaelbel.application.moviemade.Moviemade;
import org.michaelbel.application.rest.model.Movie;
import org.michaelbel.application.ui.view.LoadingView;
import org.michaelbel.application.ui.view.MovieViewCompat;
import org.michaelbel.application.ui.view.movie.MovieViewCard;
import org.michaelbel.application.ui.view.movie.MovieViewList;

import java.util.List;

//@SuppressWarnings("all")
public class MoviesAdapter extends RecyclerView.Adapter {

    private List<Movie> movies;

    public MoviesAdapter(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View view;

        if (type == 0) {
            view = new MovieViewCard(parent.getContext());
        } else if (type == 1) {
            view = new MovieViewList(parent.getContext());
        } else if (type == 2) {
            view = new MovieViewCompat(parent.getContext());
        } else {
            view = new LoadingView(parent.getContext());
        }

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        Movie movie = movies.get(position);

        if (type == 0) {
            MovieViewCard view = (MovieViewCard) holder.itemView;
            view.getPosterImage().setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, 150));
            view.setPoster(movie.posterPath)
                .setTitle(movie.title)
                .setYear(movie.releaseDate);
        } else if (type == 1) {
            MovieViewList view = (MovieViewList) holder.itemView;
            view.setPoster(movie.posterPath)
                .setTitle(movie.title)
                .setVoteAverage(movie.voteAverage)
                .setYear(movie.releaseDate)
                .setDivider(position != movies.size() - 1);
        } else if (type == 2) {
            MovieViewCompat view = (MovieViewCompat) holder.itemView;
            view.setMovie(movie);
        }
    }

    @Override
    public int getItemCount() {
        return movies != null ? movies.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        SharedPreferences preferences = Moviemade.AppContext.getSharedPreferences("mainconfig2", Context.MODE_PRIVATE);
        int viewType = preferences.getInt("view_type", 1);

        if (viewType == 0) {
            return 0;
        } else if (viewType == 1) {
            return 1;
        } else if (viewType == 2) {
            return 2;
        } else {
            return 3;
        }
    }
}