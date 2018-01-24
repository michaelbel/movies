package org.michaelbel.moviemade.mvp.view;

import com.arellomobile.mvp.MvpView;

import org.michaelbel.moviemade.app.annotation.EmptyViewMode;
import org.michaelbel.moviemade.rest.TmdbObject;

import java.util.List;

@Deprecated
public interface MvpResultsView extends MvpView {

    void showResults(List<TmdbObject> results);

    void showError(@EmptyViewMode int mode);
}