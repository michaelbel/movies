package org.michaelbel.moviemade.rest.response;

import com.google.gson.annotations.SerializedName;

import org.michaelbel.moviemade.rest.model.Movie;

import java.util.List;

@SuppressWarnings("all")
public class MoviePeopleResponse {

    @SerializedName("cast")
    public List<Movie> castMovies;

    @SerializedName("crew")
    public List<Movie> crewMovies;

    @SerializedName("id")
    public int peopleId;
}
