package org.michaelbel.moviemade.ui.modules.account;

import android.content.SharedPreferences;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.michaelbel.moviemade.BuildConfig;
import org.michaelbel.moviemade.Moviemade;
import org.michaelbel.moviemade.data.constants.StatusCodeKt;
import org.michaelbel.moviemade.data.dao.RequestToken;
import org.michaelbel.moviemade.data.dao.SessionId;
import org.michaelbel.moviemade.data.dao.Username;
import org.michaelbel.moviemade.data.service.ACCOUNT;
import org.michaelbel.moviemade.data.service.AUTHENTICATION;
import org.michaelbel.moviemade.utils.Error;
import org.michaelbel.moviemade.utils.NetworkUtil;
import org.michaelbel.moviemade.utils.RxUtil;
import org.michaelbel.moviemade.utils.SharedPrefsKt;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;
import retrofit2.Retrofit;

@InjectViewState
public class AccountPresenter extends MvpPresenter<AccountMvp> {

    private Disposable disposable1;
    private Disposable disposable2;
    private Disposable disposable3;
    private Disposable disposable4;
    private Disposable disposable5;
    private Disposable disposable6;

    @Inject Retrofit retrofit;
    @Inject SharedPreferences sharedPreferences;

    AccountPresenter() {
        Moviemade.getComponent().injest(this);
    }

    public void createSessionId(String token) {
        if (NetworkUtil.INSTANCE.notConnected()) {
            getViewState().setError(Error.ERROR_NO_CONNECTION);
            return;
        }

        AUTHENTICATION service = retrofit.create(AUTHENTICATION.class);
        disposable1 = service.createSession(BuildConfig.TMDB_API_KEY, new RequestToken(token)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(session -> {
                if (session != null) {
                    if (session.getSuccess()) {
                        sharedPreferences.edit().putString(SharedPrefsKt.KEY_SESSION_ID, session.getSessionId()).apply();
                        getViewState().sessionChanged(true);
                    }
                }
            }, e -> {
                getViewState().setError(Error.ERROR_NO_CONNECTION);
                e.printStackTrace();
            });
    }

    private void authWithLogin(Username username) {
        if (NetworkUtil.INSTANCE.notConnected()) {
            getViewState().setError(Error.ERROR_NO_CONNECTION);
            return;
        }

        AUTHENTICATION service = retrofit.create(AUTHENTICATION.class);
        disposable2 = service.createSessionWithLogin(BuildConfig.TMDB_API_KEY, username).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(tokenResponse -> {
                if (tokenResponse != null) {
                    if (tokenResponse.getSuccess()) {
                        String authorizedToken = tokenResponse.getRequestToken();
                        createSessionId(authorizedToken);
                    }
                }
            }, e -> {
                getViewState().setError(Error.ERROR_AUTH_WITH_LOGIN);
                e.printStackTrace();
            });
    }

    void deleteSession() {
        if (NetworkUtil.INSTANCE.notConnected()) {
            getViewState().setError(Error.ERROR_NO_CONNECTION);
            return;
        }

        AUTHENTICATION service = retrofit.create(AUTHENTICATION.class);
        disposable3 = service.deleteSession(BuildConfig.TMDB_API_KEY, new SessionId(sharedPreferences.getString(SharedPrefsKt.KEY_SESSION_ID, ""))).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(deletedSession -> {
                if (deletedSession != null) {
                    if (deletedSession.getSuccess()) {
                        getViewState().sessionChanged(false);
                    }
                }
            }, e -> {
                getViewState().setError(Error.ERROR_NO_CONNECTION);
                e.printStackTrace();
            });
    }

    void getAccountDetails() {
        if (NetworkUtil.INSTANCE.notConnected()) {
            getViewState().setError(Error.ERROR_NO_CONNECTION);
            return;
        }

        ACCOUNT service = retrofit.create(ACCOUNT.class);
        disposable4 = service.getDetails(BuildConfig.TMDB_API_KEY, sharedPreferences.getString(SharedPrefsKt.KEY_SESSION_ID, "")).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(account -> {
                if (account != null) {
                    getViewState().setAccount(account);
                }
            }, e -> {
                int code = ((HttpException) e).code();
                if (code == StatusCodeKt.CODE_401) {
                    getViewState().setError(Error.ERROR_UNAUTHORIZED);
                } else if (code == StatusCodeKt.CODE_404) {
                    getViewState().setError(Error.ERROR_NOT_FOUND);
                }
            });
    }

    void createRequestToken() {
        if (NetworkUtil.INSTANCE.notConnected()) {
            getViewState().setError(Error.ERROR_CONNECTION_NO_TOKEN);
            return;
        }

        AUTHENTICATION service = retrofit.create(AUTHENTICATION.class);
        disposable5 = service.createRequestToken(BuildConfig.TMDB_API_KEY).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(response -> {
                if (response != null) {
                    if (response.getSuccess()) {
                        sharedPreferences.edit().putString(SharedPrefsKt.KEY_TOKEN, response.getRequestToken()).apply();
                        sharedPreferences.edit().putString(SharedPrefsKt.KEY_DATE_AUTHORISED, response.getDate()).apply();
                        getViewState().startBrowserAuth(response.getRequestToken());
                    }
                }
            }, e -> {
                getViewState().setError(Error.ERROR_CONNECTION_NO_TOKEN);
                e.printStackTrace();
            });
    }

    void createRequestToken(String name, String pass) {
        if (NetworkUtil.INSTANCE.notConnected()) {
            getViewState().setError(Error.ERROR_CONNECTION_NO_TOKEN);
            return;
        }

        AUTHENTICATION service = retrofit.create(AUTHENTICATION.class);
        disposable6 = service.createRequestToken(BuildConfig.TMDB_API_KEY).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(response -> {
                if (response != null) {
                    if (response.getSuccess()) {
                        sharedPreferences.edit().putString(SharedPrefsKt.KEY_TOKEN, response.getRequestToken()).apply();
                        sharedPreferences.edit().putString(SharedPrefsKt.KEY_DATE_AUTHORISED, response.getDate()).apply();
                        Username username = new Username(name, pass, response.getRequestToken());
                        authWithLogin(username);
                    }
                }
            }, e -> {
                getViewState().setError(Error.ERROR_CONNECTION_NO_TOKEN);
                e.printStackTrace();
            });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxUtil.INSTANCE.unsubscribe(disposable1);
        RxUtil.INSTANCE.unsubscribe(disposable2);
        RxUtil.INSTANCE.unsubscribe(disposable3);
        RxUtil.INSTANCE.unsubscribe(disposable4);
        RxUtil.INSTANCE.unsubscribe(disposable5);
        RxUtil.INSTANCE.unsubscribe(disposable6);
    }
}