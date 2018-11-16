package org.michaelbel.moviemade.ui.modules.movie;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.michaelbel.moviemade.BuildConfig;
import org.michaelbel.moviemade.Moviemade;
import org.michaelbel.moviemade.data.constants.CreditsKt;
import org.michaelbel.moviemade.data.dao.Cast;
import org.michaelbel.moviemade.data.dao.CreditsResponse;
import org.michaelbel.moviemade.data.dao.Crew;
import org.michaelbel.moviemade.data.dao.Movie;
import org.michaelbel.moviemade.data.service.MOVIES;
import org.michaelbel.moviemade.utils.AndroidExtensions;
import org.michaelbel.moviemade.utils.ConstantsKt;
import org.michaelbel.moviemade.utils.LanguageUtil;
import org.michaelbel.moviemade.utils.NetworkUtil;

import java.util.ArrayList;
import java.util.List;
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
        getViewState().setOriginalLanguage(LanguageUtil.INSTANCE.formatLanguage(movie.getOriginalLanguage()));
        getViewState().setWatching(false);
    }

    void loadMovieDetails(int movieId) {
        if (NetworkUtil.INSTANCE.notConnected()) {
            getViewState().setConnectionError();
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
                getViewState().setConnectionError();
            }

            @Override
            public void onComplete() {
                loadCredits(movieId);
            }
        }));
    }

    private void loadCredits(int movieId) {
        Moviemade.getComponent().injest(this);
        MOVIES service = retrofit.create(MOVIES.class);
        Observable<CreditsResponse> observable = service.getCredits(movieId, BuildConfig.TMDB_API_KEY).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposables.add(observable.subscribeWith(new DisposableObserver<CreditsResponse>() {

            @Override
            public void onNext(CreditsResponse response) {
                reloadCredits(response.getCast(), response.getCrew());
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {}
        }));
    }

    private void reloadCredits(List<Cast> casts, List<Crew> crews) {
        List<String> actors = new ArrayList<>();
        for (Cast cast : casts) {
            actors.add(cast.getName());
        }
        StringBuilder actorsBuilder = new StringBuilder();
        for (String name: actors) {
            actorsBuilder.append(name);
            if (!Objects.equals(name, actors.get(actors.size() - 1))) {
                actorsBuilder.append(", ");
            }
        }

        List<String> directors = new ArrayList<>();
        List<String> writers = new ArrayList<>();
        List<String> producers = new ArrayList<>();
        for (Crew crew : crews) {
            switch (crew.getDepartment()) {
                case CreditsKt.DIRECTING:
                    directors.add(crew.getName());
                    break;
                case CreditsKt.WRITING:
                    writers.add(crew.getName());
                    break;
                case CreditsKt.PRODUCTION:
                    producers.add(crew.getName());
                    break;
            }
        }

        StringBuilder directorsBuilder = new StringBuilder();
        for (int i = 0; i < directors.size(); i++) {
            directorsBuilder.append(directors.get(i));
            // if item is not last and is not empty
            if (i != directors.size() - 1 && (directors.get(directors.size() - 1) != null)) {
                directorsBuilder.append(", ");
            }
        }

        StringBuilder writersBuilder = new StringBuilder();
        for (int i = 0; i < writers.size(); i++) {
            writersBuilder.append(writers.get(i));
            if (i != writers.size() - 1 && (writers.get(writers.size() - 1) != null)) {
                writersBuilder.append(", ");
            }
        }

        StringBuilder producersBuilder = new StringBuilder();
        for (int i = 0; i < producers.size(); i++) {
            producersBuilder.append(producers.get(i));
            if (i != producers.size() - 1 && (producers.get(producers.size() - 1) != null)) {
                producersBuilder.append(", ");
            }
        }

        getViewState().setCredits(actorsBuilder.toString(), directorsBuilder.toString(), writersBuilder.toString(), producersBuilder.toString());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposables.dispose();
    }
}