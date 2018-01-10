package org.michaelbel.moviemade.mvp.view;

import com.arellomobile.mvp.MvpView;

import org.michaelbel.moviemade.rest.model.Movie;

import java.util.List;

public interface MvpReviewsView extends MvpView {

    void loadReviews();

    void showNoResults();

    void showSuccessful(List<Movie> movies);

    void showError();
}