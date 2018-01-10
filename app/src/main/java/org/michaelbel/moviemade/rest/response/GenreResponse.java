package org.michaelbel.moviemade.rest.response;

import com.google.gson.annotations.SerializedName;

import org.michaelbel.moviemade.rest.model.Movie;

import java.util.List;

@SuppressWarnings("all")
public class GenreResponse {

    @SerializedName("id")
    public int id;

    @SerializedName("page")
    public int page;

    @SerializedName("results")
    public List<Movie> movies;

    @SerializedName("total_pages")
    public int totalPages;

    @SerializedName("total_results")
    public int totalResults;
}