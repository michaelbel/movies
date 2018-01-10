package org.michaelbel.moviemade.rest.response;

import com.google.gson.annotations.SerializedName;

import org.michaelbel.moviemade.rest.model.Movie;

import java.util.List;

public class MovieResponse {

    @SerializedName("results")
    public List<Movie> movies;

    @SerializedName("page")
    public int page;

    @SerializedName("total_pages")
    public int totalPages;

    @SerializedName("total_results")
    public int totalResults;

    @SerializedName("dates")
    public ResultDates resultDates;

    public class ResultDates {

        @SerializedName("minimum")
        public String minimumDate;

        @SerializedName("maximum")
        public String maximumDate;
    }
}