package org.michaelbel.moviemade.ui.modules.search;

import org.jetbrains.annotations.NotNull;
import org.michaelbel.moviemade.data.entity.Movie;
import org.michaelbel.moviemade.utils.EmptyViewMode;
import org.michaelbel.moviemade.utils.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class SearchMoviesPresenter implements SearchContract.Presenter {

    private int page;
    private String currentQuery;
    private SearchContract.View view;
    private SearchContract.Repository repository;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public SearchMoviesPresenter(SearchRepository repository) {
        this.repository = repository;
    }

    @Override
    public void setView(@NotNull SearchContract.View view) {
        this.view = view;
    }

    @Override
    public void search(@NotNull String query) {
        currentQuery = query;
        view.searchStart();

        // Fixme.
        if (NetworkUtil.INSTANCE.notConnected()) {
            view.setError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        page = 1;
        disposables.add(repository.search(query, page)
            .subscribe(moviesResponse -> {
                // Fixme
                List<Movie> results = new ArrayList<>(moviesResponse.getMovies());
                if (results.isEmpty()) {
                    view.setError(EmptyViewMode.MODE_NO_RESULTS);
                    return;
                }
                view.setMovies(results);
            }, throwable -> view.setError(EmptyViewMode.MODE_NO_RESULTS)));
    }

    @Override
    public void loadNextResults() {
        page++;
        disposables.add(repository.search(currentQuery, page)
            .subscribe(moviesResponse -> view.setMovies(moviesResponse.getMovies())));
    }

    @Override
    public void onDestroy() {
        disposables.dispose();
    }
}