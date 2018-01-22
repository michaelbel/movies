package org.michaelbel.moviemade.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.michaelbel.moviemade.app.Url;
import org.michaelbel.moviemade.app.annotation.EmptyViewMode;
import org.michaelbel.moviemade.mvp.view.MvpResultsView;
import org.michaelbel.moviemade.rest.ApiFactory;
import org.michaelbel.moviemade.rest.TmdbObject;
import org.michaelbel.moviemade.rest.api.PEOPLE;
import org.michaelbel.moviemade.rest.model.v3.People;
import org.michaelbel.moviemade.rest.response.PeopleResponse;
import org.michaelbel.moviemade.util.AndroidUtils;
import org.michaelbel.moviemade.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class PopularPeoplePresenter extends MvpPresenter<MvpResultsView> {

    public int page;
    public int totalPages;
    public boolean loading;
    public boolean loadingLocked;

    public void loadPeople() {
        if (NetworkUtils.notConnected()) {
            getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        page = 1;
        totalPages = 0;
        loading = false;
        loadingLocked = false;

        PEOPLE service = ApiFactory.createService(PEOPLE.class);
        service.getPopular(Url.TMDB_API_KEY, Url.en_US, page)
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(new Observer<PeopleResponse>() {
                   @Override
                   public void onSubscribe(Disposable d) {

                   }

                   @Override
                   public void onNext(PeopleResponse response) {
                       totalPages = response.totalPages;

                       List<TmdbObject> results = new ArrayList<>();

                       if (AndroidUtils.includeAdult()) {
                           results.addAll(response.people);
                       } else {
                           for (People people : response.people) {
                               if (!people.adult) {
                                   results.add(people);
                               }
                           }
                       }

                       if (results.isEmpty()) {
                           getViewState().showError(EmptyViewMode.MODE_NO_PEOPLE);
                           return;
                       }

                       getViewState().showResults(results);
                       page++;
                   }

                   @Override
                   public void onError(Throwable e) {
                       getViewState().showError(EmptyViewMode.MODE_NO_PEOPLE);
                   }

                   @Override
                   public void onComplete() {

                   }
               });
    }

    public void loadResults() {
        PEOPLE service = ApiFactory.createService(PEOPLE.class);
        service.getPopular(Url.TMDB_API_KEY, Url.en_US, page)
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(new Observer<PeopleResponse>() {
                   @Override
                   public void onSubscribe(Disposable d) {

                   }

                   @Override
                   public void onNext(PeopleResponse response) {
                       List<TmdbObject> results = new ArrayList<>();

                       if (AndroidUtils.includeAdult()) {
                           results.addAll(response.people);
                       } else {
                           for (People people : response.people) {
                               if (!people.adult) {
                                   results.add(people);
                               }
                           }
                       }

                       if (results.isEmpty()) {
                           return;
                       }

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

        loading = true;
    }
}