package org.michaelbel.moviemade.mvp.view;

import com.arellomobile.mvp.MvpView;

import org.michaelbel.moviemade.app.annotation.EmptyViewMode;
import org.michaelbel.moviemade.rest.model.Movie;

import java.util.List;

public interface MvpGenreMoviesView extends MvpView {

    void showResults(List<Movie> newMovies);

    void showError(@EmptyViewMode int mode);
}