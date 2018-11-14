package org.michaelbel.moviemade.modules_beta.person;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.michaelbel.moviemade.BuildConfig;
import org.michaelbel.moviemade.ConstantsKt;
import org.michaelbel.moviemade.annotation.EmptyViewMode;
import org.michaelbel.moviemade.data.dao.Person;
import org.michaelbel.moviemade.data.dao.PersonsResponse;
import org.michaelbel.moviemade.data.service.PEOPLE;
import org.michaelbel.moviemade.extensions.NetworkUtil;
import org.michaelbel.moviemade.ApiFactory;
import org.michaelbel.moviemade.ui.modules.main.ResultsMvp;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class PopularPeoplePresenter extends MvpPresenter<ResultsMvp> {

    public int page = 1;
    public int totalPages;
    public boolean isLoading = false;
    public boolean isLastPage = false;

    private final CompositeDisposable disposables = new CompositeDisposable();

    public void loadFirstPage() {
        if (NetworkUtil.INSTANCE.notConnected()) {
            getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        PEOPLE service = ApiFactory.createService2(PEOPLE.class);
        Observable<PersonsResponse> observable = service.getPopular(BuildConfig.TMDB_API_KEY, ConstantsKt.en_US, page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposables.add(observable.subscribeWith(new DisposableObserver<PersonsResponse>() {
            @Override
            public void onNext(PersonsResponse response) {
                totalPages = response.getTotalPages();
                List<Person> results = new ArrayList<>(response.getPeople());
                if (results.isEmpty()) {
                    getViewState().showError(EmptyViewMode.MODE_NO_PEOPLE);
                    return;
                }
              //  getViewState().showResults(results, true);
            }

            @Override
            public void onError(Throwable e) {
                getViewState().showError(EmptyViewMode.MODE_NO_PEOPLE);
            }

            @Override
            public void onComplete() {}
        }));
    }

    public void loadNextPage() {
        PEOPLE service = ApiFactory.createService2(PEOPLE.class);
        Observable<PersonsResponse> observable = service.getPopular(BuildConfig.TMDB_API_KEY, ConstantsKt.en_US, page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposables.add(observable.subscribeWith(new DisposableObserver<PersonsResponse>() {
            @Override
            public void onNext(PersonsResponse response) {
                List<Person> results = new ArrayList<>(response.getPeople());
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