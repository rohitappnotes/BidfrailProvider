package com.bidfrail.provider.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubCategory implements Parcelable {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("category_id")
    @Expose
    private int categoryId;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("gif")
    @Expose
    private String gif;

    @SerializedName("poster")
    @Expose
    private String poster;

    @SerializedName("convenience_fee")
    @Expose
    private int convenienceFee;

    @SerializedName("tax_and_charges")
    @Expose
    private int taxAndCharges;

    @SerializedName("highlight_tag")
    @Expose
    private String highlightTag;

    @SerializedName("highlight_tag_color")
    @Expose
    private String highlightTagColor;

    @SerializedName("is_trending")
    @Expose
    private int isTrending;

    @SerializedName("is_active")
    @Expose
    private String isActive;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public SubCategory() {
    }

    public SubCategory(int id, int categoryId, String name, String gif, String poster, int convenienceFee, int taxAndCharges, String highlightTag, String highlightTagColor, int isTrending, String isActive, String createdAt, String updatedAt) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.gif = gif;
        this.poster = poster;
        this.convenienceFee = convenienceFee;
        this.taxAndCharges = taxAndCharges;
        this.highlightTag = highlightTag;
        this.highlightTagColor = highlightTagColor;
        this.isTrending = isTrending;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGif() {
        return gif;
    }

    public void setGif(String gif) {
        this.gif = gif;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public int getConvenienceFee() {
        return convenienceFee;
    }

    public void setConvenienceFee(int convenienceFee) {
        this.convenienceFee = convenienceFee;
    }

    public int getTaxAndCharges() {
        return taxAndCharges;
    }

    public void setTaxAndCharges(int taxAndCharges) {
        this.taxAndCharges = taxAndCharges;
    }

    public String getHighlightTag() {
        return highlightTag;
    }

    public void setHighlightTag(String highlightTag) {
        this.highlightTag = highlightTag;
    }

    public String getHighlightTagColor() {
        return highlightTagColor;
    }

    public void setHighlightTagColor(String highlightTagColor) {
        this.highlightTagColor = highlightTagColor;
    }

    public int getIsTrending() {
        return isTrending;
    }

    public void setIsTrending(int isTrending) {
        this.isTrending = isTrending;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.categoryId);
        dest.writeString(this.name);
        dest.writeString(this.gif);
        dest.writeString(this.poster);
        dest.writeInt(this.convenienceFee);
        dest.writeInt(this.taxAndCharges);
        dest.writeString(this.highlightTag);
        dest.writeString(this.highlightTagColor);
        dest.writeInt(this.isTrending);
        dest.writeString(this.isActive);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.categoryId = source.readInt();
        this.name = source.readString();
        this.gif = source.readString();
        this.poster = source.readString();
        this.convenienceFee = source.readInt();
        this.taxAndCharges = source.readInt();
        this.highlightTag = source.readString();
        this.highlightTagColor = source.readString();
        this.isTrending = source.readInt();
        this.isActive = source.readString();
        this.createdAt = source.readString();
        this.updatedAt = source.readString();
    }

    protected SubCategory(Parcel in) {
        this.id = in.readInt();
        this.categoryId = in.readInt();
        this.name = in.readString();
        this.gif = in.readString();
        this.poster = in.readString();
        this.convenienceFee = in.readInt();
        this.taxAndCharges = in.readInt();
        this.highlightTag = in.readString();
        this.highlightTagColor = in.readString();
        this.isTrending = in.readInt();
        this.isActive = in.readString();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
    }

    public static final Creator<SubCategory> CREATOR = new Creator<SubCategory>() {
        @Override
        public SubCategory createFromParcel(Parcel source) {
            return new SubCategory(source);
        }

        @Override
        public SubCategory[] newArray(int size) {
            return new SubCategory[size];
        }
    };
}
