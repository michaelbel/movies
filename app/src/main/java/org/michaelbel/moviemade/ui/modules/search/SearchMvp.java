package org.michaelbel.moviemade.ui.modules.search;

import com.arellomobile.mvp.MvpView;

import org.michaelbel.moviemade.annotation.EmptyViewMode;
import org.michaelbel.moviemade.data.TmdbObject;
import org.michaelbel.moviemade.data.dao.Movie;

import java.util.List;

public interface SearchMvp extends MvpView {

    void searchStart();

    void showResults(List<Movie> results, boolean firstPage);

    void showError(@EmptyViewMode int mode);
}