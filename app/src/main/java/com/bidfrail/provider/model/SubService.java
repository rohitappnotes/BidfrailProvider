package com.bidfrail.provider.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubService implements Parcelable {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("service_id")
    @Expose
    private int serviceId;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("main_price")
    @Expose
    private int mainPrice;

    @SerializedName("offer_price")
    @Expose
    private int offerPrice;

    @SerializedName("highlight_tag")
    @Expose
    private String highlightTag;

    @SerializedName("highlight_tag_color")
    @Expose
    private String highlightTagColor;

    @SerializedName("is_active")
    @Expose
    private int isActive;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @SerializedName("get_bids_credit_point")
    @Expose
    private int getBidsCreditPoint;

    @SerializedName("book_now_credit_point")
    @Expose
    private int bookNowCreditPoint;

    public SubService() {
    }

    public SubService(int id, int serviceId, String name, String description, String image, int mainPrice, int offerPrice, String highlightTag, String highlightTagColor, int isActive, String createdAt, String updatedAt, int getBidsCreditPoint, int bookNowCreditPoint) {
        this.id = id;
        this.serviceId = serviceId;
        this.name = name;
        this.description = description;
        this.image = image;
        this.mainPrice = mainPrice;
        this.offerPrice = offerPrice;
        this.highlightTag = highlightTag;
        this.highlightTagColor = highlightTagColor;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.getBidsCreditPoint = getBidsCreditPoint;
        this.bookNowCreditPoint = bookNowCreditPoint;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getMainPrice() {
        return mainPrice;
    }

    public void setMainPrice(int mainPrice) {
        this.mainPrice = mainPrice;
    }

    public int getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(int offerPrice) {
        this.offerPrice = offerPrice;
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

    public int getGetBidsCreditPoint() {
        return getBidsCreditPoint;
    }

    public void setGetBidsCreditPoint(int getBidsCreditPoint) {
        this.getBidsCreditPoint = getBidsCreditPoint;
    }

    public int getBookNowCreditPoint() {
        return bookNowCreditPoint;
    }

    public void setBookNowCreditPoint(int bookNowCreditPoint) {
        this.bookNowCreditPoint = bookNowCreditPoint;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.serviceId);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.image);
        dest.writeInt(this.mainPrice);
        dest.writeInt(this.offerPrice);
        dest.writeString(this.highlightTag);
        dest.writeString(this.highlightTagColor);
        dest.writeInt(this.isActive);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
        dest.writeInt(this.getBidsCreditPoint);
        dest.writeInt(this.bookNowCreditPoint);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.serviceId = source.readInt();
        this.name = source.readString();
        this.description = source.readString();
        this.image = source.readString();
        this.mainPrice = source.readInt();
        this.offerPrice = source.readInt();
        this.highlightTag = source.readString();
        this.highlightTagColor = source.readString();
        this.isActive = source.readInt();
        this.createdAt = source.readString();
        this.updatedAt = source.readString();
        this.getBidsCreditPoint = source.readInt();
        this.bookNowCreditPoint = source.readInt();
    }

    protected SubService(Parcel in) {
        this.id = in.readInt();
        this.serviceId = in.readInt();
        this.name = in.readString();
        this.description = in.readString();
        this.image = in.readString();
        this.mainPrice = in.readInt();
        this.offerPrice = in.readInt();
        this.highlightTag = in.readString();
        this.highlightTagColor = in.readString();
        this.isActive = in.readInt();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
        this.getBidsCreditPoint = in.readInt();
        this.bookNowCreditPoint = in.readInt();
    }

    public static final Parcelable.Creator<SubService> CREATOR = new Parcelable.Creator<SubService>() {
        @Override
        public SubService createFromParcel(Parcel source) {
            return new SubService(source);
        }

        @Override
        public SubService[] newArray(int size) {
            return new SubService[size];
        }
    };
}
