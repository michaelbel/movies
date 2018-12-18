package org.michaelbel.moviemade.ui.modules.favorites;

import org.jetbrains.annotations.NotNull;
import org.michaelbel.moviemade.data.entity.Movie;
import org.michaelbel.moviemade.data.service.AccountService;
import org.michaelbel.moviemade.utils.EmptyViewMode;
import org.michaelbel.moviemade.utils.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class FavoritesPresenter implements FavoritesContract.Presenter {

    private int page;
    private FavoritesContract.View view;
    private FavoritesContract.Repository repository;
    private CompositeDisposable disposables = new CompositeDisposable();

    FavoritesPresenter(FavoritesContract.View view, AccountService service) {
        this.view = view;
        this.repository = new FavoriteRepository(service);
    }

    @Override
    public void getFavoriteMovies(int accountId, @NotNull String sessionId) {
        if (NetworkUtil.INSTANCE.notConnected()) {
            view.setError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        page = 1;
        // todo make map to check results.isEmpty()
        disposables.add(repository.getFavoriteMovies(accountId, sessionId, page)
                .subscribe(response -> {
                    List<Movie> results = new ArrayList<>(response.getMovies());
                    if (results.isEmpty()) {
                        view.setError(EmptyViewMode.MODE_NO_MOVIES);
                        return;
                    }
                    view.setMovies(results);
                }, e -> view.setError(EmptyViewMode.MODE_NO_MOVIES)));

        /*disposables.add(service.getFavoriteMovies(accountId, BuildConfig.TMDB_API_KEY, sessionId, TmdbConfigKt.en_US, OrderKt.ASC, page)
            .doOnSubscribe(disposable -> getViewState().showLoading())
            .doAfterTerminate(() -> getViewState().hideLoading())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(response -> getViewState().setMovies(new ArrayList<>(response.getMovies())), e -> getViewState().setError(EmptyViewMode.MODE_NO_MOVIES)));*/
    }

    @Override
    public void getFavoriteMoviesNext(int accountId, @NotNull String sessionId) {
        page++;
        disposables.add(repository.getFavoriteMovies(accountId, sessionId, page)
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