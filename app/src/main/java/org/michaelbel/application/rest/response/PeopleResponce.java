package org.michaelbel.application.rest.response;

import com.google.gson.annotations.SerializedName;

import org.michaelbel.application.rest.model.People;

import java.util.List;

@SuppressWarnings("all")
public class PeopleResponce {

    @SerializedName("page")
    public int page;

    @SerializedName("results")
    public List<People> people;

    @SerializedName("total_pages")
    public int totalPages;

    @SerializedName("total_results")
    public int totalResults;
}