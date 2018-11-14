package org.michaelbel.moviemade.modules_beta.search;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.michaelbel.moviemade.BuildConfig;
import org.michaelbel.moviemade.annotation.EmptyViewMode;
import org.michaelbel.moviemade.data.dao.CompaniesResponse;
import org.michaelbel.moviemade.data.dao.Company;
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
public class SearchCompaniesPresenter extends MvpPresenter<SearchMvp> {

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
        Observable<CompaniesResponse> observable = service.searchCompanies(BuildConfig.TMDB_API_KEY, query, page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposables.add(observable.subscribeWith(new DisposableObserver<CompaniesResponse>() {
            @Override
            public void onNext(CompaniesResponse response) {
                totalPages = response.getTotalPages();
                totalResults = response.getTotalResults();
                List<Company> results = new ArrayList<>(response.getCompanies());
                if (results.isEmpty()) {
                    getViewState().showError(EmptyViewMode.MODE_NO_RESULTS);
                    return;
                }
               // getViewState().showResults(results, true);
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
        Observable<CompaniesResponse> observable = service.searchCompanies(BuildConfig.TMDB_API_KEY, currentQuery, page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposables.add(observable.subscribeWith(new DisposableObserver<CompaniesResponse>() {
            @Override
            public void onNext(CompaniesResponse response) {
                List<Company> results = new ArrayList<>(response.getCompanies());
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