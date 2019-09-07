package com.evergreen.locator.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class News implements Serializable {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("img_url")
    @Expose
    private String imgUrl;
    @SerializedName("details")
    @Expose
    private String details;

    public News(String title, String imgUrl, String details) {
        this.title = title;
        this.imgUrl = imgUrl;
        this.details = details;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
