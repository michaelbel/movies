package org.michaelbel.moviemade.mvp.view;

import com.arellomobile.mvp.MvpView;

import org.michaelbel.moviemade.rest.model.People;

import java.util.List;

public interface MvpSearchPeopleView extends MvpView {

    void searchStart();

    void searchNoResults();

    void searchComplete(List<People> people, int totalResults);

    void nextPageLoaded(List<People> newPeople);

    void showError();
}