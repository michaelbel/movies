package org.michaelbel.moviemade.ui.modules.keywords;

import org.jetbrains.annotations.NotNull;
import org.michaelbel.moviemade.BuildConfig;
import org.michaelbel.moviemade.data.entity.KeywordsResponse;
import org.michaelbel.moviemade.data.service.MoviesService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class KeywordsRepository implements KeywordsContract.Repository {

    private MoviesService service;

    KeywordsRepository(MoviesService service) {
        this.service = service;
    }

    @NotNull
    @Override
    public Observable<KeywordsResponse> getKeywords(int movieId) {
        return service.getKeywords(movieId, BuildConfig.TMDB_API_KEY)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    }
}
