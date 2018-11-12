package org.michaelbel.moviemade.ui.modules.movie;

import com.arellomobile.mvp.MvpView;
import com.bumptech.glide.request.RequestOptions;

import org.michaelbel.tmdb.v3.json.Movie;

public interface MovieMvp extends MvpView {

    void setPoster(RequestOptions options, String posterPath);

    void setMovieTitle(String title);

    void setOverview(String overview);

    void setVoteAverage(float voteAverage);

    void setVoteCount(int voteCount);

    void setReleaseDate(String releaseDate);

    void setOriginalLanguage(String originalLanguage);

    void setRuntime(String runtime);

    void setTagline(String tagline);

    void setWatching(boolean watch);

    void showConnectionError();

    void showComplete(Movie movie);
}