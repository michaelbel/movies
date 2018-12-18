package org.michaelbel.moviemade.ui.modules.reviews;

import org.michaelbel.moviemade.data.entity.Review;
import org.michaelbel.moviemade.data.service.MoviesService;
import org.michaelbel.moviemade.utils.EmptyViewMode;
import org.michaelbel.moviemade.utils.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class ReviewsPresenter implements ReviewsContract.Presenter {

    private ReviewsContract.View view;
    private ReviewsContract.Repository repository;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public ReviewsPresenter(ReviewsContract.View view, MoviesService service) {
        this.view = view;
        this.repository = new ReviewsRepository(service);
    }

    @Override
    public void getReviews(int movieId) {
        // Fixme.
        if (NetworkUtil.INSTANCE.notConnected()) {
            view.setError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        disposables.add(repository.getReviews(movieId)
            .subscribe(reviewsResponse -> {
                // Fixme.
                List<Review> results = new ArrayList<>(reviewsResponse.getReviews());
                if (results.isEmpty()) {
                    view.setError(EmptyViewMode.MODE_NO_REVIEWS);
                    return;
                }
                view.setReviews(reviewsResponse.getReviews());
            }, throwable -> view.setError(EmptyViewMode.MODE_NO_REVIEWS)));
    }

    @Override
    public void onDestroy() {
        disposables.dispose();
    }
}