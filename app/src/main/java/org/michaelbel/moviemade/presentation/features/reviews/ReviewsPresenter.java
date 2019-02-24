package org.michaelbel.moviemade.presentation.features.reviews;

import org.jetbrains.annotations.NotNull;
import org.michaelbel.moviemade.core.entity.Review;
import org.michaelbel.moviemade.core.utils.EmptyViewMode;
import org.michaelbel.moviemade.core.utils.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class ReviewsPresenter implements ReviewsContract.Presenter {

    private ReviewsContract.View view;
    private ReviewsContract.Repository repository;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public ReviewsPresenter(ReviewsRepository repository) {
        this.repository = repository;
    }

    @Override
    public void attach(@NotNull ReviewsContract.View view) {
        this.view = view;
    }

    @Override
    public void getReviews(int movieId) {
        if (!NetworkUtil.INSTANCE.isNetworkConnected()) {
            view.setError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        disposables.add(repository.getReviews(movieId)
            .subscribe(reviewsResponse -> {
                List<Review> results = new ArrayList<>(reviewsResponse.getReviews());
                if (results.isEmpty()) {
                    view.setError(EmptyViewMode.MODE_NO_REVIEWS);
                    return;
                }
                view.setReviews(reviewsResponse.getReviews());
            }, throwable -> view.setError(EmptyViewMode.MODE_NO_REVIEWS)));
    }

    @Override
    public void destroy() {
        disposables.dispose();
    }
}