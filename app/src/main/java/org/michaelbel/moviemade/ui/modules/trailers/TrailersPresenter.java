package org.michaelbel.moviemade.ui.modules.trailers;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.michaelbel.moviemade.BuildConfig;
import org.michaelbel.moviemade.Moviemade;
import org.michaelbel.moviemade.data.dao.Video;
import org.michaelbel.moviemade.data.dao.VideosResponse;
import org.michaelbel.moviemade.data.service.MOVIES;
import org.michaelbel.moviemade.utils.EmptyViewMode;
import org.michaelbel.moviemade.utils.NetworkUtil;
import org.michaelbel.moviemade.utils.TmdbConfigKt;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

@InjectViewState
public class TrailersPresenter extends MvpPresenter<TrailersMvp> {

    @Inject Retrofit retrofit;

    private final CompositeDisposable disposables = new CompositeDisposable();

    public TrailersPresenter() {
        Moviemade.getComponent().injest(this);
    }

    public void getVideos(int movieId) {
        if (NetworkUtil.INSTANCE.notConnected()) {
            getViewState().setError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        MOVIES service = retrofit.create(MOVIES.class);
        Observable<VideosResponse> observable = service.getVideos(movieId, BuildConfig.TMDB_API_KEY, TmdbConfigKt.en_US).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposables.add(observable.subscribeWith(new DisposableObserver<VideosResponse>() {
            @Override
            public void onNext(VideosResponse response) {
                List<Video> results = new ArrayList<>(response.getTrailers());
                if (results.isEmpty()) {
                    getViewState().setError(EmptyViewMode.MODE_NO_TRAILERS);
                    return;
                }
                getViewState().setTrailers(response.getTrailers());
            }

            @Override
            public void onError(Throwable e) {
                getViewState().setError(EmptyViewMode.MODE_NO_TRAILERS);
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