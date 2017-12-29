package org.michaelbel.application.rest.response;

import com.google.gson.annotations.SerializedName;

import org.michaelbel.application.rest.model.Movie;

import java.util.List;

@SuppressWarnings("all")
public class MoviePeopleResponse {

    @SerializedName("cast")
    public List<Movie> castMovieList;

    @SerializedName("crew")
    public List<Movie> crewMovieList;

    @SerializedName("id")
    public int peopleId;
}
