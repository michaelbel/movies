package org.michaelbel.moviemade.ui.modules.favorite;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.michaelbel.moviemade.BuildConfig;
import org.michaelbel.moviemade.Moviemade;
import org.michaelbel.moviemade.data.dao.Movie;
import org.michaelbel.moviemade.data.service.ACCOUNT;
import org.michaelbel.moviemade.utils.EmptyViewMode;
import org.michaelbel.moviemade.utils.NetworkUtil;
import org.michaelbel.moviemade.utils.RxUtil;
import org.michaelbel.moviemade.utils.TmdbConfigKt;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

@InjectViewState
public class FavePresenter extends MvpPresenter<FaveMvp> {

    private int page;
    private Disposable disposable;
    private Disposable disposable2;

    @Inject
    Retrofit retrofit;

    void getFavoriteMovies(Moviemade app, int accountId, String sessionId) {
        if (NetworkUtil.INSTANCE.notConnected()) {
            getViewState().setError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        page = 1;
        app.getAppComponent().injest(this);

        ACCOUNT service = retrofit.create(ACCOUNT.class);
        disposable = service.getFavoriteMovies(accountId, BuildConfig.TMDB_API_KEY, sessionId, TmdbConfigKt.en_US, "created_at.asc", page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(response -> {
                List<Movie> results = new ArrayList<>(response.getMovies());
                if (results.isEmpty()) {
                    getViewState().setError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }
                getViewState().setMovies(results);
            }, e -> {
                getViewState().setError(EmptyViewMode.MODE_NO_MOVIES);
                e.printStackTrace();
            });
    }

    void getFavoriteMoviesNext(Moviemade app, int accountId, String sessionId) {
        page++;
        app.getAppComponent().injest(this);

        ACCOUNT service = retrofit.create(ACCOUNT.class);
        disposable2 = service.getFavoriteMovies(accountId, BuildConfig.TMDB_API_KEY, sessionId, TmdbConfigKt.en_US, "created_at.asc", page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    List<Movie> results = new ArrayList<>(response.getMovies());
                    if (results.isEmpty()) {
                        getViewState().setError(EmptyViewMode.MODE_NO_MOVIES);
                        return;
                    }
                    getViewState().setMovies(results);
                }, e -> {
                    getViewState().setError(EmptyViewMode.MODE_NO_MOVIES);
                    e.printStackTrace();
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxUtil.INSTANCE.unsubscribe(disposable);
        RxUtil.INSTANCE.unsubscribe(disposable2);
    }
}