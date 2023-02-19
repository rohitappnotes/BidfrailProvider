package com.bidfrail.provider.fcm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class NotificationResponse {

    @SerializedName("multicast_id")
    @Expose
    private long multicastId;

    @SerializedName("success")
    @Expose
    private int success;

    @SerializedName("failure")
    @Expose
    private int failure;

    @SerializedName("canonical_ids")
    @Expose
    private int canonicalIds;

    @SerializedName("results")
    @Expose
    private List<Result> results = null;

    public Long getMulticastId() {
        return multicastId;
    }

    public void setMulticastId(Long multicastId) {
        this.multicastId = multicastId;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getFailure() {
        return failure;
    }

    public void setFailure(int failure) {
        this.failure = failure;
    }

    public int getCanonicalIds() {
        return canonicalIds;
    }

    public void setCanonicalIds(int canonicalIds) {
        this.canonicalIds = canonicalIds;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
}
