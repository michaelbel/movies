package org.michaelbel.moviemade.rest.model.v3;

import com.google.gson.annotations.SerializedName;

import org.michaelbel.moviemade.rest.model.Backdrop;

import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

public class CollectionImages extends RealmObject {

    @SerializedName("id")
    public int id;

    @Ignore
    @SerializedName("posters")
    public List<Backdrop> posters;

    @Ignore
    @SerializedName("backdrops")
    public List<Backdrop> backdrops;
}