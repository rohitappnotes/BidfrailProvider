package com.bidfrail.provider.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Service implements Parcelable {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("category_id")
    @Expose
    private int categoryId;

    @SerializedName("sub_category_id")
    @Expose
    private int subCategoryId;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("pdf")
    @Expose
    private String pdf;

    @SerializedName("is_active")
    @Expose
    private int isActive;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @SerializedName("sub_service")
    @Expose
    private ArrayList<SubService> subServiceArrayList;

    public Service() {
    }

    public Service(int id, int categoryId, int subCategoryId, String name, String image, String pdf, int isActive, String createdAt, String updatedAt, ArrayList<SubService> subServiceArrayList) {
        this.id = id;
        this.categoryId = categoryId;
        this.subCategoryId = subCategoryId;
        this.name = name;
        this.image = image;
        this.pdf = pdf;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.subServiceArrayList = subServiceArrayList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(int subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
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

    public ArrayList<SubService> getSubServiceArrayList() {
        return subServiceArrayList;
    }

    public void setSubServiceArrayList(ArrayList<SubService> subServiceArrayList) {
        this.subServiceArrayList = subServiceArrayList;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.categoryId);
        dest.writeInt(this.subCategoryId);
        dest.writeString(this.name);
        dest.writeString(this.image);
        dest.writeString(this.pdf);
        dest.writeInt(this.isActive);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
        dest.writeTypedList(this.subServiceArrayList);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.categoryId = source.readInt();
        this.subCategoryId = source.readInt();
        this.name = source.readString();
        this.image = source.readString();
        this.pdf = source.readString();
        this.isActive = source.readInt();
        this.createdAt = source.readString();
        this.updatedAt = source.readString();
        this.subServiceArrayList = source.createTypedArrayList(SubService.CREATOR);
    }

    protected Service(Parcel in) {
        this.id = in.readInt();
        this.categoryId = in.readInt();
        this.subCategoryId = in.readInt();
        this.name = in.readString();
        this.image = in.readString();
        this.pdf = in.readString();
        this.isActive = in.readInt();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
        this.subServiceArrayList = in.createTypedArrayList(SubService.CREATOR);
    }

    public static final Creator<Service> CREATOR = new Creator<Service>() {
        @Override
        public Service createFromParcel(Parcel source) {
            return new Service(source);
        }

        @Override
        public Service[] newArray(int size) {
            return new Service[size];
        }
    };
}
