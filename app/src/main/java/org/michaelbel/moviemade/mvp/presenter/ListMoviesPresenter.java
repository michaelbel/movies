package org.michaelbel.moviemade.mvp.presenter;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.michaelbel.moviemade.app.Url;
import org.michaelbel.moviemade.app.annotation.EmptyViewMode;
import org.michaelbel.moviemade.mvp.view.MvpResultsView;
import org.michaelbel.moviemade.rest.ApiFactory;
import org.michaelbel.moviemade.rest.TmdbObject;
import org.michaelbel.moviemade.rest.api.MOVIES;
import org.michaelbel.moviemade.rest.model.Movie;
import org.michaelbel.moviemade.rest.response.MovieResponse;
import org.michaelbel.moviemade.util.AndroidUtils;
import org.michaelbel.moviemade.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@InjectViewState
public class ListMoviesPresenter extends MvpPresenter<MvpResultsView> {

    public int page;
    public int totalPages;
    public boolean loading;
    public boolean loadingLocked;

    private int movieId;

    public void loadNowPlayingMovies(boolean firstpage) {
        if (NetworkUtils.notConnected()) {
            getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        if (firstpage) {
            page = 1;
            totalPages = 0;
            loading = false;
            loadingLocked = false;
        }

        MOVIES service = ApiFactory.createService(MOVIES.class);
        service.getNowPlaying(Url.TMDB_API_KEY, Url.en_US, page).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (!response.isSuccessful()) {
                    if (firstpage) {
                        getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
                    } else {
                        loadingLocked = true;
                    }

                    return;
                }

                if (firstpage) {
                    if (response.body() == null) {
                        getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
                        return;
                    }

                    totalPages = response.body().totalPages;
                }

                List<TmdbObject> results = new ArrayList<>();

                if (AndroidUtils.includeAdult()) {
                    results.addAll(response.body().movies);
                } else {
                    for (Movie movie : response.body().movies) {
                        if (!movie.adult) {
                            results.add(movie);
                        }
                    }
                }

                if (results.isEmpty()) {
                    if (firstpage) {
                        getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
                    }

                    return;
                }

                getViewState().showResults(results);
                page++;

                if (!firstpage) {
                    loading = false;
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                if (firstpage) {
                    getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
                } else {
                    loadingLocked = true;
                    loading = false;
                }
            }
        });

        if (!firstpage) {
            loading = true;
        }
    }
}