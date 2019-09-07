package com.evergreen.locator.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class StoreList {
    @SerializedName("store")
    @Expose
    private ArrayList<Store> store = null;

    public ArrayList<Store> getStore() {
        return store;
    }

    public void setStore(ArrayList<Store> store) {
        this.store = store;
    }

}

