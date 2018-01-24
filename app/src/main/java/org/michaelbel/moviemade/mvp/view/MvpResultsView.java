package org.michaelbel.moviemade.mvp.view;

import com.arellomobile.mvp.MvpView;

import org.michaelbel.moviemade.app.annotation.EmptyViewMode;
import org.michaelbel.moviemade.rest.TmdbObject;

import java.util.List;

public interface MvpResultsView extends MvpView {

    void showResults(List<TmdbObject> results, boolean firstPage);

    void showError(@EmptyViewMode int mode);
}