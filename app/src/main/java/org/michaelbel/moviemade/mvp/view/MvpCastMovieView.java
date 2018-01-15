package org.michaelbel.moviemade.mvp.view;

import com.arellomobile.mvp.MvpView;

import org.michaelbel.moviemade.app.annotation.EmptyViewMode;
import org.michaelbel.moviemade.rest.model.Cast;

import java.util.List;

public interface MvpCastMovieView extends MvpView {

    void showResults(List<Cast> newCasts);

    void showError(@EmptyViewMode int mode);
}