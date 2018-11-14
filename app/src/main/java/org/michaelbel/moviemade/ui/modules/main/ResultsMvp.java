package org.michaelbel.moviemade.ui.modules.main;

import com.arellomobile.mvp.MvpView;

import org.michaelbel.moviemade.annotation.EmptyViewMode;
import org.michaelbel.moviemade.data.dao.Movie;

import java.util.List;

public interface ResultsMvp extends MvpView {

    void showResults(List<Movie> results, boolean firstPage);

    void showError(@EmptyViewMode int mode);
}