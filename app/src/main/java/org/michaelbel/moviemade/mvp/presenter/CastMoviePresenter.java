package org.michaelbel.moviemade.mvp.presenter;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.michaelbel.moviemade.app.Url;
import org.michaelbel.moviemade.app.annotation.EmptyViewMode;
import org.michaelbel.moviemade.mvp.view.MvpCastMovieView;
import org.michaelbel.moviemade.rest.ApiFactory;
import org.michaelbel.moviemade.rest.api.MOVIES;
import org.michaelbel.moviemade.rest.model.Cast;
import org.michaelbel.moviemade.rest.response.CreditResponse;
import org.michaelbel.moviemade.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@InjectViewState
public class CastMoviePresenter extends MvpPresenter<MvpCastMovieView> {

    public void loadCredits(int movieId) {
        if (NetworkUtils.notConnected()) {
            getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        MOVIES service = ApiFactory.createService(MOVIES.class);
        Call<CreditResponse> call = service.getCredits(movieId, Url.TMDB_API_KEY);
        call.enqueue(new Callback<CreditResponse>() {
            @Override
            public void onResponse(@NonNull Call<CreditResponse> call, @NonNull Response<CreditResponse> response) {
                if (!response.isSuccessful()) {
                    getViewState().showError(EmptyViewMode.MODE_NO_PEOPLE);
                    return;
                }

                if (response.body() == null) {
                    getViewState().showError(EmptyViewMode.MODE_NO_PEOPLE);
                    return;
                }

                if (response.body().casts.isEmpty()) {
                    getViewState().showError(EmptyViewMode.MODE_NO_PEOPLE);
                    return;
                }

                List<Cast> newCasts = new ArrayList<>();
                newCasts.addAll(response.body().casts);

                getViewState().showResults(newCasts);
            }

            @Override
            public void onFailure(@NonNull Call<CreditResponse> call, @NonNull Throwable t) {
                getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
            }
        });
    }
}