package org.michaelbel.moviemade.presentation.features.similar;

import org.jetbrains.annotations.NotNull;
import org.michaelbel.moviemade.core.utils.Error;
import org.michaelbel.moviemade.core.utils.NetworkUtil;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.HttpException;

public class SimilarMoviesPresenter implements SimilarContract.Presenter {

    private int page;
    private SimilarContract.View view;
    private SimilarContract.Repository repository;
    private CompositeDisposable disposables = new CompositeDisposable();

    public SimilarMoviesPresenter(SimilarRepository repository) {
        this.repository = repository;
    }

    @Override
    public void attach(@NotNull SimilarContract.View view) {
        this.view = view;
    }

    @Override
    public void getSimilarMovies(int movieId) {
        if (!NetworkUtil.INSTANCE.isNetworkConnected()) {
            view.setError(new Throwable("No connection"), Error.ERR_NO_CONNECTION);
            return;
        }

        page = 1;
        disposables.add(repository.getSimilarMovies(movieId, page)
            .subscribe(
                response -> {
                    if (response.getMovies().isEmpty()) {
                        view.setError(new Throwable("No movies"), Error.ERR_NO_MOVIES);
                        return;
                    }
                    view.setMovies(response.getMovies());
                },
                e -> view.setError(e, ((HttpException) e).code())
            )
        );
    }

    @Override
    public void getSimilarMoviesNext(int movieId) {
        if (!NetworkUtil.INSTANCE.isNetworkConnected()) {
            return;
        }

        page++;
        disposables.add(repository.getSimilarMovies(movieId, page)
            .subscribe(response -> view.setMovies(response.getMovies())));
    }

    @Override
    public void destroy() {
        disposables.dispose();
    }
}