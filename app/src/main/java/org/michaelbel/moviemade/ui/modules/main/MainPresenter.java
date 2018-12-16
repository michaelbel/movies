package org.michaelbel.moviemade.ui.modules.main;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.michaelbel.moviemade.BuildConfig;
import org.michaelbel.moviemade.Moviemade;
import org.michaelbel.moviemade.data.entity.Movie;
import org.michaelbel.moviemade.data.service.MOVIES;
import org.michaelbel.moviemade.utils.EmptyViewMode;
import org.michaelbel.moviemade.utils.NetworkUtil;
import org.michaelbel.moviemade.utils.RxUtil;
import org.michaelbel.moviemade.utils.TmdbConfigKt;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainMvp> {

    private int page;
    private Disposable nowPlayingSubscription;
    private Disposable nowPlayingSubscription2;
    private Disposable topRatedSubscription;
    private Disposable topRatedSubscription2;
    private Disposable upcomingSubscription;
    private Disposable upcomingSubscription2;

    @Inject MOVIES service;

    public MainPresenter() {
        Moviemade.getAppComponent().injest(this);
    }

    public void getNowPlaying() {
        if (NetworkUtil.INSTANCE.notConnected()) {
            getViewState().setError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        page = 1;
        nowPlayingSubscription = service.getNowPlaying(BuildConfig.TMDB_API_KEY, TmdbConfigKt.en_US, page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            /*.doFinally(new Action() {
                @Override
                public void run() {
                    do smth.
                }
            })*/
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
            });
    }

    public void getNowPlayingNext() {
        page++;
        nowPlayingSubscription2 = service.getNowPlaying(BuildConfig.TMDB_API_KEY, TmdbConfigKt.en_US, page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(response -> {
                List<Movie> results = new ArrayList<>(response.getMovies());
                if (results.isEmpty()) {
                    getViewState().setError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }
                getViewState().setMovies(results);
            });
    }

    public void getTopRated() {
        if (NetworkUtil.INSTANCE.notConnected()) {
            getViewState().setError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        page = 1;
        topRatedSubscription = service.getTopRated(BuildConfig.TMDB_API_KEY, TmdbConfigKt.en_US, page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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
            });
    }

    public void getTopRatedNext() {
        page++;
        topRatedSubscription2 = service.getTopRated(BuildConfig.TMDB_API_KEY, TmdbConfigKt.en_US, page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(response -> {
                List<Movie> results = new ArrayList<>(response.getMovies());
                if (results.isEmpty()) {
                    getViewState().setError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }
                getViewState().setMovies(results);
            });
    }

    public void getUpcoming() {
        if (NetworkUtil.INSTANCE.notConnected()) {
            getViewState().setError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        page = 1;
        upcomingSubscription = service.getUpcoming(BuildConfig.TMDB_API_KEY, TmdbConfigKt.en_US, page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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
            });
    }

    public void getUpcomingNext() {
        page++;
        upcomingSubscription2 = service.getUpcoming(BuildConfig.TMDB_API_KEY, TmdbConfigKt.en_US, page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(response -> {
                List<Movie> results = new ArrayList<>(response.getMovies());
                if (results.isEmpty()) {
                    getViewState().setError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }
                getViewState().setMovies(results);
            });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxUtil.INSTANCE.unsubscribe(nowPlayingSubscription);
        RxUtil.INSTANCE.unsubscribe(nowPlayingSubscription2);
        RxUtil.INSTANCE.unsubscribe(topRatedSubscription);
        RxUtil.INSTANCE.unsubscribe(topRatedSubscription2);
        RxUtil.INSTANCE.unsubscribe(upcomingSubscription);
        RxUtil.INSTANCE.unsubscribe(upcomingSubscription2);
    }
}