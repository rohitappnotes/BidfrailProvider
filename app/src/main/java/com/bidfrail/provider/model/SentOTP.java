package com.bidfrail.provider.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SentOTP implements Parcelable {

    @SerializedName("sent_otp")
    @Expose
    private String sentOTP;

    public String getSentOTP() {
        return sentOTP;
    }

    public void setSentOTP(String sentOTP) {
        this.sentOTP = sentOTP;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.sentOTP);
    }

    public void readFromParcel(Parcel source) {
        this.sentOTP = source.readString();
    }

    public SentOTP() {
    }

    protected SentOTP(Parcel in) {
        this.sentOTP = in.readString();
    }

    public static final Creator<SentOTP> CREATOR = new Creator<SentOTP>() {
        @Override
        public SentOTP createFromParcel(Parcel source) {
            return new SentOTP(source);
        }

        @Override
        public SentOTP[] newArray(int size) {
            return new SentOTP[size];
        }
    };
}