package org.michaelbel.moviemade.ui.modules.account;

import org.jetbrains.annotations.NotNull;
import org.michaelbel.moviemade.BuildConfig;
import org.michaelbel.moviemade.data.entity.Account;
import org.michaelbel.moviemade.data.entity.DeletedSession;
import org.michaelbel.moviemade.data.entity.RequestToken;
import org.michaelbel.moviemade.data.entity.Session;
import org.michaelbel.moviemade.data.entity.SessionId;
import org.michaelbel.moviemade.data.entity.Token;
import org.michaelbel.moviemade.data.entity.Username;
import org.michaelbel.moviemade.data.entity.VideosResponse;
import org.michaelbel.moviemade.data.service.AccountService;
import org.michaelbel.moviemade.data.service.AuthService;
import org.michaelbel.moviemade.data.service.MoviesService;
import org.michaelbel.moviemade.ui.modules.trailers.TrailersContract;
import org.michaelbel.moviemade.utils.SharedPrefsKt;
import org.michaelbel.moviemade.utils.TmdbConfigKt;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AccountRepository implements AccountContract.Repository {

    private AuthService authService;
    private AccountService accountService;

    AccountRepository(AuthService authService, AccountService accountService) {
        this.authService = authService;
        this.accountService = accountService;
    }

    @NotNull
    @Override
    public Observable<Session> createSessionId(@NotNull String token) {
        return authService.createSession(BuildConfig.TMDB_API_KEY, new RequestToken(token))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    }

    @NotNull
    @Override
    public Observable<Token> authWithLogin(@NotNull Username un) {
        return authService.createSessionWithLogin(BuildConfig.TMDB_API_KEY, un)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    }

    @NotNull
    @Override
    public Observable<DeletedSession> deleteSession(@NonNull SessionId sessionId) {
        return authService.deleteSession(BuildConfig.TMDB_API_KEY, sessionId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    }

    @NotNull
    @Override
    public Observable<Account> getAccountDetails(@NonNull String sessionId) {
        return accountService.getDetails(BuildConfig.TMDB_API_KEY, sessionId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    }

    @NotNull
    @Override
    public Observable<Token> createRequestToken() {
        return authService.createRequestToken(BuildConfig.TMDB_API_KEY)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    }

    @NotNull
    @Override
    public Observable<Token> createRequestToken(@NotNull String name, @NotNull String pass) {
        return authService.createRequestToken(BuildConfig.TMDB_API_KEY)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    }
}