package com.bidfrail.provider.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AboutUs {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("user_about_us")
    @Expose
    private String userAboutUs;

    @SerializedName("provider_about_us")
    @Expose
    private String providerAboutUs;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserAboutUs() {
        return userAboutUs;
    }

    public void setUserAboutUs(String userAboutUs) {
        this.userAboutUs = userAboutUs;
    }

    public String getProviderAboutUs() {
        return providerAboutUs;
    }

    public void setProviderAboutUs(String providerAboutUs) {
        this.providerAboutUs = providerAboutUs;
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
}