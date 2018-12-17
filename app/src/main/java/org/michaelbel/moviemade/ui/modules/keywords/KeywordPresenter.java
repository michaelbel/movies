package org.michaelbel.moviemade.ui.modules.keywords;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.michaelbel.moviemade.BuildConfig;
import org.michaelbel.moviemade.Moviemade;
import org.michaelbel.moviemade.data.entity.Movie;
import org.michaelbel.moviemade.data.service.KeywordsService;
import org.michaelbel.moviemade.utils.AdultUtil;
import org.michaelbel.moviemade.utils.EmptyViewMode;
import org.michaelbel.moviemade.utils.NetworkUtil;
import org.michaelbel.moviemade.utils.RxUtil;
import org.michaelbel.moviemade.utils.TmdbConfigKt;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class KeywordPresenter extends MvpPresenter<KeywordMvp> {

    private int page;
    private CompositeDisposable disposables = new CompositeDisposable();

    @Inject KeywordsService service;

    public KeywordPresenter() {
        Moviemade.getAppComponent().injest(this);
    }

    public void getMovies(int keywordId) {
        if (NetworkUtil.INSTANCE.notConnected()) {
            getViewState().setError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        page = 1;
        disposables.add(service.getMovies(keywordId, BuildConfig.TMDB_API_KEY, TmdbConfigKt.en_US, AdultUtil.INSTANCE.includeAdult(Moviemade.appContext), page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(response -> {
                List<Movie> results = new ArrayList<>(response.getMovies());
                if (results.isEmpty()) {
                    getViewState().setError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }
                getViewState().setMovies(results);
            }, e -> getViewState().setError(EmptyViewMode.MODE_NO_MOVIES)));
    }

    public void getMoviesNext(int keywordId) {
        page++;
        disposables.add(service.getMovies(keywordId, BuildConfig.TMDB_API_KEY, TmdbConfigKt.en_US, AdultUtil.INSTANCE.includeAdult(Moviemade.appContext), page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(response -> {
                List<Movie> results = new ArrayList<>(response.getMovies());
                if (results.isEmpty()) {
                    getViewState().setError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }
                getViewState().setMovies(results);
            }));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposables.dispose();
    }
}