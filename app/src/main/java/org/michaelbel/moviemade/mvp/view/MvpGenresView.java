package org.michaelbel.moviemade.mvp.view;

import com.arellomobile.mvp.MvpView;

import org.michaelbel.moviemade.app.annotation.EmptyViewMode;
import org.michaelbel.moviemade.rest.model.Genre;

import java.util.List;

public interface MvpGenresView extends MvpView {

    void showResults(List<Genre> genres);

    void showError(@EmptyViewMode int mode);
}