package org.michaelbel.moviemade.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.michaelbel.moviemade.mvp.view.MvpReviewsView;
import org.michaelbel.moviemade.util.NetworkUtils;

@InjectViewState
public class ReviewsMoviePresenter extends MvpPresenter<MvpReviewsView> {

    public void loadReviews(int movieId) {
        if (NetworkUtils.notConnected()) {
            getViewState().showError();
        }
    }
}