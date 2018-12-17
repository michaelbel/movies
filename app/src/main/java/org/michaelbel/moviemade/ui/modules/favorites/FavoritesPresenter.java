package org.michaelbel.moviemade.ui.modules.favorites;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.michaelbel.moviemade.BuildConfig;
import org.michaelbel.moviemade.Moviemade;
import org.michaelbel.moviemade.data.constants.OrderKt;
import org.michaelbel.moviemade.data.entity.Movie;
import org.michaelbel.moviemade.data.service.AccountService;
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
public class FavoritesPresenter extends MvpPresenter<FavoritesMvp> {

    private int page;
    private CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    AccountService service;

    FavoritesPresenter() {
        Moviemade.getAppComponent().injest(this);
    }

    void getFavoriteMovies(int accountId, String sessionId) {
        // TODO include it into observable.
        if (NetworkUtil.INSTANCE.notConnected()) {
            getViewState().setError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        page = 1;
        // todo make map to check results.isEmpty()
        disposables.add(service.getFavoriteMovies(accountId, BuildConfig.TMDB_API_KEY, sessionId, TmdbConfigKt.en_US, OrderKt.ASC, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(response -> {
                List<Movie> results = new ArrayList<>(response.getMovies());
                if (results.isEmpty()) {
                    getViewState().setError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }
                getViewState().setMovies(results);
            }, e -> getViewState().setError(EmptyViewMode.MODE_NO_MOVIES)));

        //disposables.add(service.getFavoriteMovies(accountId, BuildConfig.TMDB_API_KEY, sessionId, TmdbConfigKt.en_US, OrderKt.ASC, page)
        //    .doOnSubscribe(disposable -> getViewState().showLoading())
        //    .doAfterTerminate(() -> getViewState().hideLoading())
        //    .subscribeOn(Schedulers.io())
        //    .observeOn(AndroidSchedulers.mainThread())
        //    .subscribe(response -> getViewState().setMovies(new ArrayList<>(response.getMovies())), e -> getViewState().setError(EmptyViewMode.MODE_NO_MOVIES)));
    }

    void getFavoriteMoviesNext(int accountId, String sessionId) {
        page++;
        disposables.add(service.getFavoriteMovies(accountId, BuildConfig.TMDB_API_KEY, sessionId, TmdbConfigKt.en_US, OrderKt.ASC, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(response -> {
                List<Movie> results = new ArrayList<>(response.getMovies());
                if (results.isEmpty()) {
                    getViewState().setError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }
                getViewState().setMovies(results);
            }, e -> {
                getViewState().setError(EmptyViewMode.MODE_NO_MOVIES);
                e.printStackTrace();
            }));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposables.dispose();
    }
}