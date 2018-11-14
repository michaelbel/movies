package org.michaelbel.moviemade.ui.base;

import org.michaelbel.moviemade.data.dao.Cast;
import org.michaelbel.moviemade.data.dao.Collection;
import org.michaelbel.moviemade.data.dao.Company;
import org.michaelbel.moviemade.data.dao.Genre;
import org.michaelbel.moviemade.data.dao.Keyword;
import org.michaelbel.moviemade.data.dao.Movie;
import org.michaelbel.moviemade.data.dao.Person;
import org.michaelbel.moviemade.data.dao.Review;
import org.michaelbel.moviemade.model.MovieRealm;

import java.util.ArrayList;

public interface MediaMvp {

    void startMovie(Movie movie);

    void startTrailers(Movie movie);

    void startReviews(Movie movie);



    void startMovie(MovieRealm movie);

    void startPerson(Cast person);

    void startPerson(Person person);

    void startReview(Review review, Movie movie);

    void startReview(Review review, MovieRealm movie);

    void startGenre(Genre genre);

    void startGenres(ArrayList<Genre> list);

    void startKeyword(Keyword keyword);

    void startCollection(Collection collection);

    void startCompany(Company company);
}