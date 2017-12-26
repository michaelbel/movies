package org.michaelbel.application.rest.response;

import com.google.gson.annotations.SerializedName;

import org.michaelbel.application.rest.model.Movie;

import java.util.List;

@SuppressWarnings("all")
public class MovieResponse {

    @SerializedName("results")
    public List<Movie> movieList;

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