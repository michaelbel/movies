package org.michaelbel.moviemade.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import org.michaelbel.moviemade.rest.TmdbObject;
import org.michaelbel.moviemade.rest.model.Movie;
import org.michaelbel.moviemade.ui.view.movie.MovieViewListBig;

import java.util.List;

public class SearchMoviesAdapter extends RecyclerView.Adapter {

    private List<TmdbObject> searches;

    public SearchMoviesAdapter(List<TmdbObject> searchResults) {
        this.searches = searchResults;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        return new Holder(new MovieViewListBig(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Movie movie = (Movie) searches.get(position);

        MovieViewListBig view = (MovieViewListBig) holder.itemView;
        view.setPoster(movie.posterPath)
            .setTitle(movie.title)
            .setRating(String.valueOf(movie.voteAverage))
            .setVoteCount(String.valueOf(movie.voteCount))
            //.setReleaseDate(DateUtils.getMovieReleaseDate(movie.releaseDate))
            .setOverview(movie.overview)
            .setDivider(true);
    }

    @Override
    public int getItemCount() {
        return searches != null ? searches.size() : 0;
    }

    public List<TmdbObject> getMovies() {
        return searches;
    }
}