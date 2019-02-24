package org.michaelbel.moviemade.presentation.features.main;

import org.jetbrains.annotations.NotNull;
import org.michaelbel.moviemade.core.entity.Movie;
import org.michaelbel.moviemade.core.utils.EmptyViewMode;
import org.michaelbel.moviemade.core.utils.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class MainPresenter implements MainContract.Presenter {

    private int page;
    private MainContract.View view;
    private MainContract.Repository repository;
    private CompositeDisposable disposables = new CompositeDisposable();

    public MainPresenter(MainRepository repository) {
        this.repository = repository;
    }

    @Override
    public void attach(@NotNull MainContract.View view) {
        this.view = view;
    }

    @Override
    public void getNowPlaying() {
        if (!NetworkUtil.INSTANCE.isNetworkConnected()) {
            view.setError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        page = 1;
        disposables.add(repository.getNowPlaying(page)
            .subscribe(response -> {
                List<Movie> results = new ArrayList<>(response.getMovies());
                if (results.isEmpty()) {
                    view.setError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }
                view.setContent(results);
            }, e -> view.setError(EmptyViewMode.MODE_NO_MOVIES)));
    }

    @Override
    public void getNowPlayingNext() {
        if (!NetworkUtil.INSTANCE.isNetworkConnected()) return;

        page++;
        disposables.add(repository.getNowPlaying(page).subscribe(response -> view.setContent(response.getMovies())));
    }

    @Override
    public void getTopRated() {
        if (!NetworkUtil.INSTANCE.isNetworkConnected()) {
            view.setError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        page = 1;
        disposables.add(repository.getTopRated(page)
            .subscribe(response -> {
                List<Movie> results = new ArrayList<>(response.getMovies());
                if (results.isEmpty()) {
                    view.setError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }
                view.setContent(results);
            }, e -> view.setError(EmptyViewMode.MODE_NO_MOVIES)));
    }

    @Override
    public void getTopRatedNext() {
        if (!NetworkUtil.INSTANCE.isNetworkConnected()) return;

        page++;
        disposables.add(repository.getTopRated(page).subscribe(response -> view.setContent(response.getMovies())));
    }

    @Override
    public void getUpcoming() {
        if (!NetworkUtil.INSTANCE.isNetworkConnected()) {
            view.setError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        page = 1;
        disposables.add(repository.getUpcoming(page)
            .subscribe(response -> {
                List<Movie> results = new ArrayList<>(response.getMovies());
                if (results.isEmpty()) {
                    view.setError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }
                view.setContent(results);
            }, e -> view.setError(EmptyViewMode.MODE_NO_MOVIES)));
    }

    @Override
    public void getUpcomingNext() {
        if (!NetworkUtil.INSTANCE.isNetworkConnected()) return;

        page++;
        disposables.add(repository.getUpcoming(page).subscribe(response -> view.setContent(response.getMovies())));
    }

    @Override
    public void destroy() {
        disposables.dispose();
    }
}