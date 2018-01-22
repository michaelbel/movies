package org.michaelbel.moviemade.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.michaelbel.moviemade.app.Url;
import org.michaelbel.moviemade.app.annotation.EmptyViewMode;
import org.michaelbel.moviemade.mvp.view.MvpResultsView;
import org.michaelbel.moviemade.rest.ApiFactory;
import org.michaelbel.moviemade.rest.TmdbObject;
import org.michaelbel.moviemade.rest.api.MOVIES;
import org.michaelbel.moviemade.rest.response.ReviewResponse;
import org.michaelbel.moviemade.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class ReviewsMoviePresenter extends MvpPresenter<MvpResultsView> {

    public int page;
    public int totalPages;
    public boolean loading;
    public boolean loadingLocked;

    private int movieId;

    public void loadReviews(int movieId) {
        this.movieId = movieId;

        if (movieId == 0) {
            getViewState().showError(EmptyViewMode.MODE_NO_REVIEWS);
            return;
        }

        if (NetworkUtils.notConnected()) {
            getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        page = 1;
        totalPages = 0;
        loading = false;
        loadingLocked = false;

        MOVIES service = ApiFactory.createService(MOVIES.class);
        service.getReviews(movieId, Url.TMDB_API_KEY, Url.en_US, page)
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(new Observer<ReviewResponse>() {
                   @Override
                   public void onSubscribe(Disposable d) {

                   }

                   @Override
                   public void onNext(ReviewResponse response) {
                       totalPages = response.totalPages;

                       List<TmdbObject> results = new ArrayList<>();
                       results.addAll(response.reviews);

                       if (results.isEmpty()) {
                           getViewState().showError(EmptyViewMode.MODE_NO_REVIEWS);
                           return;
                       }

                       getViewState().showResults(results);
                       page++;
                   }

                   @Override
                   public void onError(Throwable e) {
                       getViewState().showError(EmptyViewMode.MODE_NO_REVIEWS);
                   }

                   @Override
                   public void onComplete() {

                   }
               });
    }

    public void loadResults() {
        MOVIES service = ApiFactory.createService(MOVIES.class);
        service.getReviews(movieId, Url.TMDB_API_KEY, Url.en_US, page)
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(new Observer<ReviewResponse>() {
                   @Override
                   public void onSubscribe(Disposable d) {

                   }

                   @Override
                   public void onNext(ReviewResponse response) {
                       List<TmdbObject> results = new ArrayList<>();
                       results.addAll(response.reviews);

                       if (results.isEmpty()) {
                           return;
                       }

                       getViewState().showResults(results);
                       loading = false;
                       page++;
                   }

                   @Override
                   public void onError(Throwable e) {
                       loadingLocked = true;
                       loading = false;
                   }

                   @Override
                   public void onComplete() {

                   }
               });

        loading = true;
    }
}