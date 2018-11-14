package org.michaelbel.moviemade.ui.modules.movie;

import com.arellomobile.mvp.MvpView;

import org.michaelbel.moviemade.data.dao.Movie;

public interface MovieMvp extends MvpView {

    void setPoster(String posterPath);

    void setMovieTitle(String title);

    void setOverview(String overview);

    void setVoteAverage(float voteAverage);

    void setVoteCount(int voteCount);

    void setReleaseDate(String releaseDate);

    void setOriginalLanguage(String originalLanguage);

    void setRuntime(String runtime);

    void setTagline(String tagline);

    void setURLs(String imdbId, String homepage);

    void setWatching(boolean watch);

    void showConnectionError();

    void showComplete(Movie movie);
}