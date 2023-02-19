package com.bidfrail.provider.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category implements Parcelable {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("category_is")
    @Expose
    private String categoryIs;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("poster")
    @Expose
    private String poster;

    @SerializedName("category_type")
    @Expose
    private String categoryType;

    @SerializedName("is_active")
    @Expose
    private String isActive;

    @SerializedName("is_trending")
    @Expose
    private int isTrending;

    @SerializedName("is_new")
    @Expose
    private int isNew;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Category() {
    }

    public Category(int id, String categoryIs, String name, String poster, String categoryType, String isActive, int isTrending, int isNew, String createdAt, String updatedAt) {
        this.id = id;
        this.categoryIs = categoryIs;
        this.name = name;
        this.poster = poster;
        this.categoryType = categoryType;
        this.isActive = isActive;
        this.isTrending = isTrending;
        this.isNew = isNew;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryIs() {
        return categoryIs;
    }

    public void setCategoryIs(String categoryIs) {
        this.categoryIs = categoryIs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public int getIsTrending() {
        return isTrending;
    }

    public void setIsTrending(int isTrending) {
        this.isTrending = isTrending;
    }

    public int getIsNew() {
        return isNew;
    }

    public void setIsNew(int isNew) {
        this.isNew = isNew;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.categoryIs);
        dest.writeString(this.name);
        dest.writeString(this.poster);
        dest.writeString(this.categoryType);
        dest.writeString(this.isActive);
        dest.writeInt(this.isTrending);
        dest.writeInt(this.isNew);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.categoryIs = source.readString();
        this.name = source.readString();
        this.poster = source.readString();
        this.categoryType = source.readString();
        this.isActive = source.readString();
        this.isTrending = source.readInt();
        this.isNew = source.readInt();
        this.createdAt = source.readString();
        this.updatedAt = source.readString();
    }

    protected Category(Parcel in) {
        this.id = in.readInt();
        this.categoryIs = in.readString();
        this.name = in.readString();
        this.poster = in.readString();
        this.categoryType = in.readString();
        this.isActive = in.readString();
        this.isTrending = in.readInt();
        this.isNew = in.readInt();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel source) {
            return new Category(source);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
}
