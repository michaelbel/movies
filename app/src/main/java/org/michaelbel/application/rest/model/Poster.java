package org.michaelbel.application.rest.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

@SuppressWarnings("all")
public class Poster extends RealmObject implements Serializable {

    @SerializedName("aspect_ratio")
    public float aspectRatio;

    @SerializedName("file_path")
    public String filePath;

    @SerializedName("height")
    public int height;

    @SerializedName("width")
    public int width;

    @SerializedName("iso_639_1")
    public String code;

    @SerializedName("vote_averahe")
    public float voteAverage;

    @SerializedName("vote_count")
    public int voteCount;
}
