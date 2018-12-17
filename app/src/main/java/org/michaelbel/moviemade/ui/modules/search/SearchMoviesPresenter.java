package org.michaelbel.moviemade.ui.modules.search;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.michaelbel.moviemade.BuildConfig;
import org.michaelbel.moviemade.Moviemade;
import org.michaelbel.moviemade.data.entity.Movie;
import org.michaelbel.moviemade.data.service.SearchService;
import org.michaelbel.moviemade.utils.AdultUtil;
import org.michaelbel.moviemade.utils.EmptyViewMode;
import org.michaelbel.moviemade.utils.NetworkUtil;
import org.michaelbel.moviemade.utils.TmdbConfigKt;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class SearchMoviesPresenter extends MvpPresenter<SearchMvp> {

    @Inject
    SearchService service;

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
        disposables.add(service.searchMovies(BuildConfig.TMDB_API_KEY, TmdbConfigKt.en_US, query, page, AdultUtil.INSTANCE.includeAdult(Moviemade.appContext), "")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(moviesResponse -> {
                List<Movie> results = new ArrayList<>(moviesResponse.getMovies());
                if (results.isEmpty()) {
                    getViewState().setError(EmptyViewMode.MODE_NO_RESULTS);
                    return;
                }
                getViewState().setMovies(results);
            }, throwable -> getViewState().setError(EmptyViewMode.MODE_NO_RESULTS)));
    }

    void loadNextPage() {
        page++;
        disposables.add(service.searchMovies(BuildConfig.TMDB_API_KEY, TmdbConfigKt.en_US, currentQuery, page, AdultUtil.INSTANCE.includeAdult(Moviemade.appContext), null)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(moviesResponse -> getViewState().setMovies(moviesResponse.getMovies()), Throwable::printStackTrace));
    }

    @Override
    public void onDestroy() {
        disposables.dispose();
        super.onDestroy();
    }
}