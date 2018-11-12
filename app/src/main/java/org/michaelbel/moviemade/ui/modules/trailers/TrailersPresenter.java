package org.michaelbel.moviemade.ui.modules.trailers;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.michaelbel.moviemade.BuildConfig;
import org.michaelbel.moviemade.Url;
import org.michaelbel.moviemade.rest.ApiFactory;
import org.michaelbel.moviemade.rest.api.MOVIES;
import org.michaelbel.moviemade.rest.response.TrailersResponse;
import org.michaelbel.moviemade.utils.NetworkUtils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class TrailersPresenter extends MvpPresenter<TrailersMvp> {

    private final CompositeDisposable disposables = new CompositeDisposable();

    public void loadTrailers(int movieId) {
        if (NetworkUtils.notConnected()) {
            getViewState().showError();
            return;
        }

        MOVIES service = ApiFactory.createService2(MOVIES.class);
        Observable<TrailersResponse> observable = service.getVideos(movieId, BuildConfig.TMDB_API_KEY, Url.en_US).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposables.add(observable.subscribeWith(new DisposableObserver<TrailersResponse>() {
            @Override
            public void onNext(TrailersResponse response) {
                getViewState().setTrailers(response.trailers);
            }

            @Override
            public void onError(Throwable e) {
                getViewState().showError();
            }

            @Override
            public void onComplete() {}
        }));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposables.dispose();
    }
}