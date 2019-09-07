package com.evergreen.locator.listener;

import android.view.View;

import com.google.android.gms.maps.model.Marker;

public interface OnInfoWindowItemClickListener {
    void onItemListener(View view, Marker marker);
}
