package org.michaelbel.moviemade.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.michaelbel.moviemade.app.Url;
import org.michaelbel.moviemade.app.annotation.EmptyViewMode;
import org.michaelbel.moviemade.mvp.view.MvpResultsView;
import org.michaelbel.moviemade.rest.ApiFactory;
import org.michaelbel.moviemade.rest.TmdbObject;
import org.michaelbel.moviemade.rest.api.service.KEYWORDS;
import org.michaelbel.moviemade.rest.response.MoviesResponse;
import org.michaelbel.moviemade.util.AndroidUtils;
import org.michaelbel.moviemade.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class KeywordMoviesPresenter extends MvpPresenter<MvpResultsView> {

    public int page;
    public int totalPages;
    public boolean loading;
    public boolean loadingLocked;

    private int keywordId;

    public void loadMovies(int keywordId) {
        this.keywordId = keywordId;

        if (keywordId == 0) {
            getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
            return;
        }

        if (NetworkUtils.notConnected()) {
            getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        page = 1;
        totalPages = 0;
        loading = false;
        loadingLocked = false;

        KEYWORDS service = ApiFactory.createService(KEYWORDS.class);
        service.getMovies(keywordId, Url.TMDB_API_KEY, Url.en_US, AndroidUtils.includeAdult(), page)
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(new Observer<MoviesResponse>() {
                   @Override
                   public void onSubscribe(Disposable d) {

                   }

                   @Override
                   public void onNext(MoviesResponse response) {
                       totalPages = response.totalPages;

                       List<TmdbObject> results = new ArrayList<>();
                       results.addAll(response.movies);

                       getViewState().showResults(results);
                       page++;
                   }

                   @Override
                   public void onError(Throwable e) {
                       getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
                   }

                   @Override
                   public void onComplete() {

                   }
               });
    }

    public void loadResults() {
        KEYWORDS service = ApiFactory.createService(KEYWORDS.class);
        service.getMovies(keywordId, Url.TMDB_API_KEY, Url.en_US, AndroidUtils.includeAdult(), page)
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(new Observer<MoviesResponse>() {
                   @Override
                   public void onSubscribe(Disposable d) {

                   }

                   @Override
                   public void onNext(MoviesResponse response) {
                       List<TmdbObject> results = new ArrayList<>();
                       results.addAll(response.movies);

                       getViewState().showResults(results);
                       loading = false;
                       page++;
                   }

                   @Override
                   public void onError(Throwable e) {
                       loadingLocked = true;
                       loading = false;
                   }

                   @Override
                   public void onComplete() {

                   }
               });

        loading = true; // todo: this operator in onComplete.

    }
}