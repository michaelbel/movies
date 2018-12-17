package org.michaelbel.moviemade.ui.modules.reviews;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.michaelbel.moviemade.BuildConfig;
import org.michaelbel.moviemade.Moviemade;
import org.michaelbel.moviemade.data.entity.Review;
import org.michaelbel.moviemade.data.service.MoviesService;
import org.michaelbel.moviemade.utils.EmptyViewMode;
import org.michaelbel.moviemade.utils.NetworkUtil;
import org.michaelbel.moviemade.utils.TmdbConfigKt;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class ReviewsPresenter extends MvpPresenter<ReviewsMvp> {

    @Inject
    MoviesService service;

    private final CompositeDisposable disposables = new CompositeDisposable();

    public ReviewsPresenter() {
        Moviemade.getAppComponent().injest(this);
    }

    public void getReviews(int movieId) {
        // TODO add to response.
        if (NetworkUtil.INSTANCE.notConnected()) {
            getViewState().setError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        disposables.add(service.getReviews(movieId, BuildConfig.TMDB_API_KEY, TmdbConfigKt.en_US, 1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(reviewsResponse -> {
                List<Review> results = new ArrayList<>(reviewsResponse.getReviews());
                if (results.isEmpty()) {
                    getViewState().setError(EmptyViewMode.MODE_NO_REVIEWS);
                    return;
                }
                getViewState().setReviews(reviewsResponse.getReviews());
            }, throwable -> getViewState().setError(EmptyViewMode.MODE_NO_REVIEWS)));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposables.dispose();
    }
}