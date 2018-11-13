package org.michaelbel.moviemade.rest.model.v3;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.michaelbel.tmdb.TmdbObject;
import org.michaelbel.tmdb.v3.json.Movie;

import java.util.ArrayList;
import java.util.List;

public class People extends TmdbObject implements Parcelable {

    @SerializedName("profile_path")
    public String profilePath;

    @SerializedName("adult")
    public boolean adult;

    @SerializedName("id")
    public int id;

    @SerializedName("popularity")
    public double popularity;

    @SerializedName("name")
    public String name;

    @SerializedName("known_for")
    public List<Movie> movies;

    public People() {
    }

    protected People(Parcel in) {
        profilePath = in.readString();
        adult = in.readByte() != 0x00;
        id = in.readInt();
        popularity = in.readDouble();
        name = in.readString();
        if (in.readByte() == 0x01) {
            movies = new ArrayList<Movie>();
            in.readList(movies, Movie.class.getClassLoader());
        } else {
            movies = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(profilePath);
        dest.writeByte((byte) (adult ? 0x01 : 0x00));
        dest.writeInt(id);
        dest.writeDouble(popularity);
        dest.writeString(name);
        if (movies == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(movies);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<People> CREATOR = new Parcelable.Creator<People>() {
        @Override
        public People createFromParcel(Parcel in) {
            return new People(in);
        }

        @Override
        public People[] newArray(int size) {
            return new People[size];
        }
    };
}