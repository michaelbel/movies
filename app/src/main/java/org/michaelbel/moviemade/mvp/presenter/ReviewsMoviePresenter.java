package org.michaelbel.moviemade.mvp.presenter;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.michaelbel.moviemade.app.Url;
import org.michaelbel.moviemade.app.annotation.EmptyViewMode;
import org.michaelbel.moviemade.mvp.view.MvpReviewsView;
import org.michaelbel.moviemade.rest.ApiFactory;
import org.michaelbel.moviemade.rest.api.MOVIES;
import org.michaelbel.moviemade.rest.model.v3.Review;
import org.michaelbel.moviemade.rest.response.ReviewResponse;
import org.michaelbel.moviemade.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@InjectViewState
public class ReviewsMoviePresenter extends MvpPresenter<MvpReviewsView> {

    public int page;
    public int totalPages;
    public boolean loading;
    public boolean loadingLocked;

    private int movieId;

    public void loadReviews(int movieId) {
        this.movieId = movieId;

        if (movieId == 0) {
            getViewState().showError(EmptyViewMode.MODE_NO_REVIEWS);
            return;
        }

        if (NetworkUtils.notConnected()) {
            getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        page = 1;
        totalPages = 0;
        loading = false;
        loadingLocked = false;

        MOVIES service = ApiFactory.createService(MOVIES.class);
        Call<ReviewResponse> call = service.getReviews(movieId, Url.TMDB_API_KEY, Url.en_US, page);
        call.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(@NonNull Call<ReviewResponse> call, @NonNull Response<ReviewResponse> response) {
                if (!response.isSuccessful()) {
                    getViewState().showError(EmptyViewMode.MODE_NO_REVIEWS);
                    return;
                }

                if (response.body() == null) {
                    getViewState().showError(EmptyViewMode.MODE_NO_REVIEWS);
                    return;
                }

                totalPages = response.body().totalPages;

                List<Review> newReviews = new ArrayList<>();
                newReviews.addAll(response.body().reviews);

                if (newReviews.isEmpty()) {
                    getViewState().showError(EmptyViewMode.MODE_NO_REVIEWS);
                    return;
                }

                getViewState().showResults(newReviews);
                page++;
            }

            @Override
            public void onFailure(@NonNull Call<ReviewResponse> call, @NonNull Throwable t) {
                getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
            }
        });
    }

    public void loadResults() {
        MOVIES service = ApiFactory.createService(MOVIES.class);
        Call<ReviewResponse> call = service.getReviews(movieId, Url.TMDB_API_KEY, Url.en_US, page);
        call.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(@NonNull Call<ReviewResponse> call, @NonNull Response<ReviewResponse> response) {
                if (!response.isSuccessful()) {
                    loadingLocked = true;
                    return;
                }

                List<Review> newReviews = new ArrayList<>();
                newReviews.addAll(response.body().reviews);

                if (newReviews.isEmpty()) {
                    return;
                }

                getViewState().showResults(newReviews);
                page++;
                loading = false;
            }

            @Override
            public void onFailure(@NonNull Call<ReviewResponse> call, @NonNull Throwable t) {
                loadingLocked = true;
                loading = false;
            }
        });

        loading = true;
    }
}