package com.bidfrail.provider.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingBill implements Parcelable {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("order_id")
    @Expose
    private int orderId;

    @SerializedName("order_type")
    @Expose
    private String orderType;

    @SerializedName("extra_charge")
    @Expose
    private int extraCharge;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("total_amount")
    @Expose
    private int totalAmount;

    @SerializedName("coupon_id")
    @Expose
    private int couponId;

    @SerializedName("coupon_amount")
    @Expose
    private int couponAmount;

    @SerializedName("pay_amount")
    @Expose
    private int payAmount;

    @SerializedName("payment_mode")
    @Expose
    private String paymentMode;

    @SerializedName("payment_status")
    @Expose
    private String paymentStatus;

    @SerializedName("payment_id")
    @Expose
    private String paymentId;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public BookingBill() {
    }

    public BookingBill(int id, int orderId, String orderType, int extraCharge, String description, int totalAmount, int couponId, int couponAmount, int payAmount, String paymentMode, String paymentStatus, String paymentId, String createdAt, String updatedAt) {
        this.id = id;
        this.orderId = orderId;
        this.orderType = orderType;
        this.extraCharge = extraCharge;
        this.description = description;
        this.totalAmount = totalAmount;
        this.couponId = couponId;
        this.couponAmount = couponAmount;
        this.payAmount = payAmount;
        this.paymentMode = paymentMode;
        this.paymentStatus = paymentStatus;
        this.paymentId = paymentId;
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

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public int getExtraCharge() {
        return extraCharge;
    }

    public void setExtraCharge(int extraCharge) {
        this.extraCharge = extraCharge;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public int getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(int couponAmount) {
        this.couponAmount = couponAmount;
    }

    public int getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(int payAmount) {
        this.payAmount = payAmount;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
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
        dest.writeString(this.orderType);
        dest.writeInt(this.extraCharge);
        dest.writeString(this.description);
        dest.writeInt(this.totalAmount);
        dest.writeInt(this.couponId);
        dest.writeInt(this.couponAmount);
        dest.writeInt(this.payAmount);
        dest.writeString(this.paymentMode);
        dest.writeString(this.paymentStatus);
        dest.writeString(this.paymentId);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.orderId = source.readInt();
        this.orderType = source.readString();
        this.extraCharge = source.readInt();
        this.description = source.readString();
        this.totalAmount = source.readInt();
        this.couponId = source.readInt();
        this.couponAmount = source.readInt();
        this.payAmount = source.readInt();
        this.paymentMode = source.readString();
        this.paymentStatus = source.readString();
        this.paymentId = source.readString();
        this.createdAt = source.readString();
        this.updatedAt = source.readString();
    }

    protected BookingBill(Parcel in) {
        this.id = in.readInt();
        this.orderId = in.readInt();
        this.orderType = in.readString();
        this.extraCharge = in.readInt();
        this.description = in.readString();
        this.totalAmount = in.readInt();
        this.couponId = in.readInt();
        this.couponAmount = in.readInt();
        this.payAmount = in.readInt();
        this.paymentMode = in.readString();
        this.paymentStatus = in.readString();
        this.paymentId = in.readString();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
    }

    public static final Creator<BookingBill> CREATOR = new Creator<BookingBill>() {
        @Override
        public BookingBill createFromParcel(Parcel source) {
            return new BookingBill(source);
        }

        @Override
        public BookingBill[] newArray(int size) {
            return new BookingBill[size];
        }
    };
}
