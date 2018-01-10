package org.michaelbel.moviemade.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import org.michaelbel.moviemade.rest.model.Movie;
import org.michaelbel.moviemade.ui.view.movie.MovieViewList;

import java.util.List;

public class SearchMoviesAdapter extends RecyclerView.Adapter {

    private List<Movie> searches;

    public SearchMoviesAdapter(List<Movie> searchResults) {
        this.searches = searchResults;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        return new Holder(new MovieViewList(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Movie movie = searches.get(position);

        MovieViewList view = (MovieViewList) holder.itemView;
        view.setPoster(movie.posterPath)
            .setTitle(movie.title)
            .setYear(movie.releaseDate)
            .setVoteAverage(movie.voteAverage)
            .setDivider(position != searches.size() - 1);
    }

    @Override
    public int getItemCount() {
        return searches != null ? searches.size() : 0;
    }
}