package com.example.bencanalytical.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class responseBencana {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<responseBencanaData> data = null;
    @SerializedName("data_count")
    @Expose
    private Integer dataCount;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<responseBencanaData> getData() {
        return data;
    }

    public void setData(List<responseBencanaData> data) {
        this.data = data;
    }

    public Integer getDataCount() {
        return dataCount;
    }

    public void setDataCount(Integer dataCount) {
        this.dataCount = dataCount;
    }
}
