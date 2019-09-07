package com.evergreen.locator.data;

import com.evergreen.locator.model.NewsList;
import com.evergreen.locator.model.Store;

import java.util.ArrayList;

public class AppConstant {
    // All Store List
    public static final ArrayList<Store> ALL_STORE_LIST = new ArrayList<>();
    public static final ArrayList<NewsList> ALL_NEWS_LIST = new ArrayList<>();
    public static final String TOOLBAR_TITLE_NEWS ="News & Offer" ;
    public static final String TOOLBAR_TITLE_ALL_STORE ="All Store" ;
    public static  double MY_LOCATION_LATITUTE =0;
    public static  double MY_LOCATION_LONGITUTE =0;
    public static final String KEY_DETAIL_DATA ="detail_data" ;
    public static final String KM = "km";
    public static final String SPACE = "";
    public static final String DISTANCE ="Distance:";
    public static final String KM_FOR_YOU = " Km From You";

    public static final int REQUEST_PHONE_CALL = 2;
    public static  int COVER_AREA=1;

    // push notification
    public static final String BUNDLE_KEY_TITLE = "title";
    public static final String BUNDLE_KEY_MESSAGE = "message";


    public static int OVERVIEW=0;

    public static final int VALUE_ZERO = 0;
}
