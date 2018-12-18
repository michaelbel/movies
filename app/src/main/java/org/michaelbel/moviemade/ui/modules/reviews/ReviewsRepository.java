package org.michaelbel.moviemade.ui.modules.reviews;

import org.jetbrains.annotations.NotNull;
import org.michaelbel.moviemade.BuildConfig;
import org.michaelbel.moviemade.data.entity.ReviewsResponse;
import org.michaelbel.moviemade.data.service.MoviesService;
import org.michaelbel.moviemade.utils.TmdbConfigKt;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ReviewsRepository implements ReviewsContract.Repository {

    private MoviesService service;

    ReviewsRepository(MoviesService service) {
        this.service = service;
    }

    @NotNull
    @Override
    public Observable<ReviewsResponse> getReviews(int movieId) {
        // FIXME paging
        return service.getReviews(movieId, BuildConfig.TMDB_API_KEY, TmdbConfigKt.en_US, 1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    }
}
