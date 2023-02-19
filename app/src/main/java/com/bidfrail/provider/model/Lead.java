package com.bidfrail.provider.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Lead implements Parcelable {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("order_number")
    @Expose
    private String orderNumber;

    @SerializedName("order_type")
    @Expose
    private String orderType;

    @SerializedName("user_id")
    @Expose
    private int userId;

    @SerializedName("slot_date")
    @Expose
    private String slotDate;

    @SerializedName("slot_time")
    @Expose
    private String slotTime;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("latitude")
    @Expose
    private String latitude;

    @SerializedName("longitude")
    @Expose
    private String longitude;

    @SerializedName("lat_lon_address")
    @Expose
    private String latLonAddress;

    @SerializedName("flat_building_street")
    @Expose
    private String flatBuildingStreet;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("user_status")
    @Expose
    private String userStatus;

    @SerializedName("provider_status")
    @Expose
    private String providerStatus;

    @SerializedName("provider_id")
    @Expose
    private int providerId;

    @SerializedName("credit")
    @Expose
    private int credit;

    @SerializedName("expire_time")
    @Expose
    private int expireTime;

    @SerializedName("otp")
    @Expose
    private String otp;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @SerializedName("distance")
    @Expose
    private double distance;

    @SerializedName("seconds")
    @Expose
    private int seconds;

    @SerializedName("booking_bill")
    @Expose
    private BookingBill bookingBill;

    @SerializedName("service")
    @Expose
    private Service service;

    @SerializedName("category")
    @Expose
    private Category category;

    @SerializedName("subCategory")
    @Expose
    private SubCategory subCategory;

    @SerializedName("cart")
    @Expose
    private List<Cart> cart = null;

    @SerializedName("bookings_bids")
    @Expose
    private BookingsBids bookingsBids;

    @SerializedName("bookings_final_offer")
    @Expose
    private BookingsFinalOffer bookingsFinalOffer;

    @SerializedName("user")
    @Expose
    private User user;

    public Lead() {
    }

    public Lead(int id, String orderNumber, String orderType, int userId, String slotDate, String slotTime, String message, String latitude, String longitude, String latLonAddress, String flatBuildingStreet, String address, String userStatus, String providerStatus, int providerId, int credit, int expireTime, String otp, String createdAt, String updatedAt, double distance, int seconds, BookingBill bookingBill, Service service, Category category, SubCategory subCategory, List<Cart> cart, BookingsBids bookingsBids, BookingsFinalOffer bookingsFinalOffer, User user) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.orderType = orderType;
        this.userId = userId;
        this.slotDate = slotDate;
        this.slotTime = slotTime;
        this.message = message;
        this.latitude = latitude;
        this.longitude = longitude;
        this.latLonAddress = latLonAddress;
        this.flatBuildingStreet = flatBuildingStreet;
        this.address = address;
        this.userStatus = userStatus;
        this.providerStatus = providerStatus;
        this.providerId = providerId;
        this.credit = credit;
        this.expireTime = expireTime;
        this.otp = otp;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.distance = distance;
        this.seconds = seconds;
        this.bookingBill = bookingBill;
        this.service = service;
        this.category = category;
        this.subCategory = subCategory;
        this.cart = cart;
        this.bookingsBids = bookingsBids;
        this.bookingsFinalOffer = bookingsFinalOffer;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSlotDate() {
        return slotDate;
    }

    public void setSlotDate(String slotDate) {
        this.slotDate = slotDate;
    }

    public String getSlotTime() {
        return slotTime;
    }

    public void setSlotTime(String slotTime) {
        this.slotTime = slotTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getFlatBuildingStreet() {
        return flatBuildingStreet;
    }

    public void setFlatBuildingStreet(String flatBuildingStreet) {
        this.flatBuildingStreet = flatBuildingStreet;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getProviderStatus() {
        return providerStatus;
    }

    public void setProviderStatus(String providerStatus) {
        this.providerStatus = providerStatus;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public int getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(int expireTime) {
        this.expireTime = expireTime;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
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

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public BookingBill getBookingBill() {
        return bookingBill;
    }

    public void setBookingBill(BookingBill bookingBill) {
        this.bookingBill = bookingBill;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }

    public List<Cart> getCart() {
        return cart;
    }

    public void setCart(List<Cart> cart) {
        this.cart = cart;
    }

    public BookingsBids getBookingsBids() {
        return bookingsBids;
    }

    public void setBookingsBids(BookingsBids bookingsBids) {
        this.bookingsBids = bookingsBids;
    }

    public BookingsFinalOffer getBookingsFinalOffer() {
        return bookingsFinalOffer;
    }

    public void setBookingsFinalOffer(BookingsFinalOffer bookingsFinalOffer) {
        this.bookingsFinalOffer = bookingsFinalOffer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.orderNumber);
        dest.writeString(this.orderType);
        dest.writeInt(this.userId);
        dest.writeString(this.slotDate);
        dest.writeString(this.slotTime);
        dest.writeString(this.message);
        dest.writeString(this.latitude);
        dest.writeString(this.longitude);
        dest.writeString(this.latLonAddress);
        dest.writeString(this.flatBuildingStreet);
        dest.writeString(this.address);
        dest.writeString(this.userStatus);
        dest.writeString(this.providerStatus);
        dest.writeInt(this.providerId);
        dest.writeInt(this.credit);
        dest.writeInt(this.expireTime);
        dest.writeString(this.otp);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
        dest.writeDouble(this.distance);
        dest.writeInt(this.seconds);
        dest.writeParcelable(this.bookingBill, flags);
        dest.writeParcelable(this.service, flags);
        dest.writeParcelable(this.category, flags);
        dest.writeParcelable(this.subCategory, flags);
        dest.writeTypedList(this.cart);
        dest.writeParcelable(this.bookingsBids, flags);
        dest.writeParcelable(this.bookingsFinalOffer, flags);
        dest.writeParcelable(this.user, flags);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.orderNumber = source.readString();
        this.orderType = source.readString();
        this.userId = source.readInt();
        this.slotDate = source.readString();
        this.slotTime = source.readString();
        this.message = source.readString();
        this.latitude = source.readString();
        this.longitude = source.readString();
        this.latLonAddress = source.readString();
        this.flatBuildingStreet = source.readString();
        this.address = source.readString();
        this.userStatus = source.readString();
        this.providerStatus = source.readString();
        this.providerId = source.readInt();
        this.credit = source.readInt();
        this.expireTime = source.readInt();
        this.otp = source.readString();
        this.createdAt = source.readString();
        this.updatedAt = source.readString();
        this.distance = source.readDouble();
        this.seconds = source.readInt();
        this.bookingBill = source.readParcelable(BookingBill.class.getClassLoader());
        this.service = source.readParcelable(Service.class.getClassLoader());
        this.category = source.readParcelable(Category.class.getClassLoader());
        this.subCategory = source.readParcelable(SubCategory.class.getClassLoader());
        this.cart = source.createTypedArrayList(Cart.CREATOR);
        this.bookingsBids = source.readParcelable(BookingsBids.class.getClassLoader());
        this.bookingsFinalOffer = source.readParcelable(BookingsFinalOffer.class.getClassLoader());
        this.user = source.readParcelable(User.class.getClassLoader());
    }

    protected Lead(Parcel in) {
        this.id = in.readInt();
        this.orderNumber = in.readString();
        this.orderType = in.readString();
        this.userId = in.readInt();
        this.slotDate = in.readString();
        this.slotTime = in.readString();
        this.message = in.readString();
        this.latitude = in.readString();
        this.longitude = in.readString();
        this.latLonAddress = in.readString();
        this.flatBuildingStreet = in.readString();
        this.address = in.readString();
        this.userStatus = in.readString();
        this.providerStatus = in.readString();
        this.providerId = in.readInt();
        this.credit = in.readInt();
        this.expireTime = in.readInt();
        this.otp = in.readString();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
        this.distance = in.readDouble();
        this.seconds = in.readInt();
        this.bookingBill = in.readParcelable(BookingBill.class.getClassLoader());
        this.service = in.readParcelable(Service.class.getClassLoader());
        this.category = in.readParcelable(Category.class.getClassLoader());
        this.subCategory = in.readParcelable(SubCategory.class.getClassLoader());
        this.cart = in.createTypedArrayList(Cart.CREATOR);
        this.bookingsBids = in.readParcelable(BookingsBids.class.getClassLoader());
        this.bookingsFinalOffer = in.readParcelable(BookingsFinalOffer.class.getClassLoader());
        this.user = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<Lead> CREATOR = new Creator<Lead>() {
        @Override
        public Lead createFromParcel(Parcel source) {
            return new Lead(source);
        }

        @Override
        public Lead[] newArray(int size) {
            return new Lead[size];
        }
    };
}