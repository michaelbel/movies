package org.michaelbel.moviemade.ui.modules.movie;

import org.jetbrains.annotations.NotNull;
import org.michaelbel.moviemade.BuildConfig;
import org.michaelbel.moviemade.data.constants.MediaTypeKt;
import org.michaelbel.moviemade.data.entity.AccountStates;
import org.michaelbel.moviemade.data.entity.Fave;
import org.michaelbel.moviemade.data.entity.Mark;
import org.michaelbel.moviemade.data.entity.Movie;
import org.michaelbel.moviemade.data.entity.Watch;
import org.michaelbel.moviemade.data.service.AccountService;
import org.michaelbel.moviemade.data.service.MoviesService;
import org.michaelbel.moviemade.utils.TmdbConfigKt;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MovieRepository implements MovieContract.Repository {

    private MoviesService moviesService;
    private AccountService accountService;

    MovieRepository(MoviesService moviesService, AccountService accountService) {
        this.moviesService = moviesService;
        this.accountService = accountService;
    }

    @NotNull
    @Override
    public Observable<Movie> getDetails(int movieId) {
        return moviesService.getDetails(movieId, BuildConfig.TMDB_API_KEY, TmdbConfigKt.en_US, MediaTypeKt.CREDITS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    }

    @NotNull
    @Override
    public Observable<Mark> markFavorite(int accountId, @NotNull String sessionId, int mediaId, boolean favorite) {
        return accountService.markAsFavorite(TmdbConfigKt.CONTENT_TYPE, accountId, BuildConfig.TMDB_API_KEY, sessionId, new Fave(MediaTypeKt.MOVIE, mediaId, favorite))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    }

    @NotNull
    @Override
    public Observable<Mark> addWatchlist(int accountId, @NotNull String sessionId, int mediaId, boolean watchlist) {
        return accountService.addToWatchlist(TmdbConfigKt.CONTENT_TYPE, accountId, BuildConfig.TMDB_API_KEY, sessionId, new Watch(MediaTypeKt.MOVIE, mediaId, watchlist))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    }

    @NotNull
    @Override
    public Observable<AccountStates> getAccountStates(int movieId, @NotNull String sessionId) {
        return moviesService.getAccountStates(movieId, BuildConfig.TMDB_API_KEY, sessionId, "")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    }
}