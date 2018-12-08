package org.michaelbel.moviemade.ui.modules.keywords;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.michaelbel.moviemade.BuildConfig;
import org.michaelbel.moviemade.Moviemade;
import org.michaelbel.moviemade.data.dao.Movie;
import org.michaelbel.moviemade.data.service.KEYWORDS;
import org.michaelbel.moviemade.utils.AdultUtil;
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
public class KeywordPresenter extends MvpPresenter<KeywordMvp> {

    private int page;
    private Disposable subscription2;
    private Disposable subscription3;

    @Inject Retrofit retrofit;

    public KeywordPresenter() {
        Moviemade.getComponent().injest(this);
    }

    public void getMovies(int keywordId) {
        if (NetworkUtil.INSTANCE.notConnected()) {
            getViewState().setError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        page = 1;
        KEYWORDS service = retrofit.create(KEYWORDS.class);
        subscription2 = service.getMovies(keywordId, BuildConfig.TMDB_API_KEY, TmdbConfigKt.en_US, AdultUtil.INSTANCE.includeAdult(Moviemade.appContext), page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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

    public void getMoviesNext(int keywordId) {
        page++;
        KEYWORDS service = retrofit.create(KEYWORDS.class);
        subscription3 = service.getMovies(keywordId, BuildConfig.TMDB_API_KEY, TmdbConfigKt.en_US, AdultUtil.INSTANCE.includeAdult(Moviemade.appContext), page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(response -> {
                List<Movie> results = new ArrayList<>(response.getMovies());
                if (results.isEmpty()) {
                    getViewState().setError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }
                getViewState().setMovies(results);
            });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxUtil.INSTANCE.unsubscribe(subscription2);
        RxUtil.INSTANCE.unsubscribe(subscription3);
    }
}