package com.evergreen.locator.utility;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.evergreen.locator.activity.NewsDetailActivity;
import com.evergreen.locator.activity.StoreDetailsActivity;
import com.evergreen.locator.data.AppConstant;
import com.evergreen.locator.model.News;
import com.evergreen.locator.model.Store;

public class ActivityUtils {

    private static ActivityUtils sActivityUtils = null;

    public static ActivityUtils getInstance() {
        if (sActivityUtils == null) {
            sActivityUtils = new ActivityUtils();
        }
        return sActivityUtils;
    }

    public void invokeActivity(Activity activity, Class<?> tClass, boolean shouldFinish) {
        Intent intent = new Intent(activity, tClass);
        activity.startActivity(intent);
        if (shouldFinish) {
            activity.finish();
        }
    }

    public static void invokStoreDetail(Activity mContext, Store storeDetail){
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstant.KEY_DETAIL_DATA, storeDetail);
        Intent intent = new Intent(mContext, StoreDetailsActivity.class);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    public static void invokNewsDetail(Activity mContext, News newsDetail){
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstant.KEY_DETAIL_DATA, newsDetail);
        Intent intent = new Intent(mContext, NewsDetailActivity.class);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    public void invokePhoneCall(Context context, String contactNumber) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+contactNumber));
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE},AppConstant.REQUEST_PHONE_CALL);
            return;
        }
        else {
            context.startActivity(callIntent);
        }

    }



}
