package org.michaelbel.application.rest.response;

import com.google.gson.annotations.SerializedName;

import org.michaelbel.application.rest.model.Cast;
import org.michaelbel.application.rest.model.Crew;

import java.util.List;

@SuppressWarnings("all")
public class CreditResponse {

    @SerializedName("id")
    public int id;

    @SerializedName("cast")
    public List<Cast> casts;

    @SerializedName("crew")
    public List<Crew> crews;
}