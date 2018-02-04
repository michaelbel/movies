package org.michaelbel.moviemade.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.michaelbel.moviemade.app.Url;
import org.michaelbel.moviemade.app.annotation.EmptyViewMode;
import org.michaelbel.moviemade.mvp.view.MvpResultsView;
import org.michaelbel.moviemade.rest.ApiFactory;
import org.michaelbel.moviemade.rest.TmdbObject;
import org.michaelbel.moviemade.rest.api.service.COLLECTIONS;
import org.michaelbel.moviemade.rest.model.v3.Collection;
import org.michaelbel.moviemade.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

@InjectViewState
public class CollectionPresenter extends MvpPresenter<MvpResultsView> {

    private Disposable disposable;

    public void loadMovies(int collectionId) {
        if (collectionId == 0) {
            getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
            return;
        }

        if (NetworkUtils.notConnected()) {
            getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        COLLECTIONS service = ApiFactory.createService(COLLECTIONS.class);
        Observable<Collection> observable = service.getDetails(collectionId, Url.TMDB_API_KEY, Url.en_US).observeOn(AndroidSchedulers.mainThread());
        disposable = observable.subscribeWith(new DisposableObserver<Collection>() {
            @Override
            public void onNext(Collection collection) {
                List<TmdbObject> results = new ArrayList<>(collection.movies);
                if (results.isEmpty()) {
                    getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }
                getViewState().showResults(results, true);
            }

            @Override
            public void onError(Throwable e) {
                getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
                e.printStackTrace();
            }

            @Override
            public void onComplete() {}
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}