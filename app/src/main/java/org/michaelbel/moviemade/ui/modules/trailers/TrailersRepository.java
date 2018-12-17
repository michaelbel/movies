package org.michaelbel.moviemade.ui.modules.trailers;

import org.jetbrains.annotations.NotNull;
import org.michaelbel.moviemade.BuildConfig;
import org.michaelbel.moviemade.data.entity.VideosResponse;
import org.michaelbel.moviemade.data.service.MoviesService;
import org.michaelbel.moviemade.utils.TmdbConfigKt;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TrailersRepository implements TrailersContract.Repository {

    private MoviesService service;

    TrailersRepository(MoviesService service) {
        this.service = service;
    }

    @NotNull
    @Override
    public Observable<VideosResponse> getVideos(int movieId) {
        return service.getVideos(movieId, BuildConfig.TMDB_API_KEY, TmdbConfigKt.en_US)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    }
}