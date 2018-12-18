package org.michaelbel.moviemade.ui.modules.recommendations;

import org.michaelbel.moviemade.data.entity.Movie;
import org.michaelbel.moviemade.data.service.MoviesService;
import org.michaelbel.moviemade.utils.EmptyViewMode;
import org.michaelbel.moviemade.utils.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class RcmdPresenter implements RcmdContract.Presenter {

    private int page;
    private RcmdContract.View view;
    private RcmdContract.Repository repository;
    private CompositeDisposable disposables = new CompositeDisposable();

    RcmdPresenter(RcmdContract.View view, MoviesService service) {
        this.view = view;
        this.repository = new RcmdRepository(service);
    }

    @Override
    public void getRcmdMovies(int movieId) {
        // Fixme
        if (NetworkUtil.INSTANCE.notConnected()) {
            view.setError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        page = 1;
        disposables.add(repository.getRcmdMovies(movieId, page)
            .subscribe(response -> {
                // Fixme
                List<Movie> results = new ArrayList<>(response.getMovies());
                if (results.isEmpty()) {
                    view.setError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }
                view.setMovies(results);
            }, e -> view.setError(EmptyViewMode.MODE_NO_MOVIES)));
    }

    @Override
    public void getRcmdMoviesNext(int movieId) {
        page++;
        disposables.add(repository.getRcmdMovies(movieId, page)
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