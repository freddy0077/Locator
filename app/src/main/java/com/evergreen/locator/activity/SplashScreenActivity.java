package com.evergreen.locator.activity;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.evergreen.locator.R;

import com.evergreen.locator.utility.ActivityUtils;
import com.evergreen.locator.utility.AppUtils;

public class SplashScreenActivity extends AppCompatActivity {

    private Context mContext;
    private Activity mActivity;
    private static final int SPLASH_DURATION = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initVariables();
        initView();
    }

    private void initVariables() {
        mActivity = SplashScreenActivity.this;
        mContext = mActivity.getApplicationContext();
    }

    private void initView() {
        setContentView(R.layout.activity_splash_screen);

    }

    @Override
    protected void onResume() {
        super.onResume();
        initFunctionality();
    }

    private void initFunctionality() {
        if (AppUtils.isNetworkAvailable(mContext)) {
            findViewById(R.id.ivLogo).postDelayed(new Runnable() {
                @Override
                public void run() {
                    ActivityUtils.getInstance().invokeActivity(mActivity, MainActivity.class, true);
                }
            }, SPLASH_DURATION);

        } else {
            AppUtils.noInternetWarning(findViewById(R.id.ivLogo), mContext);
        }

    }
}
