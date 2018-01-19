package org.michaelbel.moviemade.rest.response;

import com.google.gson.annotations.SerializedName;

import org.michaelbel.moviemade.rest.model.Company;
import org.michaelbel.moviemade.rest.model.v3.Collection;

import java.util.List;

public class CollectionResponse {

    @SerializedName("results")
    public List<Collection> collections;

    @SerializedName("results")
    public List<Company> companies;

    @SerializedName("page")
    public int page;

    @SerializedName("total_pages")
    public int totalPages;

    @SerializedName("total_results")
    public int totalResults;
}