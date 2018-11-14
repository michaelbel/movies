package org.michaelbel.moviemade.modules_beta.person;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.michaelbel.moviemade.BuildConfig;
import org.michaelbel.moviemade.annotation.EmptyViewMode;
import org.michaelbel.moviemade.data.dao.Cast;
import org.michaelbel.moviemade.data.dao.CreditsResponse;
import org.michaelbel.moviemade.data.service.MOVIES;
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
public class MovieCastsPresenter extends MvpPresenter<ResultsMvp> {

    private final CompositeDisposable disposables = new CompositeDisposable();

    public void loadCredits(int movieId) {
        if (NetworkUtil.INSTANCE.notConnected()) {
            getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        // todo То, что этот метод вызывается 2 раза нехорошо, нужно вызывать его 1 раз и затем только передавать данные
        MOVIES service = ApiFactory.createService2(MOVIES.class);
        Observable<CreditsResponse> observable = service.getCredits(movieId, BuildConfig.TMDB_API_KEY).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposables.add(observable.subscribeWith(new DisposableObserver<CreditsResponse>() {
            @Override
            public void onNext(CreditsResponse response) {
                List<Cast> results = new ArrayList<>(response.getCast());
                if (results.isEmpty()) {
                    getViewState().showError(EmptyViewMode.MODE_NO_PEOPLE);
                }
               // getViewState().showResults(results, true);
            }

            @Override
            public void onError(Throwable e) {
                getViewState().showError(EmptyViewMode.MODE_NO_PEOPLE);
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