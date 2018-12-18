package org.michaelbel.moviemade.ui.modules.main;

import org.jetbrains.annotations.NotNull;
import org.michaelbel.moviemade.BuildConfig;
import org.michaelbel.moviemade.data.entity.MoviesResponse;
import org.michaelbel.moviemade.data.service.MoviesService;
import org.michaelbel.moviemade.utils.TmdbConfigKt;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainRepository implements MainContract.Repository {

    private MoviesService service;

    MainRepository(MoviesService service) {
        this.service = service;
    }

    @NotNull
    @Override
    public Observable<MoviesResponse> getNowPlaying(int page) {
        return service.getNowPlaying(BuildConfig.TMDB_API_KEY, TmdbConfigKt.en_US, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    }

    @NotNull
    @Override
    public Observable<MoviesResponse> getTopRated(int page) {
        return service.getTopRated(BuildConfig.TMDB_API_KEY, TmdbConfigKt.en_US, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    }

    @NotNull
    @Override
    public Observable<MoviesResponse> getUpcoming(int page) {
        return service.getUpcoming(BuildConfig.TMDB_API_KEY, TmdbConfigKt.en_US, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    }
}