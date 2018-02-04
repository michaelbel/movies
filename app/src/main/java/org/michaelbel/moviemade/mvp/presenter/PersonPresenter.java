package org.michaelbel.moviemade.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.michaelbel.moviemade.app.Url;
import org.michaelbel.moviemade.app.annotation.EmptyViewMode;
import org.michaelbel.moviemade.mvp.view.MvpPersonView;
import org.michaelbel.moviemade.rest.ApiFactory;
import org.michaelbel.moviemade.rest.api.PEOPLE;
import org.michaelbel.moviemade.rest.model.Person;
import org.michaelbel.moviemade.utils.NetworkUtils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

@InjectViewState
public class PersonPresenter extends MvpPresenter<MvpPersonView> {

    private Disposable disposable;

    public void loadPerson(int personId) {
        if (NetworkUtils.notConnected()) {
            getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        PEOPLE service = ApiFactory.createService(PEOPLE.class);
        Observable<Person> observable = service.getDetails(personId, Url.TMDB_API_KEY, Url.en_US, null).observeOn(AndroidSchedulers.mainThread());
        disposable = observable.subscribeWith(new DisposableObserver<Person>() {
            @Override
            public void onNext(Person person) {
                getViewState().showPerson(person);
            }

            @Override
            public void onError(Throwable e) {
                getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
                e.printStackTrace();
            }

            @Override
            public void onComplete() {}
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}