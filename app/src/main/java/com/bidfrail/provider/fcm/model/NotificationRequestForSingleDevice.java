package com.bidfrail.provider.fcm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationRequestForSingleDevice {

    @SerializedName("to")
    @Expose
    private String to;

    @SerializedName("data")
    @Expose
    private Data data;

    @SerializedName("content_available")
    @Expose
    private boolean contentAvailable; // Parameter used to set the content available (iOS only) : Example content_available : true

    @SerializedName("priority")
    @Expose
    private String priority;  // Example priority : normal or high

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public boolean isContentAvailable() {
        return contentAvailable;
    }

    public void setContentAvailable(boolean contentAvailable) {
        this.contentAvailable = contentAvailable;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
