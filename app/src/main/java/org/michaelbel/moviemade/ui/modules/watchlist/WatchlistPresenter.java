package org.michaelbel.moviemade.ui.modules.watchlist;

import org.jetbrains.annotations.NotNull;
import org.michaelbel.moviemade.data.entity.Movie;
import org.michaelbel.moviemade.utils.EmptyViewMode;
import org.michaelbel.moviemade.utils.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class WatchlistPresenter implements WatchlistContract.Presenter {

    private int page;
    private WatchlistContract.View view;
    private WatchlistContract.Repository repository;
    private CompositeDisposable disposables = new CompositeDisposable();

    public WatchlistPresenter(WatchlistRepository repository) {
        this.repository = repository;
    }

    @Override
    public void setView(@NotNull WatchlistContract.View view) {
        this.view = view;
    }

    @Override
    public void getWatchlistMovies(int accountId, @NotNull String sessionId) {
        // Fixme.
        if (NetworkUtil.INSTANCE.notConnected()) {
            view.setError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        page = 1;
        disposables.add(repository.getWatchlistMovies(accountId, sessionId, page)
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
    public void getWatchlistMoviesNext(int accountId, @NotNull String sessionId) {
        page++;
        disposables.add(repository.getWatchlistMovies(accountId, sessionId, page)
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
    public void onDestroy() {
        disposables.dispose();
    }
}