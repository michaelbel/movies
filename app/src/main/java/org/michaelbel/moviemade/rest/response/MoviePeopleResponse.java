package org.michaelbel.moviemade.rest.response;

import com.google.gson.annotations.SerializedName;

import org.michaelbel.moviemade.data.dao.Movie;

import java.util.List;

public class MoviePeopleResponse {

    @SerializedName("id")
    public int peopleId;

    @SerializedName("cast")
    public List<Movie> castMovies;

    @SerializedName("crew")
    public List<Movie> crewMovies;
}
