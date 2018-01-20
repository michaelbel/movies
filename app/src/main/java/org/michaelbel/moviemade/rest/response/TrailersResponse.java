package org.michaelbel.moviemade.rest.response;

import com.google.gson.annotations.SerializedName;

import org.michaelbel.moviemade.rest.model.v3.Trailer;

import java.util.List;

public class TrailersResponse {

    @SerializedName("id")
    public int id;

    @SerializedName("results")
    public List<Trailer> trailers;
}