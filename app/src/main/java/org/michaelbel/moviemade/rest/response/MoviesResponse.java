package org.michaelbel.moviemade.rest.response;

import com.google.gson.annotations.SerializedName;

import org.michaelbel.tmdb.v3.json.Movie;

import java.util.List;

public class MoviesResponse {

    @SerializedName("id")
    public int id;

    @SerializedName("page")
    public int page;

    @SerializedName("total_pages")
    public int totalPages;

    @SerializedName("total_results")
    public int totalResults;

    @SerializedName("results")
    public List<Movie> movies;
}