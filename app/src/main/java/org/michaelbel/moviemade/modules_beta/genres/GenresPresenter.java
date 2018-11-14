package org.michaelbel.moviemade.modules_beta.genres;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.michaelbel.moviemade.BuildConfig;
import org.michaelbel.moviemade.ConstantsKt;
import org.michaelbel.moviemade.annotation.EmptyViewMode;
import org.michaelbel.moviemade.data.dao.GenresResponse;
import org.michaelbel.moviemade.data.service.GENRES;
import org.michaelbel.moviemade.extensions.NetworkUtil;
import org.michaelbel.moviemade.ApiFactory;
import org.michaelbel.moviemade.ui.modules.main.ResultsMvp;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class GenresPresenter extends MvpPresenter<ResultsMvp> {

    private final CompositeDisposable disposables = new CompositeDisposable();

    public void loadGenres() {
        if (NetworkUtil.INSTANCE.notConnected()) {
            getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        GENRES service = ApiFactory.createService2(GENRES.class);
        Observable<GenresResponse> observable = service.getMovieList(BuildConfig.TMDB_API_KEY, ConstantsKt.en_US).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposables.add(observable.subscribeWith(new DisposableObserver<GenresResponse>() {
            @Override
            public void onNext(GenresResponse response) {
               //List<TmdbObject> results = new ArrayList<>(response.genres);
               //getViewState().showResults(results, true);
            }

            @Override
            public void onError(Throwable e) {
                getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
            }

            @Override
            public void onComplete() {}
        }));
    }

    @Override
    public void onDestroy() {
        disposables.dispose();
        super.onDestroy();
    }
}