package com.evergreen.locator.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Store implements Serializable {

    @SerializedName("store_id")
    @Expose
    private Integer storeId;

    @SerializedName("name")
    @Expose
    private String branchName;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;

    @SerializedName("location")
    @Expose
    private Location location;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("contact_number")
    @Expose
    private String contactNumber;
    @SerializedName("email")
    @Expose
    private String email;


    public Store(){

    }

    public Store(Integer storeId, String branchName, String imageUrl, String address, Double longitude, Double latitude, String contactNumber) {
        this.storeId = storeId;
        this.branchName = branchName;
        this.imageUrl = imageUrl;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.contactNumber = contactNumber;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getImageUrl() {
        return "https://s3.eu-west-2.amazonaws.com/amlahspices.com/img/body-bg.jpg";
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
