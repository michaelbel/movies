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
import org.michaelbel.moviemade.rest.api.PEOPLE;
import org.michaelbel.moviemade.rest.model.Movie;
import org.michaelbel.moviemade.rest.response.MoviePeopleResponse;
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
        Call<MovieResponse> call = service.getNowPlaying(Url.TMDB_API_KEY, Url.en_US, page);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (!response.isSuccessful()) {
                    getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }

                if (response.body() == null) {
                    getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }

                totalPages = response.body().totalPages;

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
                    getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }

                getViewState().showResults(results);
                page++;
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
            }
        });
    }

    public void loadNowPlayingNextMovies() {
        MOVIES service = ApiFactory.createService(MOVIES.class);
        Call<MovieResponse> call = service.getNowPlaying(Url.TMDB_API_KEY, Url.en_US, page);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (!response.isSuccessful()) {
                    loadingLocked = true;
                    return;
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
                    return;
                }

                getViewState().showResults(results);
                loading = false;
                page++;
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                loadingLocked = true;
                loading = false;
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
        Call<MovieResponse> call = service.getPopular(Url.TMDB_API_KEY, Url.en_US, page);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (!response.isSuccessful()) {
                    getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }

                if (response.body() == null) {
                    getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }

                totalPages = response.body().totalPages;

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
                    getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }

                getViewState().showResults(results);
                page++;
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
            }
        });
    }

    public void loadPopularNextMovies() {
        MOVIES service = ApiFactory.createService(MOVIES.class);
        Call<MovieResponse> call = service.getPopular(Url.TMDB_API_KEY, Url.en_US, page);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (!response.isSuccessful()) {
                    loadingLocked = true;
                    return;
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
                    return;
                }

                getViewState().showResults(results);
                loading = false;
                page++;
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                loadingLocked = true;
                loading = false;
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
        Call<MovieResponse> call = service.getTopRated(Url.TMDB_API_KEY, Url.en_US, page);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (!response.isSuccessful()) {
                    getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }

                if (response.body() == null) {
                    getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }

                totalPages = response.body().totalPages;

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
                    getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }

                getViewState().showResults(results);
                page++;
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
            }
        });
    }

    public void loadTopRatedNextMovies() {
        MOVIES service = ApiFactory.createService(MOVIES.class);
        Call<MovieResponse> call = service.getTopRated(Url.TMDB_API_KEY, Url.en_US, page);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (!response.isSuccessful()) {
                    loadingLocked = true;
                    return;
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
                    return;
                }

                getViewState().showResults(results);
                loading = false;
                page++;
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                loadingLocked = true;
                loading = false;
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
        Call<MovieResponse> call = service.getUpcoming(Url.TMDB_API_KEY, Url.en_US, page);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (!response.isSuccessful()) {
                    getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }

                if (response.body() == null) {
                    getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }

                totalPages = response.body().totalPages;

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
                    getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }

                getViewState().showResults(results);
                page++;
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
            }
        });
    }

    public void loadUpcomingNextMovies() {
        MOVIES service = ApiFactory.createService(MOVIES.class);
        Call<MovieResponse> call = service.getUpcoming(Url.TMDB_API_KEY, Url.en_US, page);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (!response.isSuccessful()) {
                    loadingLocked = true;
                    return;
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
                    return;
                }

                getViewState().showResults(results);
                loading = false;
                page++;
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                loadingLocked = true;
                loading = false;
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
        Call<MovieResponse> call = service.getSimilar(movieId, Url.TMDB_API_KEY, Url.en_US, page);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (!response.isSuccessful()) {
                    getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }

                if (response.body() == null) {
                    getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }

                totalPages = response.body().totalPages;

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
                    getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }

                getViewState().showResults(results);
                page++;
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
            }
        });
    }

    public void loadSimilarNextMovies(int movieId) {
        MOVIES service = ApiFactory.createService(MOVIES.class);
        Call<MovieResponse> call = service.getSimilar(movieId, Url.TMDB_API_KEY, Url.en_US, page);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (!response.isSuccessful()) {
                    loadingLocked = true;
                    return;
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
                    return;
                }

                getViewState().showResults(results);
                loading = false;
                page++;
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                loadingLocked = true;
                loading = false;
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
        Call<MovieResponse> call = service.getRecommendations(movieId, Url.TMDB_API_KEY, Url.en_US, page);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (!response.isSuccessful()) {
                    getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }

                if (response.body() == null) {
                    getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }

                totalPages = response.body().totalPages;

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
                    getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }

                getViewState().showResults(results);
                page++;
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
            }
        });
    }

    public void loadRelatedNextMovies(int movieId) {
        MOVIES service = ApiFactory.createService(MOVIES.class);
        Call<MovieResponse> call = service.getRecommendations(movieId, Url.TMDB_API_KEY, Url.en_US, page);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (!response.isSuccessful()) {
                    loadingLocked = true;
                    return;
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
                    return;
                }

                getViewState().showResults(results);
                loading = false;
                page++;
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                loadingLocked = true;
                loading = false;
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
        service.getMovieCredits(castId, Url.TMDB_API_KEY, Url.en_US).enqueue(new Callback<MoviePeopleResponse>() {
            @Override
            public void onResponse(@NonNull Call<MoviePeopleResponse> call, @NonNull Response<MoviePeopleResponse> response) {
                if (!response.isSuccessful()) {
                    getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }

                if (response.body() == null) {
                    getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }

                //List<Movie> crewMovies = response.body().crewMovies;
                List<TmdbObject> castMovies = new ArrayList<>();
                //castMovies.addAll(response.body().castMovies);

                if (AndroidUtils.includeAdult()) {
                    //movies.addAll(crewMovies);
                    castMovies.addAll(response.body().castMovies);
                } else {
                    for (Movie movie : response.body().castMovies) {
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
            public void onFailure(@NonNull Call<MoviePeopleResponse> call, @NonNull Throwable t) {
                getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
            }
        });
    }
}