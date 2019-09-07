package com.evergreen.locator.model;

public class InfoWindowData {
    private String branchName,Address,contact,distance;

    public InfoWindowData(){

    }

    public InfoWindowData(String branchName, String address, String contact, String distance) {
        this.branchName = branchName;
        Address = address;
        this.contact = contact;
        this.distance = distance;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
