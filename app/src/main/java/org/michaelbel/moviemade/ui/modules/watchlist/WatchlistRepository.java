package org.michaelbel.moviemade.ui.modules.watchlist;

import org.jetbrains.annotations.NotNull;
import org.michaelbel.moviemade.BuildConfig;
import org.michaelbel.moviemade.Moviemade;
import org.michaelbel.moviemade.data.constants.OrderKt;
import org.michaelbel.moviemade.data.entity.MoviesResponse;
import org.michaelbel.moviemade.data.service.AccountService;
import org.michaelbel.moviemade.utils.LangUtil;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WatchlistRepository implements WatchlistContract.Repository {

    private AccountService service;

    WatchlistRepository(AccountService service) {
        this.service = service;
    }

    @NotNull
    @Override
    public Observable<MoviesResponse> getWatchlistMovies(int accountId, @NotNull String sessionId, int page) {
        return service.getWatchlistMovies(accountId, BuildConfig.TMDB_API_KEY, sessionId, LangUtil.INSTANCE.getLanguage(Moviemade.appContext), OrderKt.ASC, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    }
}