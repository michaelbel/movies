package org.michaelbel.moviemade.rest.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

public class Genre extends RealmObject implements Serializable {

    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;
}