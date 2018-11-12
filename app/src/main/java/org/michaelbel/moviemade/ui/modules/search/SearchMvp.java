package org.michaelbel.moviemade.ui.modules.search;

import com.arellomobile.mvp.MvpView;

import org.michaelbel.moviemade.annotation.EmptyViewMode;
import org.michaelbel.tmdb.TmdbObject;

import java.util.List;

public interface SearchMvp extends MvpView {

    void searchStart();

    void showResults(List<TmdbObject> results, boolean firstPage);

    void showError(@EmptyViewMode int mode);
}