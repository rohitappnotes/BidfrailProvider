package com.bidfrail.provider.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User implements Parcelable {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("os")
    @Expose
    private String os;

    @SerializedName("picture")
    @Expose
    private String picture;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("flat_number")
    @Expose
    private String flatNumber;

    @SerializedName("street_name")
    @Expose
    private String streetName;

    @SerializedName("society_name")
    @Expose
    private String societyName;

    @SerializedName("locality")
    @Expose
    private String locality;

    @SerializedName("referral_code")
    @Expose
    private String referralCode;

    @SerializedName("reference_by")
    @Expose
    private String referenceBy;

    @SerializedName("fcm_token")
    @Expose
    private String fcmToken;

    @SerializedName("is_active")
    @Expose
    private int isActive;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @SerializedName("amount")
    @Expose
    private int amount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFlatNumber() {
        return flatNumber;
    }

    public void setFlatNumber(String flatNumber) {
        this.flatNumber = flatNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getSocietyName() {
        return societyName;
    }

    public void setSocietyName(String societyName) {
        this.societyName = societyName;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public String getReferenceBy() {
        return referenceBy;
    }

    public void setReferenceBy(String referenceBy) {
        this.referenceBy = referenceBy;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.os);
        dest.writeString(this.picture);
        dest.writeString(this.name);
        dest.writeString(this.phoneNumber);
        dest.writeString(this.email);
        dest.writeString(this.flatNumber);
        dest.writeString(this.streetName);
        dest.writeString(this.societyName);
        dest.writeString(this.locality);
        dest.writeString(this.referralCode);
        dest.writeString(this.referenceBy);
        dest.writeString(this.fcmToken);
        dest.writeInt(this.isActive);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
        dest.writeInt(this.amount);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.os = source.readString();
        this.picture = source.readString();
        this.name = source.readString();
        this.phoneNumber = source.readString();
        this.email = source.readString();
        this.flatNumber = source.readString();
        this.streetName = source.readString();
        this.societyName = source.readString();
        this.locality = source.readString();
        this.referralCode = source.readString();
        this.referenceBy = source.readString();
        this.fcmToken = source.readString();
        this.isActive = source.readInt();
        this.createdAt = source.readString();
        this.updatedAt = source.readString();
        this.amount = source.readInt();
    }

    public User() {
    }

    protected User(Parcel in) {
        this.id = in.readInt();
        this.os = in.readString();
        this.picture = in.readString();
        this.name = in.readString();
        this.phoneNumber = in.readString();
        this.email = in.readString();
        this.flatNumber = in.readString();
        this.streetName = in.readString();
        this.societyName = in.readString();
        this.locality = in.readString();
        this.referralCode = in.readString();
        this.referenceBy = in.readString();
        this.fcmToken = in.readString();
        this.isActive = in.readInt();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
        this.amount = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}