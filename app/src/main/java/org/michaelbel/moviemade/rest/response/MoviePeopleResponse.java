package org.michaelbel.moviemade.rest.response;

import com.google.gson.annotations.SerializedName;

import org.michaelbel.tmdb.v3.json.Movie;

import java.util.List;

public class MoviePeopleResponse {

    @SerializedName("id")
    public int peopleId;

    @SerializedName("cast")
    public List<Movie> castMovies;

    @SerializedName("crew")
    public List<Movie> crewMovies;
}
