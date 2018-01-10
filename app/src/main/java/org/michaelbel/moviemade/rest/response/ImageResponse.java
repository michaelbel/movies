package org.michaelbel.moviemade.rest.response;

import com.google.gson.annotations.SerializedName;

import org.michaelbel.moviemade.rest.model.Backdrop;
import org.michaelbel.moviemade.rest.model.Poster;

import java.util.List;

@SuppressWarnings("all")
public class ImageResponse {

    @SerializedName("id")
    public int id;

    @SerializedName("backdrops")
    public List<Backdrop> backdrops;

    @SerializedName("posters")
    public List<Poster> posters;
}