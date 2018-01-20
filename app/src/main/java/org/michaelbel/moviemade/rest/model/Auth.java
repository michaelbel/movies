package org.michaelbel.moviemade.rest.model;

import com.google.gson.annotations.SerializedName;

public class Auth {

    public class CreatedRequest {

        @SerializedName("success")
        public boolean success;

        @SerializedName("expires_at")
        public String expires_at;

        @SerializedName("request_token")
        public String request_token;
    }

    public class ValidatedRequest {

        @SerializedName("success")
        public boolean success;

        @SerializedName("request_token")
        public String request_token;
    }

    public class Session {

        @SerializedName("success")
        public boolean success;

        @SerializedName("session_id")
        public String sessionId;
    }

    public class GuestSession {

        @SerializedName("success")
        public boolean success;

        @SerializedName("expires_at")
        public String expires_at;

        @SerializedName("quest_session_id")
        public String questSessionId;
    }
}