package org.michaelbel.application.rest.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

@SuppressWarnings("all")
public class Country extends RealmObject implements Serializable {

    @SerializedName("iso_3166_1")
    public String country;

    @SerializedName("name")
    public String name;
}