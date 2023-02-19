package com.bidfrail.provider.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cart implements Parcelable {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("order_id")
    @Expose
    private String orderId;

    @SerializedName("sub_service_id")
    @Expose
    private int subServiceId;

    @SerializedName("quantity")
    @Expose
    private int quantity;

    @SerializedName("total_price")
    @Expose
    private int totalPrice;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @SerializedName("sub_service")
    @Expose
    private SubService subService;

    public Cart() {
    }

    public Cart(int id, String orderId, int subServiceId, int quantity, int totalPrice, String createdAt, String updatedAt, SubService subService) {
        this.id = id;
        this.orderId = orderId;
        this.subServiceId = subServiceId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.subService = subService;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getSubServiceId() {
        return subServiceId;
    }

    public void setSubServiceId(int subServiceId) {
        this.subServiceId = subServiceId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
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

    public SubService getSubService() {
        return subService;
    }

    public void setSubService(SubService subService) {
        this.subService = subService;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.orderId);
        dest.writeInt(this.subServiceId);
        dest.writeInt(this.quantity);
        dest.writeInt(this.totalPrice);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
        dest.writeParcelable(this.subService, flags);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.orderId = source.readString();
        this.subServiceId = source.readInt();
        this.quantity = source.readInt();
        this.totalPrice = source.readInt();
        this.createdAt = source.readString();
        this.updatedAt = source.readString();
        this.subService = source.readParcelable(SubService.class.getClassLoader());
    }

    protected Cart(Parcel in) {
        this.id = in.readInt();
        this.orderId = in.readString();
        this.subServiceId = in.readInt();
        this.quantity = in.readInt();
        this.totalPrice = in.readInt();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
        this.subService = in.readParcelable(SubService.class.getClassLoader());
    }

    public static final Parcelable.Creator<Cart> CREATOR = new Parcelable.Creator<Cart>() {
        @Override
        public Cart createFromParcel(Parcel source) {
            return new Cart(source);
        }

        @Override
        public Cart[] newArray(int size) {
            return new Cart[size];
        }
    };
}
