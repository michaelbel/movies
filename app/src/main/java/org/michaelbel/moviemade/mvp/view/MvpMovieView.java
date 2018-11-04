package org.michaelbel.moviemade.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.bumptech.glide.request.RequestOptions;

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

    void showComplete();
}