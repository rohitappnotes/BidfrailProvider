package com.bidfrail.provider.fcm.model;

import com.bidfrail.provider.AppConstants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName(AppConstants.Screen.Key.KYE_NAVIGATE_TO)
    @Expose
    private String navigateToScreen;

    @SerializedName(AppConstants.Screen.Key.KYE_TITLE)
    @Expose
    private String title;

    @SerializedName(AppConstants.Screen.Key.KYE_MESSAGE)
    @Expose
    private String message;

    @SerializedName(AppConstants.Screen.Key.KYE_ORDER_TYPE)
    @Expose
    private String orderType;

    @SerializedName(AppConstants.Screen.Key.KYE_ORDER_ID)
    @Expose
    private String orderId;

    @SerializedName(AppConstants.Screen.Key.KYE_DATA_REQUIRED)
    @Expose
    private String dateRequiredForScreen;

    public Data() {
    }

    public Data(String navigateToScreen, String title, String message, String orderType, String orderId, String dateRequiredForScreen) {
        this.navigateToScreen = navigateToScreen;
        this.title = title;
        this.message = message;
        this.orderType = orderType;
        this.orderId = orderId;
        this.dateRequiredForScreen = dateRequiredForScreen;
    }

    public String getNavigateToScreen() {
        return navigateToScreen;
    }

    public void setNavigateToScreen(String navigateToScreen) {
        this.navigateToScreen = navigateToScreen;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDateRequiredForScreen() {
        return dateRequiredForScreen;
    }

    public void setDateRequiredForScreen(String dateRequiredForScreen) {
        this.dateRequiredForScreen = dateRequiredForScreen;
    }
}
