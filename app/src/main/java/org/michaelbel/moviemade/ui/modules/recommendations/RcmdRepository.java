package org.michaelbel.moviemade.ui.modules.recommendations;

import org.jetbrains.annotations.NotNull;
import org.michaelbel.moviemade.BuildConfig;
import org.michaelbel.moviemade.data.entity.MoviesResponse;
import org.michaelbel.moviemade.data.service.MoviesService;
import org.michaelbel.moviemade.utils.TmdbConfigKt;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RcmdRepository implements RcmdContract.Repository {

    private MoviesService service;

    RcmdRepository(MoviesService service) {
        this.service = service;
    }

    @NotNull
    @Override
    public Observable<MoviesResponse> getRcmdMovies(int movieId, int page) {
        return service.getRecommendations(movieId, BuildConfig.TMDB_API_KEY, TmdbConfigKt.en_US, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    }
}