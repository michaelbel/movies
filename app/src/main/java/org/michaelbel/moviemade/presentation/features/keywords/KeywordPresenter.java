package org.michaelbel.moviemade.presentation.features.keywords;

import org.jetbrains.annotations.NotNull;
import org.michaelbel.moviemade.core.entity.Movie;
import org.michaelbel.moviemade.core.utils.EmptyViewMode;
import org.michaelbel.moviemade.core.utils.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class KeywordPresenter implements KeywordContract.Presenter {

    private int page;
    private KeywordContract.View view;
    private KeywordContract.Repository repository;
    private CompositeDisposable disposables = new CompositeDisposable();

    public KeywordPresenter(KeywordRepository repository) {
        this.repository = repository;
    }

    @Override
    public void attach(@NotNull KeywordContract.View view) {
        this.view = view;
    }

    @Override
    public void getMovies(int keywordId) {
        if (!NetworkUtil.INSTANCE.isNetworkConnected()) {
            view.setError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        page = 1;
        disposables.add(repository.getMovies(keywordId, page)
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
    public void getMoviesNext(int keywordId) {
        if (!NetworkUtil.INSTANCE.isNetworkConnected()) {
            return;
        }

        page++;
        disposables.add(repository.getMovies(keywordId, page).subscribe(response -> view.setMovies(response.getMovies())));
    }

    @Override
    public void destroy() {
        disposables.dispose();
    }
}