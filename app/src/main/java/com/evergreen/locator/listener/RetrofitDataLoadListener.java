package com.evergreen.locator.listener;

import com.evergreen.locator.model.Store;

import java.util.ArrayList;

public interface RetrofitDataLoadListener {
    void finishLoadData(ArrayList<Store> dataList, boolean isSuccessful);

}
