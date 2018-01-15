package org.michaelbel.moviemade.mvp.view;

import com.arellomobile.mvp.MvpView;

import org.michaelbel.moviemade.rest.model.Movie;
import org.michaelbel.moviemade.rest.model.People;

import java.util.List;

public interface MvpSearchView {

    interface SearchMovies extends MvpView {

        void searchStart();

        void showNoResults();

        void searchComplete(List<Movie> movies, int totalResults);

        void nextPageLoaded(List<Movie> newMovies);

        void showNoConnection();
    }

    interface SearchPeople extends MvpView {

        void searchStart();

        void showNoResults();

        void searchComplete(List<People> people, int totalResults);

        void nextPageLoaded(List<People> newPeople);

        void showNoConnection();
    }
}