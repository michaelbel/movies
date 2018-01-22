package org.michaelbel.moviemade.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.michaelbel.moviemade.app.Url;
import org.michaelbel.moviemade.app.annotation.EmptyViewMode;
import org.michaelbel.moviemade.mvp.view.MvpSearchView;
import org.michaelbel.moviemade.rest.ApiFactory;
import org.michaelbel.moviemade.rest.TmdbObject;
import org.michaelbel.moviemade.rest.api.SEARCH;
import org.michaelbel.moviemade.rest.response.MovieResponse;
import org.michaelbel.moviemade.util.AndroidUtils;
import org.michaelbel.moviemade.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class SearchMoviesPresenter extends MvpPresenter<MvpSearchView> {

    public int page;
    public int totalPages;
    public boolean loading;
    public boolean loadingLocked;

    private String currentQuery;

    public void search(String query) {
        page = 1;
        totalPages = 0;
        loading = false;
        loadingLocked = false;
        currentQuery = query;

        getViewState().searchStart();

        if (NetworkUtils.notConnected()) {
            getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        SEARCH service = ApiFactory.createService(SEARCH.class);
        service.searchMovies(Url.TMDB_API_KEY, Url.en_US, query, page, AndroidUtils.includeAdult(), null)
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(new Observer<MovieResponse>() {
                   @Override
                   public void onSubscribe(Disposable d) {

                   }

                   @Override
                   public void onNext(MovieResponse response) {
                       totalPages = response.totalPages;

                       List<TmdbObject> results = new ArrayList<>();
                       results.addAll(response.movies);

                       if (results.isEmpty()) {
                           getViewState().showError(EmptyViewMode.MODE_NO_RESULTS);
                           return;
                       }

                       getViewState().searchComplete(results, response.totalResults);
                       page++;
                   }

                   @Override
                   public void onError(Throwable e) {
                       getViewState().showError(EmptyViewMode.MODE_NO_RESULTS);
                   }

                   @Override
                   public void onComplete() {

                   }
               });
    }

    public void loadResults() {
        SEARCH service = ApiFactory.createService(SEARCH.class);
        service.searchMovies(Url.TMDB_API_KEY, Url.en_US, currentQuery, page, AndroidUtils.includeAdult(), null)
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(new Observer<MovieResponse>() {
                   @Override
                   public void onSubscribe(Disposable d) {

                   }

                   @Override
                   public void onNext(MovieResponse response) {
                       List<TmdbObject> results = new ArrayList<>();
                       results.addAll(response.movies);

                       if (results.isEmpty()) {
                           return;
                       }

                       getViewState().nextPageLoaded(results);
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

        loading = true;
    }

    /*private void addToSearchHistory(String query) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            SearchItem item = realm1.createObject(SearchItem.class);
            item.queryTitle = query;
            item.queryDate = DateUtils.getCurrentDateAndTimeWithMilliseconds();
        });
    }*/
}