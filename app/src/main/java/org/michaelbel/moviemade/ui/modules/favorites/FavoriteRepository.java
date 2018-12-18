package org.michaelbel.moviemade.ui.modules.favorites;

import org.jetbrains.annotations.NotNull;
import org.michaelbel.moviemade.BuildConfig;
import org.michaelbel.moviemade.data.constants.OrderKt;
import org.michaelbel.moviemade.data.entity.MoviesResponse;
import org.michaelbel.moviemade.data.service.AccountService;
import org.michaelbel.moviemade.utils.TmdbConfigKt;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FavoriteRepository implements FavoritesContract.Repository {

    private AccountService service;

    FavoriteRepository(AccountService service) {
        this.service = service;
    }

    @NotNull
    @Override
    public Observable<MoviesResponse> getFavoriteMovies(int accountId, @NotNull String sessionId, int page) {
        return service.getFavoriteMovies(accountId, BuildConfig.TMDB_API_KEY, sessionId, TmdbConfigKt.en_US, OrderKt.ASC, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    }
}