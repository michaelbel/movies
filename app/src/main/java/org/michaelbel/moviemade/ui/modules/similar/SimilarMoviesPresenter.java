package org.michaelbel.moviemade.ui.modules.similar;

import org.jetbrains.annotations.NotNull;
import org.michaelbel.moviemade.data.entity.Movie;
import org.michaelbel.moviemade.utils.EmptyViewMode;
import org.michaelbel.moviemade.utils.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class SimilarMoviesPresenter implements SimilarContract.Presenter {

    private int page;
    private SimilarContract.View view;
    private SimilarContract.Repository repository;
    private CompositeDisposable disposables = new CompositeDisposable();

    public SimilarMoviesPresenter(SimilarRepository repository) {
        this.repository = repository;
    }

    @Override
    public void setView(@NotNull SimilarContract.View view) {
        this.view = view;
    }

    @Override
    public void getSimilarMovies(int movieId) {
        // Fixme
        if (NetworkUtil.INSTANCE.notConnected()) {
            view.setError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        page = 1;
        disposables.add(repository.getSimilarMovies(movieId, page)
            .subscribe(response -> {
                // Fixme.
                List<Movie> results = new ArrayList<>(response.getMovies());
                if (results.isEmpty()) {
                    view.setError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }
                view.setMovies(results);
            }, e -> view.setError(EmptyViewMode.MODE_NO_MOVIES)));
    }

    @Override
    public void getSimilarMoviesNext(int movieId) {
        page++;
        disposables.add(repository.getSimilarMovies(movieId, page)
            .subscribe(response -> {
                // Fixme
                List<Movie> results = new ArrayList<>(response.getMovies());
                if (results.isEmpty()) {
                    view.setError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }
                view.setMovies(results);
            }));
    }

    @Override
    public void onDestroy() {
        disposables.dispose();
    }
}