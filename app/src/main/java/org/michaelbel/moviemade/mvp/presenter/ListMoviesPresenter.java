package org.michaelbel.moviemade.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.michaelbel.moviemade.app.Url;
import org.michaelbel.moviemade.app.annotation.EmptyViewMode;
import org.michaelbel.moviemade.mvp.view.MvpResultsView;
import org.michaelbel.moviemade.rest.ApiFactory;
import org.michaelbel.moviemade.rest.TmdbObject;
import org.michaelbel.moviemade.rest.api.MOVIES;
import org.michaelbel.moviemade.rest.api.PEOPLE;
import org.michaelbel.moviemade.rest.response.MoviePeopleResponse;
import org.michaelbel.moviemade.rest.response.MovieResponse;
import org.michaelbel.moviemade.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

@InjectViewState
public class ListMoviesPresenter extends MvpPresenter<MvpResultsView> {

    public int page = 1;
    public int totalPages;
    public boolean isLoading = false;
    public boolean isLastPage = false;

    private Disposable disposable1, disposable2;

    public void loadNowPlayingMovies() {
        if (NetworkUtils.notConnected()) {
            getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        MOVIES service = ApiFactory.createService(MOVIES.class);
        Observable<MovieResponse> observable = service.getNowPlaying(Url.TMDB_API_KEY, Url.en_US, page).observeOn(AndroidSchedulers.mainThread());
        disposable1 = observable.subscribeWith(new DisposableObserver<MovieResponse>() {
            @Override
            public void onNext(MovieResponse response) {
                totalPages = response.totalPages;
                List<TmdbObject> results = new ArrayList<>(response.movies);
                if (results.isEmpty()) {
                    getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }
                getViewState().showResults(results, true);
            }

            @Override
            public void onError(Throwable e) {
                getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
                e.printStackTrace();
            }

            @Override
            public void onComplete() {}
        });
    }

    public void loadNowPlayingNextMovies() {
        MOVIES service = ApiFactory.createService(MOVIES.class);
        Observable<MovieResponse> observable = service.getNowPlaying(Url.TMDB_API_KEY, Url.en_US, page).observeOn(AndroidSchedulers.mainThread());
        disposable2 = observable.subscribeWith(new DisposableObserver<MovieResponse>() {
            @Override
            public void onNext(MovieResponse response) {
                List<TmdbObject> results = new ArrayList<>(response.movies);
                getViewState().showResults(results, false);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                disposable1.dispose();
            }
        });
    }

    public void loadPopularMovies() {
        if (NetworkUtils.notConnected()) {
            getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        MOVIES service = ApiFactory.createService(MOVIES.class);
        Observable<MovieResponse> observable = service.getPopular(Url.TMDB_API_KEY, Url.en_US, page).observeOn(AndroidSchedulers.mainThread());
        disposable1 = observable.subscribeWith(new DisposableObserver<MovieResponse>() {
            @Override
            public void onNext(MovieResponse response) {
                totalPages = response.totalPages;
                List<TmdbObject> results = new ArrayList<>(response.movies);
                if (results.isEmpty()) {
                    getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }
                getViewState().showResults(results, true);
            }

            @Override
            public void onError(Throwable e) {
                getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
                e.printStackTrace();
            }

            @Override
            public void onComplete() {}
        });
    }

    public void loadPopularNextMovies() {
        MOVIES service = ApiFactory.createService(MOVIES.class);
        Observable<MovieResponse> observable = service.getPopular(Url.TMDB_API_KEY, Url.en_US, page).observeOn(AndroidSchedulers.mainThread());
        disposable2 = observable.subscribeWith(new DisposableObserver<MovieResponse>() {
            @Override
            public void onNext(MovieResponse response) {
                List<TmdbObject> results = new ArrayList<>(response.movies);
                getViewState().showResults(results, false);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                disposable1.dispose();
            }
        });
    }

    public void loadTopRatedMovies() {
        if (NetworkUtils.notConnected()) {
            getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        MOVIES service = ApiFactory.createService(MOVIES.class);
        Observable<MovieResponse> observable = service.getTopRated(Url.TMDB_API_KEY, Url.en_US, page).observeOn(AndroidSchedulers.mainThread());
        disposable1 = observable.subscribeWith(new DisposableObserver<MovieResponse>() {
            @Override
            public void onNext(MovieResponse response) {
                totalPages = response.totalPages;
                List<TmdbObject> results = new ArrayList<>(response.movies);
                if (results.isEmpty()) {
                    getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }
                getViewState().showResults(results, true);
            }

            @Override
            public void onError(Throwable e) {
                getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
                e.printStackTrace();
            }

            @Override
            public void onComplete() {}
        });
    }

    public void loadTopRatedNextMovies() {
        MOVIES service = ApiFactory.createService(MOVIES.class);
        Observable<MovieResponse> observable = service.getTopRated(Url.TMDB_API_KEY, Url.en_US, page).observeOn(AndroidSchedulers.mainThread());
        disposable2 = observable.subscribeWith(new DisposableObserver<MovieResponse>() {
            @Override
            public void onNext(MovieResponse response) {
                List<TmdbObject> results = new ArrayList<>(response.movies);
                getViewState().showResults(results, false);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                disposable1.dispose();
            }
        });
    }

    public void loadUpcomingMovies() {
        if (NetworkUtils.notConnected()) {
            getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        MOVIES service = ApiFactory.createService(MOVIES.class);
        Observable<MovieResponse> observable = service.getUpcoming(Url.TMDB_API_KEY, Url.en_US, page).observeOn(AndroidSchedulers.mainThread());
        disposable1 = observable.subscribeWith(new DisposableObserver<MovieResponse>() {
            @Override
            public void onNext(MovieResponse response) {
                totalPages = response.totalPages;
                List<TmdbObject> results = new ArrayList<>(response.movies);
                if (results.isEmpty()) {
                    getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }
                getViewState().showResults(results, true);
            }

            @Override
            public void onError(Throwable e) {
                getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
                e.printStackTrace();
            }

            @Override
            public void onComplete() {}
        });
    }

    public void loadUpcomingNextMovies() {
        MOVIES service = ApiFactory.createService(MOVIES.class);
        Observable<MovieResponse> observable = service.getUpcoming(Url.TMDB_API_KEY, Url.en_US, page).observeOn(AndroidSchedulers.mainThread());
        disposable2 = observable.subscribeWith(new DisposableObserver<MovieResponse>() {
            @Override
            public void onNext(MovieResponse response) {
                List<TmdbObject> results = new ArrayList<>(response.movies);
                getViewState().showResults(results, false);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                disposable1.dispose();
            }
        });
    }

    public void loadSimilarMovies(int movieId) {
        if (NetworkUtils.notConnected()) {
            getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        MOVIES service = ApiFactory.createService(MOVIES.class);
        Observable<MovieResponse> observable = service.getSimilar(movieId, Url.TMDB_API_KEY, Url.en_US, page).observeOn(AndroidSchedulers.mainThread());
        disposable1 = observable.subscribeWith(new DisposableObserver<MovieResponse>() {
            @Override
            public void onNext(MovieResponse response) {
                totalPages = response.totalPages;
                List<TmdbObject> results = new ArrayList<>(response.movies);
                if (results.isEmpty()) {
                    getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }
                getViewState().showResults(results, true);
            }

            @Override
            public void onError(Throwable e) {
                getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
                e.printStackTrace();
            }

            @Override
            public void onComplete() {}
        });
    }

    public void loadSimilarNextMovies(int movieId) {
        MOVIES service = ApiFactory.createService(MOVIES.class);
        Observable<MovieResponse> observable = service.getSimilar(movieId, Url.TMDB_API_KEY, Url.en_US, page).observeOn(AndroidSchedulers.mainThread());
        disposable2 = observable.subscribeWith(new DisposableObserver<MovieResponse>() {
            @Override
            public void onNext(MovieResponse response) {
                List<TmdbObject> results = new ArrayList<>(response.movies);
                getViewState().showResults(results, false);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                disposable1.dispose();
            }
        });
    }

    public void loadRelatedMovies(int movieId) {
        if (NetworkUtils.notConnected()) {
            getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        MOVIES service = ApiFactory.createService(MOVIES.class);
        Observable<MovieResponse> observable = service.getRecommendations(movieId, Url.TMDB_API_KEY, Url.en_US, page).observeOn(AndroidSchedulers.mainThread());
        disposable1 = observable.subscribeWith(new DisposableObserver<MovieResponse>() {
            @Override
            public void onNext(MovieResponse response) {
                totalPages = response.totalPages;
                List<TmdbObject> results = new ArrayList<>(response.movies);
                if (results.isEmpty()) {
                    getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }
                getViewState().showResults(results, true);
            }

            @Override
            public void onError(Throwable e) {
                getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
                e.printStackTrace();
            }

            @Override
            public void onComplete() {}
        });
    }

    public void loadRelatedNextMovies(int movieId) {
        MOVIES service = ApiFactory.createService(MOVIES.class);
        Observable<MovieResponse> observable = service.getRecommendations(movieId, Url.TMDB_API_KEY, Url.en_US, page).observeOn(AndroidSchedulers.mainThread());
        disposable2 = observable.subscribeWith(new DisposableObserver<MovieResponse>() {
            @Override
            public void onNext(MovieResponse response) {
                List<TmdbObject> results = new ArrayList<>(response.movies);
                getViewState().showResults(results, false);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                disposable1.dispose();
            }
        });
    }

    public void loadPersonMovies(int castId) {
        if (NetworkUtils.notConnected()) {
            getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        PEOPLE service = ApiFactory.createService(PEOPLE.class);
        Observable<MoviePeopleResponse> observable = service.getMovieCredits(castId, Url.TMDB_API_KEY, Url.en_US).observeOn(AndroidSchedulers.mainThread());
        disposable1 = observable.subscribeWith(new DisposableObserver<MoviePeopleResponse>() {
            @Override
            public void onNext(MoviePeopleResponse response) {
                List<TmdbObject> results = new ArrayList<>(response.castMovies);
                if (results.isEmpty()) {
                    getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }
                getViewState().showResults(results, true);
            }

            @Override
            public void onError(Throwable e) {
                getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
                e.printStackTrace();
            }

            @Override
            public void onComplete() {}
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (disposable1 != null && !disposable1.isDisposed()) {
            disposable1.dispose();
        }

        if (disposable2 != null && !disposable2.isDisposed()) {
            disposable2.dispose();
        }
    }
}