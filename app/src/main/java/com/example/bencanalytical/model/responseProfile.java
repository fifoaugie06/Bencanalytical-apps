package com.example.bencanalytical.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class responseProfile {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private responseProfileData data;
    @SerializedName("data_count")
    @Expose
    private String dataCount;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public responseProfileData getData() {
        return data;
    }

    public void setData(responseProfileData data) {
        this.data = data;
    }

    public String getDataCount() {
        return dataCount;
    }

    public void setDataCount(String dataCount) {
        this.dataCount = dataCount;
    }
}
