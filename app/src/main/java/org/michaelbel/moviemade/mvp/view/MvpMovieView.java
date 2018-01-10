package org.michaelbel.moviemade.mvp.view;

import com.arellomobile.mvp.MvpView;

import org.michaelbel.moviemade.rest.model.Crew;
import org.michaelbel.moviemade.rest.model.Movie;
import org.michaelbel.moviemade.rest.model.Trailer;

import java.util.List;

public interface MvpMovieView extends MvpView {

    //void showMovieFromExtra(Movie movie);

    void showMovie(Movie movie, boolean loaded);

    void showError();

    void showTrailers(List<Trailer> trailers);

    void showImages(int postersCount, int backdropsCount);

    void showCrew(List<Crew> crews);
}