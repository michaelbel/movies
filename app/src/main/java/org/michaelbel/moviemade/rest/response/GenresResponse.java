package org.michaelbel.moviemade.rest.response;

import com.google.gson.annotations.SerializedName;

import org.michaelbel.moviemade.rest.model.v3.Genre;

import java.util.List;

public class GenresResponse {

    @SerializedName("genres")
    public List<Genre> genres;

}