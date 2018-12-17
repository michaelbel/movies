package org.michaelbel.moviemade.ui.modules.trailers;

import org.michaelbel.moviemade.Logger;
import org.michaelbel.moviemade.data.entity.Video;
import org.michaelbel.moviemade.data.entity.VideosResponse;
import org.michaelbel.moviemade.data.service.MoviesService;
import org.michaelbel.moviemade.utils.EmptyViewMode;
import org.michaelbel.moviemade.utils.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;

public class TrailersPresenter implements TrailersContract.Presenter {

    private TrailersContract.View view;
    private TrailersContract.Repository repository;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public TrailersPresenter(TrailersContract.View view, MoviesService service) {
        this.view = view;
        this.repository = new TrailersRepository(service);
    }

    @Override
    public void getVideos(int movieId) {
        if (NetworkUtil.INSTANCE.notConnected()) {
            view.setError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        disposables.add(repository.getVideos(movieId)
            .doOnTerminate(() -> view.hideLoading())
            .subscribe(videosResponse -> {
                List<Video> results = new ArrayList<>(videosResponse.getTrailers());
                if (results.isEmpty()) {
                    view.setError(EmptyViewMode.MODE_NO_TRAILERS);
                    return;
                }
                view.setTrailers(videosResponse.getTrailers());
            }, throwable -> view.setError(EmptyViewMode.MODE_NO_TRAILERS)));
    }

    @Override
    public void onDestroy() {
        disposables.dispose();
    }
}