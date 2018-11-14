package org.michaelbel.moviemade.modules_beta.person;

import com.arellomobile.mvp.MvpView;

import org.michaelbel.moviemade.annotation.EmptyViewMode;
import org.michaelbel.moviemade.data.dao.Person;

public interface PersonMvp extends MvpView {

    void showPerson(Person person);

    void showError(@EmptyViewMode int mode);
}