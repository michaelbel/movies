package org.michaelbel.moviemade.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.michaelbel.moviemade.app.Url;
import org.michaelbel.moviemade.app.annotation.EmptyViewMode;
import org.michaelbel.moviemade.mvp.view.MvpResultsView;
import org.michaelbel.moviemade.rest.ApiFactory;
import org.michaelbel.moviemade.rest.TmdbObject;
import org.michaelbel.moviemade.rest.api.PEOPLE;
import org.michaelbel.moviemade.rest.response.PeopleResponse;
import org.michaelbel.moviemade.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

@InjectViewState
public class PopularPeoplePresenter extends MvpPresenter<MvpResultsView> {

    public int page = 1;
    public int totalPages;
    public boolean isLoading = false;
    public boolean isLastPage = false;

    private Disposable disposable1, disposable2;

    public void loadFirstPage() {
        if (NetworkUtils.notConnected()) {
            getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        PEOPLE service = ApiFactory.createService(PEOPLE.class);
        Observable<PeopleResponse> observable = service.getPopular(Url.TMDB_API_KEY, Url.en_US, page).observeOn(AndroidSchedulers.mainThread());
        disposable1 = observable.subscribeWith(new DisposableObserver<PeopleResponse>() {
            @Override
            public void onNext(PeopleResponse response) {
                totalPages = response.totalPages;
                List<TmdbObject> results = new ArrayList<>(response.people);
                if (results.isEmpty()) {
                    getViewState().showError(EmptyViewMode.MODE_NO_PEOPLE);
                    return;
                }
                getViewState().showResults(results, true);
            }

            @Override
            public void onError(Throwable e) {
                getViewState().showError(EmptyViewMode.MODE_NO_PEOPLE);
                e.printStackTrace();
            }

            @Override
            public void onComplete() {}
        });
    }

    public void loadNextPage() {
        PEOPLE service = ApiFactory.createService(PEOPLE.class);
        Observable<PeopleResponse> observable = service.getPopular(Url.TMDB_API_KEY, Url.en_US, page).observeOn(AndroidSchedulers.mainThread());
        disposable2 = observable.subscribeWith(new DisposableObserver<PeopleResponse>() {
            @Override
            public void onNext(PeopleResponse response) {
                List<TmdbObject> results = new ArrayList<>(response.people);
                getViewState().showResults(results, false);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                disposable1.dispose();
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