package org.michaelbel.moviemade.ui.modules.search;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.michaelbel.moviemade.BuildConfig;
import org.michaelbel.moviemade.Moviemade;
import org.michaelbel.moviemade.data.entity.Movie;
import org.michaelbel.moviemade.data.entity.MoviesResponse;
import org.michaelbel.moviemade.data.service.SEARCH;
import org.michaelbel.moviemade.utils.AdultUtil;
import org.michaelbel.moviemade.utils.EmptyViewMode;
import org.michaelbel.moviemade.utils.NetworkUtil;
import org.michaelbel.moviemade.utils.TmdbConfigKt;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class SearchMoviesPresenter extends MvpPresenter<SearchMvp> {

    @Inject SEARCH service;

    private int page;
    private String currentQuery;
    private final CompositeDisposable disposables = new CompositeDisposable();

    SearchMoviesPresenter() {
        Moviemade.getAppComponent().injest(this);
    }

    public void search(String query) {
        currentQuery = query;
        getViewState().searchStart();

        if (NetworkUtil.INSTANCE.notConnected()) {
            getViewState().setError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        page = 1;
        Observable<MoviesResponse> observable = service.searchMovies(BuildConfig.TMDB_API_KEY, TmdbConfigKt.en_US, query, page, AdultUtil.INSTANCE.includeAdult(Moviemade.appContext), "").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposables.add(observable.subscribeWith(new DisposableObserver<MoviesResponse>() {
            @Override
            public void onNext(MoviesResponse response) {
                List<Movie> results = new ArrayList<>(response.getMovies());
                if (results.isEmpty()) {
                    getViewState().setError(EmptyViewMode.MODE_NO_RESULTS);
                    return;
                }
                getViewState().setMovies(results);
            }

            @Override
            public void onError(Throwable e) {
                getViewState().setError(EmptyViewMode.MODE_NO_RESULTS);
            }

            @Override
            public void onComplete() {}
        }));
    }

    void loadNextPage() {
        page++;
        Observable<MoviesResponse> observable = service.searchMovies(BuildConfig.TMDB_API_KEY, TmdbConfigKt.en_US, currentQuery, page, AdultUtil.INSTANCE.includeAdult(Moviemade.appContext), null).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposables.add(observable.subscribeWith(new DisposableObserver<MoviesResponse>() {
            @Override
            public void onNext(MoviesResponse response) {
                getViewState().setMovies(response.getMovies());
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {}
        }));
    }

    @Override
    public void onDestroy() {
        disposables.dispose();
        super.onDestroy();
    }
}