package org.michaelbel.application.rest.response;

import com.google.gson.annotations.SerializedName;

import org.michaelbel.application.rest.model.Genre;

import java.util.List;

@SuppressWarnings("all")
public class MovieGenresResponse {

    @SerializedName("genres")
    public List<Genre> genresList;

}