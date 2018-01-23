package org.michaelbel.moviemade.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.michaelbel.moviemade.app.Url;
import org.michaelbel.moviemade.app.annotation.EmptyViewMode;
import org.michaelbel.moviemade.mvp.view.MvpResultsView;
import org.michaelbel.moviemade.rest.ApiFactory;
import org.michaelbel.moviemade.rest.TmdbObject;
import org.michaelbel.moviemade.rest.api.MOVIES;
import org.michaelbel.moviemade.rest.response.CreditResponse;
import org.michaelbel.moviemade.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class CastMoviePresenter extends MvpPresenter<MvpResultsView> {

    private Disposable disposable;

    public void loadCredits(int movieId) {
        if (NetworkUtils.notConnected()) {
            getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        // todo То, что этот метод вызывается 2 раза нехорошо, нужно вызывать его 1 раз и затем только передавать данные
        MOVIES service = ApiFactory.createService(MOVIES.class);
        Observable<CreditResponse> observable = service.getCredits(movieId, Url.TMDB_API_KEY).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposable = observable.subscribeWith(new DisposableObserver<CreditResponse>() {
            @Override
            public void onNext(CreditResponse response) {
                List<TmdbObject> results = new ArrayList<>(response.casts);
                getViewState().showResults(results);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}