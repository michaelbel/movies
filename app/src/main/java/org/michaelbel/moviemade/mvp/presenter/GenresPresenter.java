package org.michaelbel.moviemade.mvp.presenter;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.michaelbel.moviemade.app.Url;
import org.michaelbel.moviemade.app.annotation.EmptyViewMode;
import org.michaelbel.moviemade.mvp.view.MvpGenresView;
import org.michaelbel.moviemade.rest.ApiFactory;
import org.michaelbel.moviemade.rest.api.GENRES;
import org.michaelbel.moviemade.rest.response.MovieGenresResponse;
import org.michaelbel.moviemade.util.NetworkUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@InjectViewState
public class GenresPresenter extends MvpPresenter<MvpGenresView> {

    public void loadGenres() {
        if (NetworkUtils.notConnected()) {
            getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        GENRES service = ApiFactory.createService(GENRES.class);
        Call<MovieGenresResponse> call = service.getMovieList(Url.TMDB_API_KEY, Url.en_US);
        call.enqueue(new Callback<MovieGenresResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieGenresResponse> call, @NonNull Response<MovieGenresResponse> response) {
                if (!response.isSuccessful()) {
                    getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
                    return;
                }

                getViewState().showResults(response.body().genres);
            }

            @Override
            public void onFailure(@NonNull Call<MovieGenresResponse> call, @NonNull Throwable t) {
                getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
            }
        });
    }
}