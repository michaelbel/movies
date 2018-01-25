package org.michaelbel.moviemade.rest.model.v3;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.michaelbel.moviemade.rest.TmdbObject;
import org.michaelbel.moviemade.rest.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class Collection extends TmdbObject implements Parcelable {

    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;

    @SerializedName("overview")
    public String overview;

    @SerializedName("poster_path")
    public String posterPath;

    @SerializedName("backdrop_path")
    public String backdropPath;

    @SerializedName("parts")
    public List<Movie> movies;

    public Collection() {}

    protected Collection(Parcel in) {
        id = in.readInt();
        name = in.readString();
        overview = in.readString();
        posterPath = in.readString();
        backdropPath = in.readString();
        if (in.readByte() == 0x01) {
            movies = new ArrayList<>();
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
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(overview);
        dest.writeString(posterPath);
        dest.writeString(backdropPath);
        if (movies == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(movies);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Collection> CREATOR = new Parcelable.Creator<Collection>() {
        @Override
        public Collection createFromParcel(Parcel in) {
            return new Collection(in);
        }

        @Override
        public Collection[] newArray(int size) {
            return new Collection[size];
        }
    };
}