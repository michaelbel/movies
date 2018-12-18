package org.michaelbel.moviemade.ui.modules.account;

import android.content.SharedPreferences;

import org.jetbrains.annotations.NotNull;
import org.michaelbel.moviemade.data.constants.StatusCodeKt;
import org.michaelbel.moviemade.data.entity.SessionId;
import org.michaelbel.moviemade.data.entity.Username;
import org.michaelbel.moviemade.data.service.AccountService;
import org.michaelbel.moviemade.data.service.AuthService;
import org.michaelbel.moviemade.utils.Error;
import org.michaelbel.moviemade.utils.NetworkUtil;
import org.michaelbel.moviemade.utils.SharedPrefsKt;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.HttpException;

public class AccountPresenter implements AccountContract.Presenter {

    private AccountContract.View view;
    private AccountContract.Repository repository;
    private SharedPreferences sharedPreferences;
    private CompositeDisposable disposables = new CompositeDisposable();

    AccountPresenter(AccountContract.View view, AuthService authService, AccountService accountService, SharedPreferences prefs) {
        this.view = view;
        this.repository = new AccountRepository(authService, accountService);
        this.sharedPreferences = prefs;
    }

    @Override
    public void createSessionId(@NotNull String token) {
        // Fixme.
        if (NetworkUtil.INSTANCE.notConnected()) {
            view.setError(Error.ERROR_NO_CONNECTION);
            return;
        }

        disposables.add(repository.createSessionId(token)
            .subscribe(session -> {
                if (session != null) {
                    if (session.getSuccess()) {
                        sharedPreferences.edit().putString(SharedPrefsKt.KEY_SESSION_ID, session.getSessionId()).apply();
                        view.sessionChanged(true);
                    }
                }
            }, e -> view.setError(Error.ERROR_NO_CONNECTION)));
    }

    @Override
    public void authWithLogin(@NotNull Username un) {
        // Fixme.
        if (NetworkUtil.INSTANCE.notConnected()) {
            view.setError(Error.ERROR_NO_CONNECTION);
            return;
        }

        disposables.add(repository.authWithLogin(un)
            .subscribe(tokenResponse -> {
                if (tokenResponse != null) {
                    if (tokenResponse.getSuccess()) {
                        String authorizedToken = tokenResponse.getRequestToken();
                        createSessionId(authorizedToken);
                    }
                }
            }, e -> view.setError(Error.ERROR_AUTH_WITH_LOGIN)));
    }

    @Override
    public void deleteSession() {
        // Fixme.
        if (NetworkUtil.INSTANCE.notConnected()) {
            view.setError(Error.ERROR_NO_CONNECTION);
            return;
        }

        disposables.add(repository.deleteSession(new SessionId(sharedPreferences.getString(SharedPrefsKt.KEY_SESSION_ID, "")))
            .subscribe(deletedSession -> {
                if (deletedSession != null) {
                    if (deletedSession.getSuccess()) {
                        view.sessionChanged(false);
                    }
                }
            }, e -> view.setError(Error.ERROR_NO_CONNECTION)));
    }

    @Override
    public void getAccountDetails() {
        // Fixme.
        if (NetworkUtil.INSTANCE.notConnected()) {
            view.setError(Error.ERROR_NO_CONNECTION);
            return;
        }

        disposables.add(repository.getAccountDetails(sharedPreferences.getString(SharedPrefsKt.KEY_SESSION_ID, ""))
            .subscribe(account -> {
                if (account != null) {
                    view.setAccount(account);
                }
            }, e -> {
                int code = ((HttpException) e).code();
                if (code == StatusCodeKt.CODE_401) {
                    view.setError(Error.ERROR_UNAUTHORIZED);
                } else if (code == StatusCodeKt.CODE_404) {
                    view.setError(Error.ERROR_NOT_FOUND);
                }
            }));
    }

    @Override
    public void createRequestToken() {
        // Fixme.
        if (NetworkUtil.INSTANCE.notConnected()) {
            view.setError(Error.ERROR_CONNECTION_NO_TOKEN);
            return;
        }

        disposables.add(repository.createRequestToken()
            .subscribe(response -> {
                if (response != null) {
                    if (response.getSuccess()) {
                        sharedPreferences.edit().putString(SharedPrefsKt.KEY_TOKEN, response.getRequestToken()).apply();
                        sharedPreferences.edit().putString(SharedPrefsKt.KEY_DATE_AUTHORISED, response.getDate()).apply();
                        view.startBrowserAuth(response.getRequestToken());
                    }
                }
            }, e -> view.setError(Error.ERROR_CONNECTION_NO_TOKEN)));
    }

    @Override
    public void createRequestToken(@NotNull String name, @NotNull String pass) {
        // Fixme.
        if (NetworkUtil.INSTANCE.notConnected()) {
            view.setError(Error.ERROR_CONNECTION_NO_TOKEN);
            return;
        }

        disposables.add(repository.createRequestToken(name, pass)
            .subscribe(response -> {
                if (response != null) {
                    if (response.getSuccess()) {
                        sharedPreferences.edit().putString(SharedPrefsKt.KEY_TOKEN, response.getRequestToken()).apply();
                        sharedPreferences.edit().putString(SharedPrefsKt.KEY_DATE_AUTHORISED, response.getDate()).apply();
                        Username username = new Username(name, pass, response.getRequestToken());
                        authWithLogin(username);
                    }
                }
            }, e -> view.setError(Error.ERROR_CONNECTION_NO_TOKEN)));
    }

    @Override
    public void onDestroy() {
        disposables.dispose();
    }
}