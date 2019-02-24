package org.michaelbel.moviemade.presentation.features.keywords;

import org.jetbrains.annotations.NotNull;
import org.michaelbel.moviemade.core.entity.Keyword;
import org.michaelbel.moviemade.core.utils.EmptyViewMode;
import org.michaelbel.moviemade.core.utils.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class KeywordsPresenter implements KeywordsContract.Presenter {

    private Disposable subscription1;
    private KeywordsContract.View view;
    private KeywordsContract.Repository repository;

    public KeywordsPresenter(KeywordsRepository repository) {
        this.repository = repository;
    }

    @Override
    public void attach(@NotNull KeywordsContract.View view) {
        this.view = view;
    }

    @Override
    public void getKeywords(int movieId) {
        if (!NetworkUtil.INSTANCE.isNetworkConnected()) {
            view.setError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        subscription1 = repository.getKeywords(movieId)
            .subscribe(response -> {
                List<Keyword> results = new ArrayList<>(response.getKeywords());
                if (results.isEmpty()) {
                    view.setError(EmptyViewMode.MODE_NO_KEYWORDS);
                    return;
                }
                view.setKeywords(results);
            }, e -> view.setError(EmptyViewMode.MODE_NO_KEYWORDS));
    }

    @Override
    public void destroy() {
        subscription1.dispose();
    }
}