package org.michaelbel.moviemade.ui.modules.reviews;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.michaelbel.moviemade.BuildConfig;
import org.michaelbel.moviemade.ConstantsKt;
import org.michaelbel.moviemade.annotation.EmptyViewMode;
import org.michaelbel.moviemade.data.dao.Review;
import org.michaelbel.moviemade.data.dao.ReviewsResponse;
import org.michaelbel.moviemade.data.service.MOVIES;
import org.michaelbel.moviemade.extensions.NetworkUtil;
import org.michaelbel.moviemade.ApiFactory;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class ReviewsPresenter extends MvpPresenter<ReviewsMvp> {

    public int page = 1;
    public int totalPages;
    public boolean isLoading = false;
    public boolean isLastPage = false;

    private final CompositeDisposable disposables = new CompositeDisposable();

    public void loadReviews(int movieId) {
        if (NetworkUtil.INSTANCE.notConnected()) {
            getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        MOVIES service = ApiFactory.createService2(MOVIES.class);
        Observable<ReviewsResponse> observable = service.getReviews(movieId, BuildConfig.TMDB_API_KEY, ConstantsKt.en_US, page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposables.add(observable.subscribeWith(new DisposableObserver<ReviewsResponse>() {
            @Override
            public void onNext(ReviewsResponse response) {
                totalPages = response.getTotalPages();
                List<Review> results = new ArrayList<>(response.getReviews());
                if (results.isEmpty()) {
                    getViewState().showError(EmptyViewMode.MODE_NO_REVIEWS);
                    return;
                }
                getViewState().setReviews(results, true);
            }

            @Override
            public void onError(Throwable e) {
                getViewState().showError(EmptyViewMode.MODE_NO_REVIEWS);
            }

            @Override
            public void onComplete() {}
        }));
    }

    public void loadReviewsNext(int movieId) {
        MOVIES service = ApiFactory.createService2(MOVIES.class);
        Observable<ReviewsResponse> observable = service.getReviews(movieId, BuildConfig.TMDB_API_KEY, ConstantsKt.en_US, page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposables.add(observable.subscribeWith(new DisposableObserver<ReviewsResponse>() {
            @Override
            public void onNext(ReviewsResponse response) {
                List<Review> results = new ArrayList<>(response.getReviews());
                getViewState().setReviews(results, false);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
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