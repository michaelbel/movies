package org.michaelbel.moviemade.mvp.view;

import com.arellomobile.mvp.MvpView;

import org.michaelbel.moviemade.rest.model.Movie;

import java.util.List;

public interface MvpSearchView extends MvpView {

    void searchStart();

    void searchNoResults();

    void searchComplete(List<Movie> movies);

    void showError();
}