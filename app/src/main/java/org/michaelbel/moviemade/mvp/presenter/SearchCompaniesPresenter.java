package org.michaelbel.moviemade.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.michaelbel.moviemade.app.Url;
import org.michaelbel.moviemade.app.annotation.EmptyViewMode;
import org.michaelbel.moviemade.mvp.view.MvpSearchView;
import org.michaelbel.moviemade.rest.ApiFactory2;
import org.michaelbel.moviemade.rest.TmdbObject;
import org.michaelbel.moviemade.rest.api.SEARCH;
import org.michaelbel.moviemade.rest.response.CompanyResponse;
import org.michaelbel.moviemade.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

@InjectViewState
public class SearchCompaniesPresenter extends MvpPresenter<MvpSearchView> {

    public int page = 1;
    public int totalPages;
    public int totalResults;
    public boolean isLoading = false;
    public boolean isLastPage = false;

    private String currentQuery;
    private Disposable disposable1, disposable2;

    public void search(String query) {
        currentQuery = query;
        getViewState().searchStart();

        if (NetworkUtils.notConnected()) {
            getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        SEARCH service = ApiFactory2.createService(SEARCH.class);
        Observable<CompanyResponse> observable = service.searchCompanies(Url.TMDB_API_KEY, query, page).observeOn(AndroidSchedulers.mainThread());
        disposable1 = observable.subscribeWith(new DisposableObserver<CompanyResponse>() {
            @Override
            public void onNext(CompanyResponse response) {
                totalPages = response.totalPages;
                totalResults = response.totalResults;
                List<TmdbObject> results = new ArrayList<>(response.companies);
                if (results.isEmpty()) {
                    getViewState().showError(EmptyViewMode.MODE_NO_RESULTS);
                    return;
                }
                getViewState().showResults(results, true);
            }

            @Override
            public void onError(Throwable e) {
                getViewState().showError(EmptyViewMode.MODE_NO_RESULTS);
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                dispose();
            }
        });
    }

    public void loadNextPage() {
        SEARCH service = ApiFactory2.createService(SEARCH.class);
        Observable<CompanyResponse> observable = service.searchCompanies(Url.TMDB_API_KEY, currentQuery, page).observeOn(AndroidSchedulers.mainThread());
        disposable2 = observable.subscribeWith(new DisposableObserver<CompanyResponse>() {
            @Override
            public void onNext(CompanyResponse response) {
                List<TmdbObject> results = new ArrayList<>(response.companies);
                getViewState().showResults(results, false);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                //disposable1.dispose();
                dispose();
            }
        });
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
}