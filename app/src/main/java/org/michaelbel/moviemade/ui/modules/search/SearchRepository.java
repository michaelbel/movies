package org.michaelbel.moviemade.ui.modules.search;

import org.jetbrains.annotations.NotNull;
import org.michaelbel.moviemade.BuildConfig;
import org.michaelbel.moviemade.Moviemade;
import org.michaelbel.moviemade.data.entity.MoviesResponse;
import org.michaelbel.moviemade.data.service.SearchService;
import org.michaelbel.moviemade.utils.AdultUtil;
import org.michaelbel.moviemade.utils.TmdbConfigKt;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SearchRepository implements SearchContract.Repository {

    private SearchService service;

    SearchRepository(SearchService service) {
        this.service = service;
    }

    @NotNull
    @Override
    public Observable<MoviesResponse> search(@NotNull String query, int page) {
        return service.searchMovies(BuildConfig.TMDB_API_KEY, TmdbConfigKt.en_US, query, page, AdultUtil.INSTANCE.includeAdult(Moviemade.appContext), "")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    }
}