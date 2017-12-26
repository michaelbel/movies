package org.michaelbel.application.rest.response;

import com.google.gson.annotations.SerializedName;

import org.michaelbel.application.rest.model.Backdrop;
import org.michaelbel.application.rest.model.Poster;

import java.util.List;

@SuppressWarnings("all")
public class ImageResponse {

    @SerializedName("id")
    public int id;

    @SerializedName("backdrops")
    public List<Backdrop> backdropsList;

    @SerializedName("posters")
    public List<Poster> postersList;
}