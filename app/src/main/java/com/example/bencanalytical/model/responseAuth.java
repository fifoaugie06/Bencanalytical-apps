package com.example.bencanalytical.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class responseAuth {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private responseAuthData data;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public responseAuthData getData() {
        return data;
    }

    public void setData(responseAuthData data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
