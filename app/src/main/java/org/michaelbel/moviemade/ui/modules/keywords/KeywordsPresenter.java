package org.michaelbel.moviemade.ui.modules.keywords;

import org.jetbrains.annotations.NotNull;
import org.michaelbel.moviemade.data.entity.Keyword;
import org.michaelbel.moviemade.utils.EmptyViewMode;
import org.michaelbel.moviemade.utils.NetworkUtil;
import org.michaelbel.moviemade.utils.RxUtil;

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
    public void setView(@NotNull KeywordsContract.View view) {
        this.view = view;
    }

    @Override
    public void getKeywords(int movieId) {
        // Fixme.
        if (NetworkUtil.INSTANCE.notConnected()) {
            view.setError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        subscription1 = repository.getKeywords(movieId)
            .subscribe(response -> {
                // Fixme.
                List<Keyword> results = new ArrayList<>(response.getKeywords());
                if (results.isEmpty()) {
                    view.setError(EmptyViewMode.MODE_NO_KEYWORDS);
                    return;
                }
                view.setKeywords(results);
            }, e -> view.setError(EmptyViewMode.MODE_NO_KEYWORDS));
    }

    @Override
    public void onDestroy() {
        RxUtil.INSTANCE.unsubscribe(subscription1);
    }
}