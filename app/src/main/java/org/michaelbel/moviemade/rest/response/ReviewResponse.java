package org.michaelbel.moviemade.rest.response;

import com.google.gson.annotations.SerializedName;

import org.michaelbel.moviemade.rest.model.v3.Review;

import java.util.List;

public class ReviewResponse {

    @SerializedName("id")
    public int id;

    @SerializedName("results")
    public List<Review> reviews;

    @SerializedName("page")
    public int page;

    @SerializedName("total_pages")
    public int totalPages;

    @SerializedName("total_results")
    public int totalResults;
}