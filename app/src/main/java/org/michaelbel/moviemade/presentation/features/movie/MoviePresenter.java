package org.michaelbel.moviemade.presentation.features.movie;

import android.content.SharedPreferences;

import org.jetbrains.annotations.NotNull;
import org.michaelbel.moviemade.core.entity.AccountStates;
import org.michaelbel.moviemade.core.entity.Cast;
import org.michaelbel.moviemade.core.entity.CreditsResponse;
import org.michaelbel.moviemade.core.entity.Crew;
import org.michaelbel.moviemade.core.entity.Movie;
import org.michaelbel.moviemade.core.remote.AccountService;
import org.michaelbel.moviemade.core.remote.MoviesService;
import org.michaelbel.moviemade.core.utils.AndroidUtil;
import org.michaelbel.moviemade.core.utils.DateUtil;
import org.michaelbel.moviemade.core.utils.NetworkUtil;
import org.michaelbel.moviemade.core.utils.SharedPrefsKt;
import org.michaelbel.moviemade.presentation.base.Presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

public class MoviePresenter extends Presenter implements MovieContract.Presenter {

    private MovieContract.View view;
    private MovieContract.Repository repository;
    private SharedPreferences preferences;

    MoviePresenter(MovieContract.View view, MoviesService moviesService, AccountService accountService, SharedPreferences prefs) {
        this.view = view;
        this.repository = new MovieRepository(moviesService, accountService);
        this.preferences = prefs;
    }

    @Override
    public void attach(@NotNull MovieContract.View view) {
        this.view = view;
    }

    @Override
    public void setDetailExtra(@NotNull Movie movie) {
        view.setPoster(movie.getPosterPath());
        view.setMovieTitle(movie.getTitle());
        view.setOverview(movie.getOverview());
        view.setVoteAverage(movie.getVoteAverage());
        view.setVoteCount(movie.getVoteCount());
        view.setReleaseDate(DateUtil.INSTANCE.formatReleaseDate(movie.getReleaseDate()));
        view.setGenres(movie.getGenreIds());
    }

    @Override
    public void getDetails(int movieId) {
        if (!NetworkUtil.INSTANCE.isNetworkConnected()) {
            view.setConnectionError();
            return;
        }

        getDisposable().add(repository.getDetails(movieId)
            .subscribeWith(new DisposableObserver<Movie>() {
                @Override
                public void onNext(Movie movie) {
                    view.setRuntime(movie.getRuntime() != 0 ? DateUtil.INSTANCE.formatRuntime(movie.getRuntime()) : null);
                    view.setOriginalLanguage(AndroidUtil.INSTANCE.formatCountries(movie.getCountries()));
                    view.setTagline(movie.getTagline());
                    view.setURLs(movie.getImdbId(), movie.getHomepage());
                    fixCredits(movie.getCredits());
                    view.showComplete(movie);
                }

                @Override
                public void onError(Throwable e) {
                    view.setConnectionError();
                }

                @Override
                public void onComplete() {
                    getAccountStates(movieId);
                }
            }));
    }

    @Override
    public void markFavorite(int accountId, int mediaId, boolean favorite) {
        if (!NetworkUtil.INSTANCE.isNetworkConnected()) return;

        getDisposable().add(repository.markFavorite(accountId, preferences.getString(SharedPrefsKt.KEY_SESSION_ID, ""), mediaId, favorite)
            .subscribe(mark -> view.onFavoriteChanged(mark), throwable -> view.setConnectionError()));
    }

    @Override
    public void addWatchlist(int accountId, int mediaId, boolean watchlist) {
        if (!NetworkUtil.INSTANCE.isNetworkConnected()) return;

        getDisposable().add(repository.addWatchlist(accountId, preferences.getString(SharedPrefsKt.KEY_SESSION_ID, ""), mediaId, watchlist)
            .subscribe(mark -> view.onWatchListChanged(mark), throwable -> view.setConnectionError()));
    }

    @Override
    public void getAccountStates(int movieId) {
        getDisposable().add(repository.getAccountStates(movieId, preferences.getString(SharedPrefsKt.KEY_SESSION_ID, ""))
            .subscribeWith(new DisposableObserver<AccountStates>() {
                @Override
                public void onNext(AccountStates states) {
                    if (states != null) {
                        view.setStates(states.getFavorite(), states.getWatchlist());
                    }
                }

                @Override
                public void onError(Throwable e) {
                    // Fixme: Rated object has an error.
                }

                @Override
                public void onComplete() {}
            }));
    }

    private void fixCredits(CreditsResponse creditsResponse) {
        List<String> actors = new ArrayList<>();
        for (Cast cast : creditsResponse.getCast()) {
            actors.add(cast.getName());
        }
        StringBuilder actorsBuilder = new StringBuilder();
        for (String name: actors) {
            actorsBuilder.append(name);
            if (!Objects.equals(name, actors.get(actors.size() - 1))) {
                actorsBuilder.append(", ");
            }
        }

        List<String> directors = new ArrayList<>();
        List<String> writers = new ArrayList<>();
        List<String> producers = new ArrayList<>();
        for (Crew crew : creditsResponse.getCrew()) {
            switch (crew.getDepartment()) {
                case Crew.Credits.DIRECTING:
                    directors.add(crew.getName());
                    break;
                case Crew.Credits.WRITING:
                    writers.add(crew.getName());
                    break;
                case Crew.Credits.PRODUCTION:
                    producers.add(crew.getName());
                    break;
            }
        }

        StringBuilder directorsBuilder = new StringBuilder();
        for (int i = 0; i < directors.size(); i++) {
            directorsBuilder.append(directors.get(i));
            // if item is not last and is not empty
            if (i != directors.size() - 1 && (directors.get(directors.size() - 1) != null)) {
                directorsBuilder.append(", ");
            }
        }

        StringBuilder writersBuilder = new StringBuilder();
        for (int i = 0; i < writers.size(); i++) {
            writersBuilder.append(writers.get(i));
            if (i != writers.size() - 1 && (writers.get(writers.size() - 1) != null)) {
                writersBuilder.append(", ");
            }
        }

        StringBuilder producersBuilder = new StringBuilder();
        for (int i = 0; i < producers.size(); i++) {
            producersBuilder.append(producers.get(i));
            if (i != producers.size() - 1 && (producers.get(producers.size() - 1) != null)) {
                producersBuilder.append(", ");
            }
        }

        view.setCredits(actorsBuilder.toString(), directorsBuilder.toString(), writersBuilder.toString(), producersBuilder.toString());
    }

    @Override
    public void destroy() {
        getDisposable().dispose();
    }
}