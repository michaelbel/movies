package org.michaelbel.moviemade.rest.model.v3;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.michaelbel.tmdb.TmdbObject;

public class Backdrop extends TmdbObject implements Parcelable {

    @SerializedName("file_path")
    public String filePath;

    @SerializedName("height")
    public int height;

    @SerializedName("width")
    public int width;

    @SerializedName("iso_639_1")
    public String code;

    @SerializedName("vote_average")
    public float voteAverage;

    @SerializedName("vote_count")
    public int voteCount;

    @SerializedName("aspect_ratio")
    public float aspectRatio;

    protected Backdrop(Parcel in) {
        filePath = in.readString();
        height = in.readInt();
        width = in.readInt();
        code = in.readString();
        voteAverage = in.readFloat();
        voteCount = in.readInt();
        aspectRatio = in.readFloat();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(filePath);
        dest.writeInt(height);
        dest.writeInt(width);
        dest.writeString(code);
        dest.writeFloat(voteAverage);
        dest.writeInt(voteCount);
        dest.writeFloat(aspectRatio);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Backdrop> CREATOR = new Parcelable.Creator<Backdrop>() {
        @Override
        public Backdrop createFromParcel(Parcel in) {
            return new Backdrop(in);
        }

        @Override
        public Backdrop[] newArray(int size) {
            return new Backdrop[size];
        }
    };
}