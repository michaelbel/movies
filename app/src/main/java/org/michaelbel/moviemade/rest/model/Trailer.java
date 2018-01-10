package org.michaelbel.moviemade.rest.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Trailer implements Serializable {

    @SerializedName("id")
    public String id;

    @SerializedName("iso_639_1")
    public String lang;

    @SerializedName("iso_3166_1")
    public String country;

    @SerializedName("key")
    public String key;

    @SerializedName("name")
    public String name;

    @SerializedName("site")
    public String site;

    @SerializedName("size")
    public String size;

    @SerializedName("type")
    public String type;
}