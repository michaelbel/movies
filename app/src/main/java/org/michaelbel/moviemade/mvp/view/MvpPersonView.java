package org.michaelbel.moviemade.mvp.view;

import com.arellomobile.mvp.MvpView;

import org.michaelbel.moviemade.rest.model.Person;

public interface MvpPersonView extends MvpView {

    void showPerson(Person person);

    void showError();

    void showComplete();
}