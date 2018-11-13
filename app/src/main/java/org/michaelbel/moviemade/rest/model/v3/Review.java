package org.michaelbel.moviemade.rest.model.v3;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.michaelbel.tmdb.TmdbObject;

public class Review extends TmdbObject implements Parcelable {

    @SerializedName("id")
    public String id;

    @SerializedName("author")
    public String author;

    @SerializedName("content")
    public String content;

    @SerializedName("iso_639_1")
    public String lang;

    @SerializedName("media_id")
    public int mediaId;

    @SerializedName("media_title")
    public String mediaTitle;

    @SerializedName("media_type")
    public String mediaType;

    @SerializedName("url")
    public String url;

    public Review() {
    }

    protected Review(Parcel in) {
        id = in.readString();
        author = in.readString();
        content = in.readString();
        lang = in.readString();
        mediaId = in.readInt();
        mediaTitle = in.readString();
        mediaType = in.readString();
        url = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(author);
        dest.writeString(content);
        dest.writeString(lang);
        dest.writeInt(mediaId);
        dest.writeString(mediaTitle);
        dest.writeString(mediaType);
        dest.writeString(url);
    }

    public static final Parcelable.Creator<Review> CREATOR = new Parcelable.Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };
}