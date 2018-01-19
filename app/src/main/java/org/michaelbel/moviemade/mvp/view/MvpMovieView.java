package org.michaelbel.moviemade.mvp.view;

import com.arellomobile.mvp.MvpView;

import org.michaelbel.moviemade.model.MovieRealm;
import org.michaelbel.moviemade.rest.model.Keyword;
import org.michaelbel.moviemade.rest.model.Crew;
import org.michaelbel.moviemade.rest.model.Movie;
import org.michaelbel.moviemade.rest.model.Trailer;

import java.util.List;

public interface MvpMovieView extends MvpView {

    void showMovie(Movie movie, boolean loaded);

    void showMovieRealm(MovieRealm movie);

    void showError();

    void showTrailers(List<Trailer> trailers);

    void showKeywords(List<Keyword> keywords);

    void showImages(String posterPath, String backdropPath, int postersCount, int backdropsCount);

    void showCrew(List<Crew> crews);

    void realmAdded();

    void favoriteButtonState(boolean state);

    void watchingButtonState(boolean state);
}