package org.michaelbel.moviemade.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.bumptech.glide.request.RequestOptions;

import org.michaelbel.moviemade.model.MovieRealm;
import org.michaelbel.moviemade.rest.model.v3.Backdrop;
import org.michaelbel.moviemade.rest.model.v3.Keyword;
import org.michaelbel.moviemade.rest.model.Crew;
import org.michaelbel.moviemade.rest.model.Movie;
import org.michaelbel.moviemade.rest.model.v3.Poster;
import org.michaelbel.moviemade.rest.model.v3.Trailer;

import java.util.List;

import androidx.annotation.NonNull;

public interface MvpMovieView extends MvpView {

    void setPoster(RequestOptions options, String posterPath);

    void setMovieTitle(String title);

    void setOverview(String overview);

    void setVoteAverage(float voteAverage);

    void setVoteCount(int voteCount);

    void setReleaseDate(String releaseDate);

    void setOriginalLanguage(String originalLanguage);

    void setRuntime(String runtime);

    void setTagline(String tagline);

    void showConnectionError();
}