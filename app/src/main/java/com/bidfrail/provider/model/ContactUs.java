package com.bidfrail.provider.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContactUs {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("user_contact_us")
    @Expose
    private String userContactUs;

    @SerializedName("provider_contact_us")
    @Expose
    private String providerContactUs;

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

    public String getUserContactUs() {
        return userContactUs;
    }

    public void setUserContactUs(String userContactUs) {
        this.userContactUs = userContactUs;
    }

    public String getProviderContactUs() {
        return providerContactUs;
    }

    public void setProviderContactUs(String providerContactUs) {
        this.providerContactUs = providerContactUs;
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