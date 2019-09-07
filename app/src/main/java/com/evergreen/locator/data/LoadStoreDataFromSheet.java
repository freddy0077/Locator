package com.evergreen.locator.data;

import android.util.Log;

import com.evergreen.locator.api.HttpParams;
import com.evergreen.locator.api.RetrofitClient;
import com.evergreen.locator.listener.RetrofitDataLoadListener;
import com.evergreen.locator.model.Store;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadStoreDataFromSheet {
    private RetrofitDataLoadListener mListener;
    //locad data from sheet
    public void loadAllStoreData() {
        final ArrayList<Store> allStorelList = new ArrayList<>();
        RetrofitClient.getClient().getStoreList(HttpParams.SHEET_ID, HttpParams.SHEET_NAME_STORE).enqueue(new Callback<com.evergreen.locator.model.Response>() {
            @Override
            public void onResponse(Call<com.evergreen.locator.model.Response> call, Response<com.evergreen.locator.model.Response> response) {
                try{

                    Log.i("LoadStoreDataFromSheet", "onResponse: " + response.body().getBody().getStore());
                    allStorelList.addAll(response.body().getBody().getStore());
                    AppConstant.ALL_STORE_LIST.addAll(allStorelList);
                    mListener.finishLoadData(allStorelList, true);

                }
                catch (Exception e){
                    e.getMessage();
                }


            }

            @Override
            public void onFailure(Call<com.evergreen.locator.model.Response> call, Throwable t) {
                Log.d("DataTesing", "Second Req DetailsDataNotFound " + t.toString());
            }
        });

    }

    public void setClickListener(RetrofitDataLoadListener mListener) {
        this.mListener = mListener;
    }

}
