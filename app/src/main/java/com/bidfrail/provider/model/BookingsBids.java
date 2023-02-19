package com.bidfrail.provider.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingsBids implements Parcelable {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("order_id")
    @Expose
    private int orderId;

    @SerializedName("provider_id")
    @Expose
    private int providerId;

    @SerializedName("bid_amount")
    @Expose
    private int bidAmount;

    @SerializedName("final_amount")
    @Expose
    private int finalAmount;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("communication_status")
    @Expose
    private int communicationStatus;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public BookingsBids() {
    }

    public BookingsBids(int id, int orderId, int providerId, int bidAmount, int finalAmount, String message, int communicationStatus, String createdAt, String updatedAt) {
        this.id = id;
        this.orderId = orderId;
        this.providerId = providerId;
        this.bidAmount = bidAmount;
        this.finalAmount = finalAmount;
        this.message = message;
        this.communicationStatus = communicationStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public int getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(int bidAmount) {
        this.bidAmount = bidAmount;
    }

    public int getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(int finalAmount) {
        this.finalAmount = finalAmount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCommunicationStatus() {
        return communicationStatus;
    }

    public void setCommunicationStatus(int communicationStatus) {
        this.communicationStatus = communicationStatus;
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
        dest.writeInt(this.orderId);
        dest.writeInt(this.providerId);
        dest.writeInt(this.bidAmount);
        dest.writeInt(this.finalAmount);
        dest.writeString(this.message);
        dest.writeInt(this.communicationStatus);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.orderId = source.readInt();
        this.providerId = source.readInt();
        this.bidAmount = source.readInt();
        this.finalAmount = source.readInt();
        this.message = source.readString();
        this.communicationStatus = source.readInt();
        this.createdAt = source.readString();
        this.updatedAt = source.readString();
    }

    protected BookingsBids(Parcel in) {
        this.id = in.readInt();
        this.orderId = in.readInt();
        this.providerId = in.readInt();
        this.bidAmount = in.readInt();
        this.finalAmount = in.readInt();
        this.message = in.readString();
        this.communicationStatus = in.readInt();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
    }

    public static final Creator<BookingsBids> CREATOR = new Creator<BookingsBids>() {
        @Override
        public BookingsBids createFromParcel(Parcel source) {
            return new BookingsBids(source);
        }

        @Override
        public BookingsBids[] newArray(int size) {
            return new BookingsBids[size];
        }
    };
}
