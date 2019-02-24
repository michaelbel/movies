package org.michaelbel.moviemade.presentation.features.watchlist;

import org.jetbrains.annotations.NotNull;
import org.michaelbel.moviemade.core.entity.Movie;
import org.michaelbel.moviemade.core.utils.EmptyViewMode;
import org.michaelbel.moviemade.core.utils.NetworkUtil;

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
    public void attach(@NotNull WatchlistContract.View view) {
        this.view = view;
    }

    @Override
    public void getWatchlistMovies(int accountId, @NotNull String sessionId) {
        if (!NetworkUtil.INSTANCE.isNetworkConnected()) {
            view.setError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        page = 1;
        disposables.add(repository.getWatchlistMovies(accountId, sessionId, page)
            .subscribe(response -> {
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
        if (!NetworkUtil.INSTANCE.isNetworkConnected()) return;

        page++;
        disposables.add(repository.getWatchlistMovies(accountId, sessionId, page).subscribe(response -> view.setMovies(response.getMovies())));
    }

    @Override
    public void destroy() {
        disposables.dispose();
    }
}