package org.michaelbel.moviemade.mvp.view;

import com.arellomobile.mvp.MvpView;

import org.michaelbel.moviemade.app.annotation.EmptyViewMode;
import org.michaelbel.moviemade.rest.model.People;

import java.util.List;

public interface MvpPopularPeopleView extends MvpView {

    void showResults(List<People> newPeople);

    void showError(@EmptyViewMode int mode);
}