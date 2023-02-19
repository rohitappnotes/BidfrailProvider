package com.bidfrail.provider.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Provider implements Parcelable {

    @SerializedName("id")
    @Expose
    private int providerId;

    @SerializedName("profile_picture")
    @Expose
    private String profilePicture;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("latitude")
    @Expose
    private String latitude;

    @SerializedName("longitude")
    @Expose
    private String longitude;

    @SerializedName("lat_lon_address")
    @Expose
    private String latLonAddress;

    @SerializedName("category_id")
    @Expose
    private int categoryId;

    @SerializedName("sub_category_id")
    @Expose
    private String subCategoryId;

    @SerializedName("about_you")
    @Expose
    private String aboutYou;

    @SerializedName("gallery")
    @Expose
    private String gallery;

    @SerializedName("aadhaar_card_front")
    @Expose
    private String aadhaarCardFront;

    @SerializedName("aadhaar_card_back")
    @Expose
    private String aadhaarCardBack;

    @SerializedName("pan_card")
    @Expose
    private String panCard;

    @SerializedName("is_active")
    @Expose
    private int isActive;

    @SerializedName("is_online")
    @Expose
    private int isOnline;

    @SerializedName("credit")
    @Expose
    private int credit;

    @SerializedName("fcm_token")
    @Expose
    private String fcmToken;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @SerializedName("category_name")
    @Expose
    private String categoryName;

    @SerializedName("sub_category_name")
    @Expose
    private String subCategoryName;

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatLonAddress() {
        return latLonAddress;
    }

    public void setLatLonAddress(String latLonAddress) {
        this.latLonAddress = latLonAddress;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getAboutYou() {
        return aboutYou;
    }

    public void setAboutYou(String aboutYou) {
        this.aboutYou = aboutYou;
    }

    public String getGallery() {
        return gallery;
    }

    public void setGallery(String gallery) {
        this.gallery = gallery;
    }

    public String getAadhaarCardFront() {
        return aadhaarCardFront;
    }

    public void setAadhaarCardFront(String aadhaarCardFront) {
        this.aadhaarCardFront = aadhaarCardFront;
    }

    public String getAadhaarCardBack() {
        return aadhaarCardBack;
    }

    public void setAadhaarCardBack(String aadhaarCardBack) {
        this.aadhaarCardBack = aadhaarCardBack;
    }

    public String getPanCard() {
        return panCard;
    }

    public void setPanCard(String panCard) {
        this.panCard = panCard;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public int getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(int isOnline) {
        this.isOnline = isOnline;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.providerId);
        dest.writeString(this.profilePicture);
        dest.writeString(this.name);
        dest.writeString(this.phoneNumber);
        dest.writeString(this.email);
        dest.writeString(this.password);
        dest.writeString(this.latitude);
        dest.writeString(this.longitude);
        dest.writeString(this.latLonAddress);
        dest.writeInt(this.categoryId);
        dest.writeString(this.subCategoryId);
        dest.writeString(this.aboutYou);
        dest.writeString(this.gallery);
        dest.writeString(this.aadhaarCardFront);
        dest.writeString(this.aadhaarCardBack);
        dest.writeString(this.panCard);
        dest.writeInt(this.isActive);
        dest.writeInt(this.isOnline);
        dest.writeInt(this.credit);
        dest.writeString(this.fcmToken);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
        dest.writeString(this.categoryName);
        dest.writeString(this.subCategoryName);
    }

    public void readFromParcel(Parcel source) {
        this.providerId = source.readInt();
        this.profilePicture = source.readString();
        this.name = source.readString();
        this.phoneNumber = source.readString();
        this.email = source.readString();
        this.password = source.readString();
        this.latitude = source.readString();
        this.longitude = source.readString();
        this.latLonAddress = source.readString();
        this.categoryId = source.readInt();
        this.subCategoryId = source.readString();
        this.aboutYou = source.readString();
        this.gallery = source.readString();
        this.aadhaarCardFront = source.readString();
        this.aadhaarCardBack = source.readString();
        this.panCard = source.readString();
        this.isActive = source.readInt();
        this.isOnline = source.readInt();
        this.credit = source.readInt();
        this.fcmToken = source.readString();
        this.createdAt = source.readString();
        this.updatedAt = source.readString();
        this.categoryName = source.readString();
        this.subCategoryName = source.readString();
    }

    public Provider() {
    }

    protected Provider(Parcel in) {
        this.providerId = in.readInt();
        this.profilePicture = in.readString();
        this.name = in.readString();
        this.phoneNumber = in.readString();
        this.email = in.readString();
        this.password = in.readString();
        this.latitude = in.readString();
        this.longitude = in.readString();
        this.latLonAddress = in.readString();
        this.categoryId = in.readInt();
        this.subCategoryId = in.readString();
        this.aboutYou = in.readString();
        this.gallery = in.readString();
        this.aadhaarCardFront = in.readString();
        this.aadhaarCardBack = in.readString();
        this.panCard = in.readString();
        this.isActive = in.readInt();
        this.isOnline = in.readInt();
        this.credit = in.readInt();
        this.fcmToken = in.readString();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
        this.categoryName = in.readString();
        this.subCategoryName = in.readString();
    }

    public static final Creator<Provider> CREATOR = new Creator<Provider>() {
        @Override
        public Provider createFromParcel(Parcel source) {
            return new Provider(source);
        }

        @Override
        public Provider[] newArray(int size) {
            return new Provider[size];
        }
    };
}