package org.michaelbel.moviemade.rest.response;

import com.google.gson.annotations.SerializedName;

import org.michaelbel.moviemade.rest.model.Genre;

import java.util.List;

public class MovieGenresResponse {

    @SerializedName("genres")
    public List<Genre> genres;

}