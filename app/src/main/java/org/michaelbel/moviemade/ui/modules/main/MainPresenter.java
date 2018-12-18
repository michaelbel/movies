package org.michaelbel.moviemade.ui.modules.main;

import org.michaelbel.moviemade.data.entity.Movie;
import org.michaelbel.moviemade.data.service.MoviesService;
import org.michaelbel.moviemade.utils.EmptyViewMode;
import org.michaelbel.moviemade.utils.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class MainPresenter implements MainContract.Presenter {

    private int page;
    private MainContract.View view;
    private MainContract.Repository repository;
    private CompositeDisposable disposables = new CompositeDisposable();

    public MainPresenter(MainContract.View view, MoviesService service) {
        this.view = view;
        this.repository = new MainRepository(service);
    }

    @Override
    public void getNowPlaying() {
        // Fixme.
        if (NetworkUtil.INSTANCE.notConnected()) {
            view.setError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        page = 1;
        disposables.add(repository.getNowPlaying(page)
            .subscribe(response -> {
                List<Movie> results = new ArrayList<>(response.getMovies());
                // Fixme.
                if (results.isEmpty()) {
                    view.setError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }
                view.setMovies(results);
            }, e -> view.setError(EmptyViewMode.MODE_NO_MOVIES)));
    }

    @Override
    public void getNowPlayingNext() {
        page++;
        disposables.add(repository.getNowPlaying(page)
            .subscribe(response -> {
                // Fixme.
                List<Movie> results = new ArrayList<>(response.getMovies());
                if (results.isEmpty()) {
                    view.setError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }
                view.setMovies(results);
            }));
    }

    @Override
    public void getTopRated() {
        // Fixme.
        if (NetworkUtil.INSTANCE.notConnected()) {
            view.setError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        page = 1;
        disposables.add(repository.getTopRated(page)
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
    public void getTopRatedNext() {
        page++;
        disposables.add(repository.getTopRated(page)
            .subscribe(response -> {
                List<Movie> results = new ArrayList<>(response.getMovies());
                if (results.isEmpty()) {
                    view.setError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }
                view.setMovies(results);
            }));
    }

    @Override
    public void getUpcoming() {
        // Fixme.
        if (NetworkUtil.INSTANCE.notConnected()) {
            view.setError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        page = 1;
        disposables.add(repository.getUpcoming(page)
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
    public void getUpcomingNext() {
        page++;
        disposables.add(repository.getUpcoming(page)
            .subscribe(response -> {
                // Fixme.
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