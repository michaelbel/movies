package org.michaelbel.moviemade.mvp.base;

import org.michaelbel.moviemade.rest.model.Cast;
import org.michaelbel.moviemade.rest.model.Genre;
import org.michaelbel.moviemade.rest.model.Movie;
import org.michaelbel.moviemade.rest.model.v3.Review;
import org.michaelbel.moviemade.rest.model.Trailer;

import java.util.ArrayList;

public interface MediaModel {

    void startMovie(Movie movie);

    void startPerson(Cast person);

    void startReview(Review review, Movie movie);

    void startTrailers(Movie movie, ArrayList<Trailer> list);

    void startGenre(Genre genre);

    void startGenres(ArrayList<Genre> list);
}
