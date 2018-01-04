package org.michaelbel.application.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import org.michaelbel.application.rest.model.Movie;
import org.michaelbel.application.ui.view.movie.MovieViewList;

import java.util.List;

@SuppressWarnings("all")
public class SearchMoviesAdapter extends RecyclerView.Adapter {

    private List<Movie> searchResults;

    public SearchMoviesAdapter(List<Movie> searchResults) {
        this.searchResults = searchResults;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        return new Holder(new MovieViewList(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Movie movie = searchResults.get(position);

        MovieViewList view = (MovieViewList) holder.itemView;
        view.setPoster(movie.posterPath)
            .setTitle(movie.title)
            .setYear(movie.releaseDate)
            .setVoteAverage(movie.voteAverage)
            .setDivider(position != searchResults.size() - 1);
    }

    @Override
    public int getItemCount() {
        return searchResults != null ? searchResults.size() : 0;
    }
}