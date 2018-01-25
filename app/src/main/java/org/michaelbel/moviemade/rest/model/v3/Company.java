package org.michaelbel.moviemade.rest.model.v3;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.michaelbel.moviemade.rest.TmdbObject;

public class Company extends TmdbObject implements Parcelable {

    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;

    @SerializedName("description")
    public String description;

    @SerializedName("headquarters")
    public String headquarters;

    @SerializedName("logo_path")
    public String logoPath;

    @SerializedName("parent_company")
    public String parentCompany;

    public Company() {}

    protected Company(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        headquarters = in.readString();
        logoPath = in.readString();
        parentCompany = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(headquarters);
        dest.writeString(logoPath);
        dest.writeString(parentCompany);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Company> CREATOR = new Parcelable.Creator<Company>() {
        @Override
        public Company createFromParcel(Parcel in) {
            return new Company(in);
        }

        @Override
        public Company[] newArray(int size) {
            return new Company[size];
        }
    };
}