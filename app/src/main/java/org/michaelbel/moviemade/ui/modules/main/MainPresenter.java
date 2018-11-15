package org.michaelbel.moviemade.ui.modules.main;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.michaelbel.moviemade.BuildConfig;
import org.michaelbel.moviemade.utils.ConstantsKt;
import org.michaelbel.moviemade.utils.EmptyViewMode;
import org.michaelbel.moviemade.data.dao.Cast;
import org.michaelbel.moviemade.data.dao.CreditsResponse;
import org.michaelbel.moviemade.data.dao.Movie;
import org.michaelbel.moviemade.data.dao.MoviesResponse;
import org.michaelbel.moviemade.data.service.MOVIES;
import org.michaelbel.moviemade.data.service.PEOPLE;
import org.michaelbel.moviemade.utils.ApiFactory;
import org.michaelbel.moviemade.utils.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainMvp> {

    public int page = 1;
    public int totalPages;
    public boolean isLoading = false;
    public boolean isLastPage = false;

    private final CompositeDisposable disposables = new CompositeDisposable();

    public void loadNowPlayingMovies() {
        if (NetworkUtil.INSTANCE.notConnected()) {
            getViewState().setError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        MOVIES service = ApiFactory.createService2(MOVIES.class);
        Observable<MoviesResponse> observable = service.getNowPlaying(BuildConfig.TMDB_API_KEY, ConstantsKt.en_US, page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposables.add(observable.subscribeWith(new DisposableObserver<MoviesResponse>() {
            @Override
            public void onNext(MoviesResponse response) {
                totalPages = response.getTotalPages();
                List<Movie> results = new ArrayList<>(response.getMovies());
                if (results.isEmpty()) {
                    getViewState().setError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }
                getViewState().setMovies(results, true);
            }

            @Override
            public void onError(Throwable e) {
                getViewState().setError(EmptyViewMode.MODE_NO_MOVIES);
            }

            @Override
            public void onComplete() {}
        }));
    }

    public void loadNowPlayingNextMovies() {
        MOVIES service = ApiFactory.createService2(MOVIES.class);
        Observable<MoviesResponse> observable = service.getNowPlaying(BuildConfig.TMDB_API_KEY, ConstantsKt.en_US, page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposables.add(observable.subscribeWith(new DisposableObserver<MoviesResponse>() {
            @Override
            public void onNext(MoviesResponse response) {
                List<Movie> results = new ArrayList<>(response.getMovies());
                getViewState().setMovies(results, false);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {}
        }));
    }

    public void loadTopRatedMovies() {
        if (NetworkUtil.INSTANCE.notConnected()) {
            getViewState().setError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        MOVIES service = ApiFactory.createService2(MOVIES.class);
        Observable<MoviesResponse> observable = service.getTopRated(BuildConfig.TMDB_API_KEY, ConstantsKt.en_US, page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposables.add(observable.subscribeWith(new DisposableObserver<MoviesResponse>() {
            @Override
            public void onNext(MoviesResponse response) {
                totalPages = response.getTotalPages();
                List<Movie> results = new ArrayList<>(response.getMovies());
                if (results.isEmpty()) {
                    getViewState().setError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }
                getViewState().setMovies(results, true);
            }

            @Override
            public void onError(Throwable e) {
                getViewState().setError(EmptyViewMode.MODE_NO_MOVIES);
            }

            @Override
            public void onComplete() {}
        }));
    }

    public void loadTopRatedNextMovies() {
        MOVIES service = ApiFactory.createService2(MOVIES.class);
        Observable<MoviesResponse> observable = service.getTopRated(BuildConfig.TMDB_API_KEY, ConstantsKt.en_US, page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposables.add(observable.subscribeWith(new DisposableObserver<MoviesResponse>() {
            @Override
            public void onNext(MoviesResponse response) {
                List<Movie> results = new ArrayList<>(response.getMovies());
                getViewState().setMovies(results, false);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {}
        }));
    }

    public void loadUpcomingMovies() {
        if (NetworkUtil.INSTANCE.notConnected()) {
            getViewState().setError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        MOVIES service = ApiFactory.createService2(MOVIES.class);
        Observable<MoviesResponse> observable = service.getUpcoming(BuildConfig.TMDB_API_KEY, ConstantsKt.en_US, page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposables.add(observable.subscribeWith(new DisposableObserver<MoviesResponse>() {
            @Override
            public void onNext(MoviesResponse response) {
                totalPages = response.getTotalPages();
                List<Movie> results = new ArrayList<>(response.getMovies());
                if (results.isEmpty()) {
                    getViewState().setError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }
                getViewState().setMovies(results, true);
            }

            @Override
            public void onError(Throwable e) {
                getViewState().setError(EmptyViewMode.MODE_NO_MOVIES);
            }

            @Override
            public void onComplete() {}
        }));
    }

    public void loadUpcomingNextMovies() {
        MOVIES service = ApiFactory.createService2(MOVIES.class);
        Observable<MoviesResponse> observable = service.getUpcoming(BuildConfig.TMDB_API_KEY, ConstantsKt.en_US, page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposables.add(observable.subscribeWith(new DisposableObserver<MoviesResponse>() {
            @Override
            public void onNext(MoviesResponse response) {
                List<Movie> results = new ArrayList<>(response.getMovies());
                getViewState().setMovies(results, false);
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