package org.michaelbel.moviemade.ui.modules.trailers;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.michaelbel.moviemade.BuildConfig;
import org.michaelbel.moviemade.data.dao.Video;
import org.michaelbel.moviemade.utils.ConstantsKt;
import org.michaelbel.moviemade.utils.EmptyViewMode;
import org.michaelbel.moviemade.data.dao.VideosResponse;
import org.michaelbel.moviemade.data.service.MOVIES;
import org.michaelbel.moviemade.utils.ApiFactory;
import org.michaelbel.moviemade.utils.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class TrailersPresenter extends MvpPresenter<TrailersMvp> {

    private final CompositeDisposable disposables = new CompositeDisposable();

    void loadTrailers(int movieId) {
        if (NetworkUtil.INSTANCE.notConnected()) {
            getViewState().setError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        MOVIES service = ApiFactory.createService2(MOVIES.class);
        Observable<VideosResponse> observable = service.getVideos(movieId, BuildConfig.TMDB_API_KEY, ConstantsKt.en_US).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposables.add(observable.subscribeWith(new DisposableObserver<VideosResponse>() {
            @Override
            public void onNext(VideosResponse response) {
                List<Video> results = new ArrayList<>(response.getTrailers());
                if (results.isEmpty()) {
                    getViewState().setError(EmptyViewMode.MODE_NO_TRAILERS);
                    Log.e("2580", response.toString());
                    return;
                }
                getViewState().setTrailers(response.getTrailers());
            }

            @Override
            public void onError(Throwable e) {
                getViewState().setError(EmptyViewMode.MODE_NO_TRAILERS);
                Log.e("2580", e.getMessage());
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