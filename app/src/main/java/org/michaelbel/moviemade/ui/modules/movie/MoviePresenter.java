package org.michaelbel.moviemade.ui.modules.movie;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.michaelbel.moviemade.BuildConfig;
import org.michaelbel.moviemade.Moviemade;
import org.michaelbel.moviemade.data.dao.Movie;
import org.michaelbel.moviemade.data.service.MOVIES;
import org.michaelbel.moviemade.utils.AndroidExtensions;
import org.michaelbel.moviemade.utils.AndroidUtils;
import org.michaelbel.moviemade.utils.ConstantsKt;
import org.michaelbel.moviemade.utils.NetworkUtil;

import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

@InjectViewState
public class MoviePresenter extends MvpPresenter<MovieMvp> {

    private final CompositeDisposable disposables = new CompositeDisposable();

    @Inject Retrofit retrofit;

    void setMovieDetailsFromExtra(Movie movie) {
        getViewState().setPoster(movie.getPosterPath());
        getViewState().setMovieTitle(movie.getTitle());
        getViewState().setOverview(movie.getOverview());
        getViewState().setVoteAverage(movie.getVoteAverage());
        getViewState().setVoteCount(movie.getVoteCount());
        getViewState().setReleaseDate(AndroidExtensions.formatReleaseDate(movie.getReleaseDate()));
        getViewState().setOriginalLanguage(AndroidUtils.formatOriginalLanguage(movie.getOriginalLanguage()));
        getViewState().setWatching(false);
    }

    void loadMovieDetails(int movieId) {
        if (NetworkUtil.INSTANCE.notConnected()) {
            getViewState().showConnectionError();
            return;
        }

        Moviemade.getComponent().injest(this);
        MOVIES service = retrofit.create(MOVIES.class);
        Observable<Movie> observable = service.getDetails(movieId, BuildConfig.TMDB_API_KEY, ConstantsKt.en_US, "").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposables.add(observable.subscribeWith(new DisposableObserver<Movie>() {
            @Override
            public void onNext(Movie movie) {
                getViewState().setRuntime((Objects.requireNonNull(movie.getRuntime() != 0 ? AndroidExtensions.formatRuntime(movie.getRuntime()) : null)));
                getViewState().setTagline(movie.getTagline());
                getViewState().setURLs(movie.getImdbId(), movie.getHomepage());

                getViewState().showComplete(movie);
            }

            @Override
            public void onError(Throwable e) {
                getViewState().showConnectionError();
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