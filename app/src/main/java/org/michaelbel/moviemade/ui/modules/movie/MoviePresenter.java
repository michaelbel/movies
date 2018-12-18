package org.michaelbel.moviemade.ui.modules.movie;

import android.content.SharedPreferences;

import org.jetbrains.annotations.NotNull;
import org.michaelbel.moviemade.data.constants.CreditsKt;
import org.michaelbel.moviemade.data.entity.AccountStates;
import org.michaelbel.moviemade.data.entity.Cast;
import org.michaelbel.moviemade.data.entity.CreditsResponse;
import org.michaelbel.moviemade.data.entity.Crew;
import org.michaelbel.moviemade.data.entity.Movie;
import org.michaelbel.moviemade.data.service.AccountService;
import org.michaelbel.moviemade.data.service.MoviesService;
import org.michaelbel.moviemade.utils.DateUtil;
import org.michaelbel.moviemade.utils.LanguageUtil;
import org.michaelbel.moviemade.utils.NetworkUtil;
import org.michaelbel.moviemade.utils.SharedPrefsKt;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

public class MoviePresenter implements MovieContract.Presenter {

    private MovieContract.View view;
    private MovieContract.Repository repository;
    private SharedPreferences preferences;
    private final CompositeDisposable disposables = new CompositeDisposable();

    MoviePresenter(MovieContract.View view, MoviesService moviesService, AccountService accountService, SharedPreferences prefs) {
        this.view = view;
        this.repository = new MovieRepository(moviesService, accountService);
        this.preferences = prefs;
    }

    @Override
    public void setDetailExtra(@NotNull Movie movie) {
        view.setPoster(movie.getPosterPath());
        view.setMovieTitle(movie.getTitle());
        view.setOverview(movie.getOverview());
        view.setVoteAverage(movie.getVoteAverage());
        view.setVoteCount(movie.getVoteCount());
        view.setReleaseDate(DateUtil.INSTANCE.formatReleaseDate(movie.getReleaseDate()));
        view.setOriginalLanguage(LanguageUtil.INSTANCE.formatLanguage(movie.getOriginalLanguage()));
        view.setGenres(movie.getGenreIds());
    }

    @Override
    public void getDetails(int movieId) {
        // Fixme.
        if (NetworkUtil.INSTANCE.notConnected()) {
            view.setConnectionError();
            return;
        }

        disposables.add(repository.getDetails(movieId)
            .subscribeWith(new DisposableObserver<Movie>() {
                @Override
                public void onNext(Movie movie) {
                    view.setRuntime((Objects.requireNonNull(movie.getRuntime() != 0 ? DateUtil.INSTANCE.formatRuntime(movie.getRuntime()) : null)));
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
        disposables.add(repository.markFavorite(accountId, preferences.getString(SharedPrefsKt.KEY_SESSION_ID, ""), mediaId, favorite)
            .subscribe(mark -> view.onFavoriteChanged(mark), throwable -> view.setConnectionError()));
    }

    @Override
    public void addWatchlist(int accountId, int mediaId, boolean watchlist) {
        disposables.add(repository.addWatchlist(accountId, preferences.getString(SharedPrefsKt.KEY_SESSION_ID, ""), mediaId, watchlist)
            .subscribe(mark -> view.onWatchListChanged(mark), throwable -> view.setConnectionError()));
    }

    @Override
    public void getAccountStates(int movieId) {
        disposables.add(repository.getAccountStates(movieId, preferences.getString(SharedPrefsKt.KEY_SESSION_ID, ""))
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
                case CreditsKt.DIRECTING:
                    directors.add(crew.getName());
                    break;
                case CreditsKt.WRITING:
                    writers.add(crew.getName());
                    break;
                case CreditsKt.PRODUCTION:
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
    public void onDestroy() {
        disposables.dispose();
    }
}