package org.michaelbel.application.rest.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("all")
public class Review implements Serializable {

    @SerializedName("id")
    public String id;

    @SerializedName("author")
    public String author;

    @SerializedName("content")
    public String content;

    @SerializedName("iso_639_1")
    public String lang;

    @SerializedName("media_id")
    public int mediaId;

    @SerializedName("media_title")
    public String mediaTitle;

    @SerializedName("media_type")
    public String mediaType;

    @SerializedName("url")
    public String url;
}