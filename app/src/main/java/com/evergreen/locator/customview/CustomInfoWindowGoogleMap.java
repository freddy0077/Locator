package com.evergreen.locator.customview;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.evergreen.locator.R;
import com.evergreen.locator.data.AppConstant;
import com.evergreen.locator.model.InfoWindowData;

public class CustomInfoWindowGoogleMap implements GoogleMap.InfoWindowAdapter {

    private Context mContext;
    private TextView txtBranchName, txtAddress,txtDistance;
    InfoWindowData infoWindowData;
    private String distance;

    public CustomInfoWindowGoogleMap(Context ctx,String distance){
        mContext = ctx;
        this.distance=distance;

    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(final Marker marker) {
        final View view = ((Activity)mContext).getLayoutInflater()
                .inflate(R.layout.info_window, null);
        view.setLayoutParams(new LinearLayout.LayoutParams(350, ViewGroup.LayoutParams.WRAP_CONTENT));
        infoWindowData=new InfoWindowData();
        txtBranchName=view.findViewById(R.id.tv_branch_name);
        txtAddress=view.findViewById(R.id.tv_address);
        txtDistance=view.findViewById(R.id.tv_distance);
        txtBranchName.setText(marker.getTitle());
        txtAddress.setText(marker.getSnippet());
        txtDistance.setText(AppConstant.DISTANCE+distance+AppConstant.KM_FOR_YOU);
        infoWindowData.setDistance(distance);

        return view;
    }


}
