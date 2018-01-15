package org.michaelbel.moviemade.mvp.presenter;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.michaelbel.moviemade.app.Url;
import org.michaelbel.moviemade.app.annotation.EmptyViewMode;
import org.michaelbel.moviemade.mvp.view.MvpGenreMoviesView;
import org.michaelbel.moviemade.rest.ApiFactory;
import org.michaelbel.moviemade.rest.api.GENRES;
import org.michaelbel.moviemade.rest.model.Movie;
import org.michaelbel.moviemade.rest.response.GenreResponse;
import org.michaelbel.moviemade.util.AndroidUtils;
import org.michaelbel.moviemade.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@InjectViewState
public class GenreMoviesPresenter extends MvpPresenter<MvpGenreMoviesView> {

    public int page;
    public int totalPages;
    public boolean loading;
    public boolean loadingLocked;

    private int genreId;

    public void loadMovies(int genreId) {
        this.genreId = genreId;

        if (genreId == 0) {
            getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
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

        GENRES service = ApiFactory.createService(GENRES.class);
        Call<GenreResponse> call =  service.getMovies(genreId, Url.TMDB_API_KEY, Url.en_US, AndroidUtils.includeAdult(), page);
        call.enqueue(new Callback<GenreResponse>() {
            @Override
            public void onResponse(@NonNull Call<GenreResponse> call, @NonNull Response<GenreResponse> response) {
                if (!response.isSuccessful()) {
                    getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }

                if (response.body() == null) {
                    getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }

                if (response.body().movies.isEmpty()) {
                    getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }

                totalPages = response.body().totalPages;

                List<Movie> newMovies = new ArrayList<>();
                newMovies.addAll(response.body().movies);

                getViewState().showResults(newMovies);
                page++;
            }

            @Override
            public void onFailure(@NonNull Call<GenreResponse> call, @NonNull Throwable t) {
                getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
            }
        });
    }

    public void loadResults() {
        GENRES service = ApiFactory.createService(GENRES.class);
        Call<GenreResponse> call =  service.getMovies(genreId, Url.TMDB_API_KEY, Url.en_US, AndroidUtils.includeAdult(), page);
        call.enqueue(new Callback<GenreResponse>() {
            @Override
            public void onResponse(@NonNull Call<GenreResponse> call, @NonNull Response<GenreResponse> response) {
                if (!response.isSuccessful()) {
                    loadingLocked = true;
                    return;
                }

                if (response.body().movies.isEmpty()) {
                    return;
                }

                List<Movie> newMovies = new ArrayList<>();
                newMovies.addAll(response.body().movies);

                getViewState().showResults(newMovies);
                page++;
                loading = false;
            }

            @Override
            public void onFailure(@NonNull Call<GenreResponse> call, @NonNull Throwable t) {
                loadingLocked = true;
                loading = false;
            }
        });

        loading = true;
    }
}