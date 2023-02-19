package com.bidfrail.provider.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingsFinalOffer implements Parcelable {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("order_id")
    @Expose
    private int orderId;

    @SerializedName("user_id")
    @Expose
    private int userId;

    @SerializedName("provider_id")
    @Expose
    private int providerId;

    @SerializedName("amount")
    @Expose
    private int amount;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public BookingsFinalOffer() {
    }

    public BookingsFinalOffer(int id, int orderId, int userId, int providerId, int amount, String message, String status, String createdAt, String updatedAt) {
        this.id = id;
        this.orderId = orderId;
        this.userId = userId;
        this.providerId = providerId;
        this.amount = amount;
        this.message = message;
        this.status = status;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        dest.writeInt(this.userId);
        dest.writeInt(this.providerId);
        dest.writeInt(this.amount);
        dest.writeString(this.message);
        dest.writeString(this.status);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.orderId = source.readInt();
        this.userId = source.readInt();
        this.providerId = source.readInt();
        this.amount = source.readInt();
        this.message = source.readString();
        this.status = source.readString();
        this.createdAt = source.readString();
        this.updatedAt = source.readString();
    }

    protected BookingsFinalOffer(Parcel in) {
        this.id = in.readInt();
        this.orderId = in.readInt();
        this.userId = in.readInt();
        this.providerId = in.readInt();
        this.amount = in.readInt();
        this.message = in.readString();
        this.status = in.readString();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
    }

    public static final Parcelable.Creator<BookingsFinalOffer> CREATOR = new Parcelable.Creator<BookingsFinalOffer>() {
        @Override
        public BookingsFinalOffer createFromParcel(Parcel source) {
            return new BookingsFinalOffer(source);
        }

        @Override
        public BookingsFinalOffer[] newArray(int size) {
            return new BookingsFinalOffer[size];
        }
    };
}
