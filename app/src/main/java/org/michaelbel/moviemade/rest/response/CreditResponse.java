package org.michaelbel.moviemade.rest.response;

import com.google.gson.annotations.SerializedName;

import org.michaelbel.moviemade.rest.model.Cast;
import org.michaelbel.moviemade.rest.model.Crew;

import java.util.List;

public class CreditResponse {

    @SerializedName("id")
    public int id;

    @SerializedName("cast")
    public List<Cast> casts;

    @SerializedName("crew")
    public List<Crew> crews;
}