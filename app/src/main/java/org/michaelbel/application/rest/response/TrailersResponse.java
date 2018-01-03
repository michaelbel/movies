package org.michaelbel.application.rest.response;

import com.google.gson.annotations.SerializedName;

import org.michaelbel.application.rest.model.Trailer;

import java.util.List;

@SuppressWarnings("all")
public class TrailersResponse {

    @SerializedName("id")
    public int id;

    @SerializedName("results")
    public List<Trailer> trailers;
}