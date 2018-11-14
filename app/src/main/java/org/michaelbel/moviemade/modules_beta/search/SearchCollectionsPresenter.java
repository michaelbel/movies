package org.michaelbel.moviemade.modules_beta.search;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.michaelbel.moviemade.BuildConfig;
import org.michaelbel.moviemade.ConstantsKt;
import org.michaelbel.moviemade.annotation.EmptyViewMode;
import org.michaelbel.moviemade.data.dao.Collection;
import org.michaelbel.moviemade.data.dao.CollectionsResponse;
import org.michaelbel.moviemade.data.service.SEARCH;
import org.michaelbel.moviemade.extensions.NetworkUtil;
import org.michaelbel.moviemade.ApiFactory;
import org.michaelbel.moviemade.ui.modules.search.SearchMvp;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class SearchCollectionsPresenter extends MvpPresenter<SearchMvp> {

    public int page = 1;
    public int totalPages;
    public int totalResults;
    public boolean isLoading = false;
    public boolean isLastPage = false;

    private String currentQuery;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public void search(String query) {
        currentQuery = query;
        getViewState().searchStart();

        if (NetworkUtil.INSTANCE.notConnected()) {
            getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        SEARCH service = ApiFactory.createService2(SEARCH.class);
        Observable<CollectionsResponse> observable = service.searchCollections(BuildConfig.TMDB_API_KEY, ConstantsKt.en_US, query, page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposables.add(observable.subscribeWith(new DisposableObserver<CollectionsResponse>() {
            @Override
            public void onNext(CollectionsResponse response) {
                totalPages = response.getTotalPages();
                totalResults = response.getTotalResults();
                List<Collection> results = new ArrayList<>(response.getCollections());
                if (results.isEmpty()) {
                    getViewState().showError(EmptyViewMode.MODE_NO_RESULTS);
                    return;
                }
              //  getViewState().showResults(results, true);
            }

            @Override
            public void onError(Throwable e) {
                getViewState().showError(EmptyViewMode.MODE_NO_RESULTS);
            }

            @Override
            public void onComplete() {}
        }));
    }

    public void loadNextPage() {
        SEARCH service = ApiFactory.createService2(SEARCH.class);
        Observable<CollectionsResponse> observable = service.searchCollections(BuildConfig.TMDB_API_KEY, ConstantsKt.en_US, currentQuery, page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposables.add(observable.subscribeWith(new DisposableObserver<CollectionsResponse>() {
            @Override
            public void onNext(CollectionsResponse response) {
                List<Collection> results = new ArrayList<>(response.getCollections());
               // getViewState().showResults(results, false);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {}
        }));
    }

    @Override
    public void onDestroy() {
        disposables.dispose();
        super.onDestroy();
    }
}