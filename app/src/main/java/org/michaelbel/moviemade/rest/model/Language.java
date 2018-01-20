package org.michaelbel.moviemade.rest.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

public class Language extends RealmObject implements Serializable {

    @SerializedName("iso_639_1")
    public String language;

    @SerializedName("name")
    public String name;
}