package org.michaelbel.application.rest.model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("all")
public class Account {

    @SerializedName("avatar")
    public Avatar avatar;

    @SerializedName("id")
    public int id;

    @SerializedName("iso_639_1")
    public String iso_639_1;

    @SerializedName("iso_3166_1")
    public String iso_3166_1;

    @SerializedName("name")
    public String name;

    @SerializedName("include_adult")
    public boolean includeAdult;

    @SerializedName("username")
    public String username;

    public class Avatar {

        public class GAvatar {

            @SerializedName("hash")
            public String hash;
        }
    }
}