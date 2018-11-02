package org.michaelbel.moviemade.mvp.view;

import com.arellomobile.mvp.MvpView;

import org.michaelbel.moviemade.annotation.EmptyViewMode;
import org.michaelbel.moviemade.rest.TmdbObject;

import java.util.List;

public interface MvpSearchView extends MvpView {

    void searchStart();

    void showResults(List<TmdbObject> results, boolean firstPage);

    void showError(@EmptyViewMode int mode);
}