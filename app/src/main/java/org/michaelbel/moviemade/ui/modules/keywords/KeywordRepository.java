package org.michaelbel.moviemade.ui.modules.keywords;

import org.jetbrains.annotations.NotNull;
import org.michaelbel.moviemade.BuildConfig;
import org.michaelbel.moviemade.Moviemade;
import org.michaelbel.moviemade.data.entity.MoviesResponse;
import org.michaelbel.moviemade.data.service.KeywordsService;
import org.michaelbel.moviemade.utils.AdultUtil;
import org.michaelbel.moviemade.utils.TmdbConfigKt;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class KeywordRepository implements KeywordContract.Repository {

    private KeywordsService service;

    KeywordRepository(KeywordsService service) {
        this.service = service;
    }

    @NotNull
    @Override
    public Observable<MoviesResponse> getMovies(int keywordId, int page) {
        return service.getMovies(keywordId, BuildConfig.TMDB_API_KEY, TmdbConfigKt.en_US, AdultUtil.INSTANCE.includeAdult(Moviemade.appContext), page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    }
}