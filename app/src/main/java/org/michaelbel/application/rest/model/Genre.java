package org.michaelbel.application.rest.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

@SuppressWarnings("all")
public class Genre extends RealmObject implements Serializable {

    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;
}