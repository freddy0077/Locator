package com.evergreen.locator.api;

import com.evergreen.locator.model.NewsList;
import com.evergreen.locator.model.Response;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET(HttpParams.SHEET_API_END_POINT)
    Call<Response> getStoreList(@Query("id") String sheetId, @Query("sheet") String sheetName);
//    Call<StoreList> getStoreList(@Query("id") String sheetId, @Query("sheet") String sheetName);

    @GET(HttpParams.SHEET_API_END_POINT)
    Call<NewsList> getNewsList(@Query("id") String sheetId, @Query("sheet") String sheetName);
}
