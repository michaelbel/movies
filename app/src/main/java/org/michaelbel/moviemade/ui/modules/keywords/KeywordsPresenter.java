package org.michaelbel.moviemade.ui.modules.keywords;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.michaelbel.moviemade.BuildConfig;
import org.michaelbel.moviemade.Moviemade;
import org.michaelbel.moviemade.data.entity.Keyword;
import org.michaelbel.moviemade.data.service.MoviesService;
import org.michaelbel.moviemade.utils.EmptyViewMode;
import org.michaelbel.moviemade.utils.NetworkUtil;
import org.michaelbel.moviemade.utils.RxUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class KeywordsPresenter extends MvpPresenter<KeywordsMvp> {

    private Disposable subscription1;

    @Inject
    MoviesService service;

    public KeywordsPresenter() {
        Moviemade.getAppComponent().injest(this);
    }

    public void getKeywords(int movieId) {
        if (NetworkUtil.INSTANCE.notConnected()) {
            getViewState().setError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        subscription1 = service.getKeywords(movieId, BuildConfig.TMDB_API_KEY).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(response -> {
                List<Keyword> results = new ArrayList<>(response.getKeywords());
                if (results.isEmpty()) {
                    getViewState().setError(EmptyViewMode.MODE_NO_KEYWORDS);
                    return;
                }
                getViewState().setKeywords(results);
            }, e -> {
                getViewState().setError(EmptyViewMode.MODE_NO_KEYWORDS);
                e.printStackTrace();
            });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxUtil.INSTANCE.unsubscribe(subscription1);
    }
}