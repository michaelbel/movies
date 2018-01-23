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
import org.michaelbel.moviemade.rest.model.Movie;
import org.michaelbel.moviemade.rest.response.MoviePeopleResponse;
import org.michaelbel.moviemade.rest.response.MovieResponse;
import org.michaelbel.moviemade.util.AndroidUtils;
import org.michaelbel.moviemade.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class ListMoviesPresenter extends MvpPresenter<MvpResultsView> {

    public int page;
    public int totalPages;
    public boolean loading;
    public boolean loadingLocked;

    private Disposable disposable1;
    private Disposable disposable2;

    public void loadNowPlayingMovies() {
        if (NetworkUtils.notConnected()) {
            getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        page = 1;
        totalPages = 0;
        loading = false;
        loadingLocked = false;

        MOVIES service = ApiFactory.createService(MOVIES.class);
        Observable<MovieResponse> observable = service.getNowPlaying(Url.TMDB_API_KEY, Url.en_US, page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposable1 = observable.subscribeWith(new DisposableObserver<MovieResponse>() {
            @Override
            public void onNext(MovieResponse response) {
                totalPages = response.totalPages;

                List<TmdbObject> results = new ArrayList<>();

                if (AndroidUtils.includeAdult()) {
                    results.addAll(response.movies);
                } else {
                    for (Movie movie : response.movies) {
                        if (!movie.adult) {
                            results.add(movie);
                        }
                    }
                }

                if (results.isEmpty()) {
                    getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }

                getViewState().showResults(results);
                page++;
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
        Observable<MovieResponse> observable = service.getNowPlaying(Url.TMDB_API_KEY, Url.en_US, page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposable2 = observable.subscribeWith(new DisposableObserver<MovieResponse>() {
            @Override
            public void onNext(MovieResponse response) {
                List<TmdbObject> results = new ArrayList<>();

                if (AndroidUtils.includeAdult()) {
                    results.addAll(response.movies);
                } else {
                    for (Movie movie : response.movies) {
                        if (!movie.adult) {
                            results.add(movie);
                        }
                    }
                }

                getViewState().showResults(results);
                loading = false;
                page++;
            }

            @Override
            public void onError(Throwable e) {
                loadingLocked = true;
                loading = false;
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                disposable1.dispose();
            }
        });
        loading = true;
    }

    public void loadPopularMovies() {
        if (NetworkUtils.notConnected()) {
            getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        page = 1;
        totalPages = 0;
        loading = false;
        loadingLocked = false;

        MOVIES service = ApiFactory.createService(MOVIES.class);
        Observable<MovieResponse> observable = service.getPopular(Url.TMDB_API_KEY, Url.en_US, page)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposable1 = observable.subscribeWith(new DisposableObserver<MovieResponse>() {
            @Override
            public void onNext(MovieResponse response) {
                List<TmdbObject> results = new ArrayList<>();

                if (AndroidUtils.includeAdult()) {
                    results.addAll(response.movies);
                } else {
                    for (Movie movie : response.movies) {
                        if (!movie.adult) {
                            results.add(movie);
                        }
                    }
                }

                if (results.isEmpty()) {
                    getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }

                getViewState().showResults(results);
                totalPages = response.totalPages;
                page++;
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
        Observable<MovieResponse> observable = service.getPopular(Url.TMDB_API_KEY, Url.en_US, page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposable2 = observable.subscribeWith(new DisposableObserver<MovieResponse>() {
            @Override
            public void onNext(MovieResponse response) {
                List<TmdbObject> results = new ArrayList<>();

                if (AndroidUtils.includeAdult()) {
                    results.addAll(response.movies);
                } else {
                    for (Movie movie : response.movies) {
                        if (!movie.adult) {
                            results.add(movie);
                        }
                    }
                }

                if (results.isEmpty()) {
                    return;
                }

                getViewState().showResults(results);
                loading = false;
                page++;
            }

            @Override
            public void onError(Throwable e) {
                loadingLocked = true;
                loading = false;
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                disposable1.dispose();
            }
        });
        loading = true;
    }

    public void loadTopRatedMovies() {
        if (NetworkUtils.notConnected()) {
            getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        page = 1;
        totalPages = 0;
        loading = false;
        loadingLocked = false;

        MOVIES service = ApiFactory.createService(MOVIES.class);
        Observable<MovieResponse> observable = service.getTopRated(Url.TMDB_API_KEY, Url.en_US, page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposable1 = observable.subscribeWith(new DisposableObserver<MovieResponse>() {
            @Override
            public void onNext(MovieResponse response) {
                List<TmdbObject> results = new ArrayList<>();

                if (AndroidUtils.includeAdult()) {
                    results.addAll(response.movies);
                } else {
                    for (Movie movie : response.movies) {
                        if (!movie.adult) {
                            results.add(movie);
                        }
                    }
                }

                if (results.isEmpty()) {
                    getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }

                getViewState().showResults(results);
                totalPages = response.totalPages;
                page++;
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
        Observable<MovieResponse> observable = service.getTopRated(Url.TMDB_API_KEY, Url.en_US, page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposable2 = observable.subscribeWith(new DisposableObserver<MovieResponse>() {
            @Override
            public void onNext(MovieResponse response) {
                List<TmdbObject> results = new ArrayList<>();

                if (AndroidUtils.includeAdult()) {
                    results.addAll(response.movies);
                } else {
                    for (Movie movie : response.movies) {
                        if (!movie.adult) {
                            results.add(movie);
                        }
                    }
                }

                if (results.isEmpty()) {
                    return;
                }

                getViewState().showResults(results);
                loading = false;
                page++;
            }

            @Override
            public void onError(Throwable e) {
                loadingLocked = true;
                loading = false;
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                disposable1.dispose();
            }
        });
        loading = true;
    }

    public void loadUpcomingMovies() {
        if (NetworkUtils.notConnected()) {
            getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        page = 1;
        totalPages = 0;
        loading = false;
        loadingLocked = false;

        MOVIES service = ApiFactory.createService(MOVIES.class);
        Observable<MovieResponse> observable = service.getUpcoming(Url.TMDB_API_KEY, Url.en_US, page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposable1 = observable.subscribeWith(new DisposableObserver<MovieResponse>() {
            @Override
            public void onNext(MovieResponse response) {
                List<TmdbObject> results = new ArrayList<>();

                if (AndroidUtils.includeAdult()) {
                    results.addAll(response.movies);
                } else {
                    for (Movie movie : response.movies) {
                        if (!movie.adult) {
                            results.add(movie);
                        }
                    }
                }

                if (results.isEmpty()) {
                    getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }

                getViewState().showResults(results);
                totalPages = response.totalPages;
                page++;
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
        Observable<MovieResponse> observable = service.getUpcoming(Url.TMDB_API_KEY, Url.en_US, page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposable2 = observable.subscribeWith(new DisposableObserver<MovieResponse>() {
            @Override
            public void onNext(MovieResponse response) {
                List<TmdbObject> results = new ArrayList<>();

                if (AndroidUtils.includeAdult()) {
                    results.addAll(response.movies);
                } else {
                    for (Movie movie : response.movies) {
                        if (!movie.adult) {
                            results.add(movie);
                        }
                    }
                }

                if (results.isEmpty()) {
                    return;
                }

                getViewState().showResults(results);
                loading = false;
                page++;
            }

            @Override
            public void onError(Throwable e) {
                loadingLocked = true;
                loading = false;
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                disposable1.dispose();
            }
        });
        loading = true;
    }

    public void loadSimilarMovies(int movieId) {
        if (NetworkUtils.notConnected()) {
            getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        page = 1;
        totalPages = 0;
        loading = false;
        loadingLocked = false;

        MOVIES service = ApiFactory.createService(MOVIES.class);
        Observable<MovieResponse> observable = service.getSimilar(movieId, Url.TMDB_API_KEY, Url.en_US, page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposable1 = observable.subscribeWith(new DisposableObserver<MovieResponse>() {
            @Override
            public void onNext(MovieResponse response) {
                List<TmdbObject> results = new ArrayList<>();

                if (AndroidUtils.includeAdult()) {
                    results.addAll(response.movies);
                } else {
                    for (Movie movie : response.movies) {
                        if (!movie.adult) {
                            results.add(movie);
                        }
                    }
                }

                if (results.isEmpty()) {
                    getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }

                getViewState().showResults(results);
                totalPages = response.totalPages;
                page++;
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
        Observable<MovieResponse> observable = service.getSimilar(movieId, Url.TMDB_API_KEY, Url.en_US, page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposable2 = observable.subscribeWith(new DisposableObserver<MovieResponse>() {
            @Override
            public void onNext(MovieResponse response) {
                List<TmdbObject> results = new ArrayList<>();

                if (AndroidUtils.includeAdult()) {
                    results.addAll(response.movies);
                } else {
                    for (Movie movie : response.movies) {
                        if (!movie.adult) {
                            results.add(movie);
                        }
                    }
                }

                if (results.isEmpty()) {
                    return;
                }

                getViewState().showResults(results);
                loading = false;
                page++;
            }

            @Override
            public void onError(Throwable e) {
                loadingLocked = true;
                loading = false;
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                disposable1.dispose();
            }
        });
        loading = true;
    }

    public void loadRelatedMovies(int movieId) {
        if (NetworkUtils.notConnected()) {
            getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        page = 1;
        totalPages = 0;
        loading = false;
        loadingLocked = false;

        MOVIES service = ApiFactory.createService(MOVIES.class);
        Observable<MovieResponse> observable = service.getRecommendations(movieId, Url.TMDB_API_KEY, Url.en_US, page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposable1 = observable.subscribeWith(new DisposableObserver<MovieResponse>() {
            @Override
            public void onNext(MovieResponse response) {
                List<TmdbObject> results = new ArrayList<>();

                // todo Нужно избавиться от этих костылей перенести в адаптер
                if (AndroidUtils.includeAdult()) {
                    results.addAll(response.movies);
                } else {
                    for (Movie movie : response.movies) {
                        if (!movie.adult) {
                            results.add(movie);
                        }
                    }
                }

                if (results.isEmpty()) {
                    getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }

                getViewState().showResults(results);
                totalPages = response.totalPages;
                page++;
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
        Observable<MovieResponse> observable = service.getRecommendations(movieId, Url.TMDB_API_KEY, Url.en_US, page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposable2 = observable.subscribeWith(new DisposableObserver<MovieResponse>() {
            @Override
            public void onNext(MovieResponse response) {
                List<TmdbObject> results = new ArrayList<>();

                if (AndroidUtils.includeAdult()) {
                    results.addAll(response.movies);
                } else {
                    for (Movie movie : response.movies) {
                        if (!movie.adult) {
                            results.add(movie);
                        }
                    }
                }

                if (results.isEmpty()) {
                    return;
                }

                getViewState().showResults(results);
                loading = false;
                page++;
            }

            @Override
            public void onError(Throwable e) {
                loadingLocked = true;
                loading = false;
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                disposable1.dispose();
            }
        });
        loading = true;
    }

    public void loadPersonMovies(int castId) {
        if (NetworkUtils.notConnected()) {
            getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        PEOPLE service = ApiFactory.createService(PEOPLE.class);
        Observable<MoviePeopleResponse> observable = service.getMovieCredits(castId, Url.TMDB_API_KEY, Url.en_US).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposable1 = observable.subscribeWith(new DisposableObserver<MoviePeopleResponse>() {
            @Override
            public void onNext(MoviePeopleResponse response) {
                //List<Movie> crewMovies = response.body().crewMovies;
                List<TmdbObject> castMovies = new ArrayList<>();
                //castMovies.addAll(response.body().castMovies);

                if (AndroidUtils.includeAdult()) {
                    //movies.addAll(crewMovies);
                    castMovies.addAll(response.castMovies);
                } else {
                    for (Movie movie : response.castMovies) {
                        if (!movie.adult) {
                            castMovies.add(movie);
                        }
                    }
                }

                if (castMovies.isEmpty()) {
                    getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }

                getViewState().showResults(castMovies);
                page++;
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