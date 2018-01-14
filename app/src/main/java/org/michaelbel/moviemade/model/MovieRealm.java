package org.michaelbel.moviemade.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

// todo update parcelable
public class MovieRealm extends RealmObject implements Parcelable {

    public int year;

    public boolean favorite;
    public boolean watching;

    //public RealmList<Genre> genres2;

    public int id;
    public String title;
    public String originalTitle;
    public String originalLanguage;
    public boolean adult;
    public String posterPath;
    public String backdropPath;
    public String releaseDate;
    public String overview;
    public String addedDate;
    public String budget;
    public String revenue;
    public String status;
    public String tagline;
    public String imdbId;
    public String homepage;
    public double popularity;
    public boolean video;
    public String runtime;
    public float voteAverage;
    public int voteCount;
    public String genres;
    public String companies;
    public String countries;

    public MovieRealm() {
    }

    @Deprecated
    public boolean isFavorite() {
        return favorite;
    }

    @Deprecated
    public boolean isWatching() {
        return watching;
    }

    protected MovieRealm(Parcel in) {
        favorite = in.readByte() != 0x00;
        watching = in.readByte() != 0x00;
        id = in.readInt();
        title = in.readString();
        originalTitle = in.readString();
        originalLanguage = in.readString();
        adult = in.readByte() != 0x00;
        posterPath = in.readString();
        backdropPath = in.readString();
        releaseDate = in.readString();
        overview = in.readString();
        addedDate = in.readString();
        budget = in.readString();
        revenue = in.readString();
        status = in.readString();
        tagline = in.readString();
        imdbId = in.readString();
        homepage = in.readString();
        popularity = in.readDouble();
        video = in.readByte() != 0x00;
        runtime = in.readString();
        voteAverage = in.readFloat();
        voteCount = in.readInt();
        genres = in.readString();
        companies = in.readString();
        countries = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (favorite ? 0x01 : 0x00));
        dest.writeByte((byte) (watching ? 0x01 : 0x00));
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(originalTitle);
        dest.writeString(originalLanguage);
        dest.writeByte((byte) (adult ? 0x01 : 0x00));
        dest.writeString(posterPath);
        dest.writeString(backdropPath);
        dest.writeString(releaseDate);
        dest.writeString(overview);
        dest.writeString(addedDate);
        dest.writeString(budget);
        dest.writeString(revenue);
        dest.writeString(status);
        dest.writeString(tagline);
        dest.writeString(imdbId);
        dest.writeString(homepage);
        dest.writeDouble(popularity);
        dest.writeByte((byte) (video ? 0x01 : 0x00));
        dest.writeString(runtime);
        dest.writeFloat(voteAverage);
        dest.writeInt(voteCount);
        dest.writeString(genres);
        dest.writeString(companies);
        dest.writeString(countries);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<MovieRealm> CREATOR = new Parcelable.Creator<MovieRealm>() {
        @Override
        public MovieRealm createFromParcel(Parcel in) {
            return new MovieRealm(in);
        }

        @Override
        public MovieRealm[] newArray(int size) {
            return new MovieRealm[size];
        }
    };
}