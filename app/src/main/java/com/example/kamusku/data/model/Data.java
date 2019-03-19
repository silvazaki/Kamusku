package com.example.kamusku.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by User on 1/18/2019.
 */

public class Data implements Parcelable {
    private int id;
    private String kata;
    private String terjemahan;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKata() {
        return kata;
    }

    public void setKata(String kata) {
        this.kata = kata;
    }

    public String getTerjemahan() {
        return terjemahan;
    }

    public void setTerjemahan(String terjemahan) {
        this.terjemahan = terjemahan;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.kata);
        dest.writeString(this.terjemahan);
    }

    public Data() {
    }

    public Data(String kata, String terjemahan) {
        this.kata = kata;
        this.terjemahan = terjemahan;
    }

    protected Data(Parcel in) {
        this.id = in.readInt();
        this.kata = in.readString();
        this.terjemahan = in.readString();
    }

    public static final Parcelable.Creator<Data> CREATOR = new Parcelable.Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel source) {
            return new Data(source);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };
}
