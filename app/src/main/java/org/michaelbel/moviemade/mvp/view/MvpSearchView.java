package org.michaelbel.moviemade.mvp.view;

import com.arellomobile.mvp.MvpView;

import org.michaelbel.moviemade.app.annotation.EmptyViewMode;
import org.michaelbel.moviemade.rest.TmdbObject;

import java.util.List;

public interface MvpSearchView extends MvpView {

    void searchStart();

    void searchComplete(List<TmdbObject> results, int totalResults);

    void nextPageLoaded(List<TmdbObject> results);

    void showError(@EmptyViewMode int mode);
}