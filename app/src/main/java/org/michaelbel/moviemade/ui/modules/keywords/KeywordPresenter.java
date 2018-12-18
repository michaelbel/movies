package org.michaelbel.moviemade.ui.modules.keywords;

import org.michaelbel.moviemade.data.entity.Movie;
import org.michaelbel.moviemade.data.service.KeywordsService;
import org.michaelbel.moviemade.utils.EmptyViewMode;
import org.michaelbel.moviemade.utils.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class KeywordPresenter implements KeywordContract.Presenter {

    private int page;
    private KeywordContract.View view;
    private KeywordContract.Repository repository;
    private CompositeDisposable disposables = new CompositeDisposable();

    public KeywordPresenter(KeywordContract.View view, KeywordsService service) {
        this.view = view;
        this.repository = new KeywordRepository(service);
    }

    @Override
    public void getMovies(int keywordId) {
        // Fixme.
        if (NetworkUtil.INSTANCE.notConnected()) {
            view.setError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        page = 1;
        disposables.add(repository.getMovies(keywordId, page)
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
    public void getMoviesNext(int keywordId) {
        page++;
        disposables.add(repository.getMovies(keywordId, page)
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