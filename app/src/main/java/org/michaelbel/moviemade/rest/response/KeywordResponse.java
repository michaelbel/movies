package org.michaelbel.moviemade.rest.response;

import com.google.gson.annotations.SerializedName;

import org.michaelbel.moviemade.rest.model.v3.Keyword;

import java.util.List;

public class KeywordResponse {

    @SerializedName("id")
    public int id;

    @SerializedName("keywords")
    public List<Keyword> keywords;
}
