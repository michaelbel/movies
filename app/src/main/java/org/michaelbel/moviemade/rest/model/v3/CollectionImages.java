package org.michaelbel.moviemade.rest.model.v3;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.michaelbel.tmdb.TmdbObject;

import java.util.ArrayList;
import java.util.List;

public class CollectionImages extends TmdbObject implements Parcelable {

    @SerializedName("id")
    public int id;

    @SerializedName("posters")
    public List<Backdrop> posters;

    @SerializedName("backdrops")
    public List<Backdrop> backdrops;

    protected CollectionImages(Parcel in) {
        id = in.readInt();
        if (in.readByte() == 0x01) {
            posters = new ArrayList<Backdrop>();
            in.readList(posters, Backdrop.class.getClassLoader());
        } else {
            posters = null;
        }
        if (in.readByte() == 0x01) {
            backdrops = new ArrayList<Backdrop>();
            in.readList(backdrops, Backdrop.class.getClassLoader());
        } else {
            backdrops = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        if (posters == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(posters);
        }
        if (backdrops == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(backdrops);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<CollectionImages> CREATOR = new Parcelable.Creator<CollectionImages>() {
        @Override
        public CollectionImages createFromParcel(Parcel in) {
            return new CollectionImages(in);
        }

        @Override
        public CollectionImages[] newArray(int size) {
            return new CollectionImages[size];
        }
    };
}