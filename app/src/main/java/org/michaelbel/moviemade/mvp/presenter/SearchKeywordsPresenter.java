package org.michaelbel.moviemade.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.michaelbel.moviemade.app.Url;
import org.michaelbel.moviemade.app.annotation.EmptyViewMode;
import org.michaelbel.moviemade.mvp.view.MvpSearchView;
import org.michaelbel.moviemade.rest.ApiFactory;
import org.michaelbel.moviemade.rest.TmdbObject;
import org.michaelbel.moviemade.rest.api.SEARCH;
import org.michaelbel.moviemade.rest.response.KeywordsResponse;
import org.michaelbel.moviemade.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class SearchKeywordsPresenter extends MvpPresenter<MvpSearchView> {

    public int page;
    public int totalPages;
    public boolean loading;
    public boolean loadingLocked;

    private String currentQuery;

    private Disposable disposable1;
    private Disposable disposable2;

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
        Observable<KeywordsResponse> observable = service.searchKeywords(Url.TMDB_API_KEY, query, page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposable1 = observable.subscribeWith(new DisposableObserver<KeywordsResponse>() {
            @Override
            public void onNext(KeywordsResponse response) {
                List<TmdbObject> results = new ArrayList<>(response.keywords);
                getViewState().searchComplete(results, response.totalResults);
                totalPages = response.totalPages;
                page++;
            }

            @Override
            public void onError(Throwable e) {
                getViewState().showError(EmptyViewMode.MODE_NO_RESULTS);
                e.printStackTrace();
            }

            @Override
            public void onComplete() {}
        });
    }

    public void loadResults() {
        SEARCH service = ApiFactory.createService(SEARCH.class);
        Observable<KeywordsResponse> observable = service.searchKeywords(Url.TMDB_API_KEY, currentQuery, page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposable2 = observable.subscribeWith(new DisposableObserver<KeywordsResponse>() {
            @Override
            public void onNext(KeywordsResponse response) {
                List<TmdbObject> results = new ArrayList<>(response.keywords);
                getViewState().nextPageLoaded(results);
                loading = false;
                page++;
            }

            @Override
            public void onError(Throwable e) {
                loadingLocked = true;
                loading = false;
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                disposable1.dispose();
            }
        });
        loading = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (disposable1 != null && !disposable1.isDisposed()) {
            disposable1.dispose();
        }

        if (disposable2 != null && !disposable2.isDisposed()) {
            disposable2.dispose();
        }
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