package com.evergreen.locator.activity;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import com.evergreen.locator.R;
import com.evergreen.locator.data.AppConstant;
import com.evergreen.locator.data.preference.AppPreference;
import com.evergreen.locator.data.preference.PrefKey;
import com.evergreen.locator.utility.ActivityUtils;

public class SettingActivity extends AppCompatActivity {

    private Context mContext;
    private Activity mActivity;
    private Toolbar mToolbar;
    private SeekBar seekBarAreaCover;
    private TextView tvProgress;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intVariable();
        intView();
        initListener();
        initFunctionality();

    }

    public void intView() {
        setContentView(R.layout.activity_setting);
        seekBarAreaCover=findViewById(R.id.seek_bar);
        tvProgress = findViewById(R.id.progress);
        checkBox=findViewById(R.id.checkBox);

        initToolbar();
        setToolBarTittle(getString(R.string.setting));
        enableBackButton();
    }



    public void intVariable() {
        mContext = getApplicationContext();
        mActivity = SettingActivity.this;
    }

    private void initListener() {
        seekBarAreaCover.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                AppPreference.getInstance(mContext).setInteger(PrefKey.SEEKBAR_VALUE, progress);
                AppPreference.getInstance(mContext).setBoolean(PrefKey.IS_LOADED, true);
                tvProgress.setText(AppConstant.SPACE + progress + AppConstant.KM);
                int zoom = 0;
                if (progress <= 100) {
                    zoom = 12;
                }

                AppPreference.getInstance(mContext).setInteger(PrefKey.ZOOM, zoom);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AppPreference.getInstance(mContext).setNotificationStatus(isChecked);
            }
        });
    }

    private void initFunctionality() {
        if (AppPreference.getInstance(mContext).getBoolean(PrefKey.IS_LOADED)){
            int value=AppPreference.getInstance(mContext).getInteger(PrefKey.SEEKBAR_VALUE);
            seekBarAreaCover.setProgress(value);
            tvProgress.setText(AppConstant.SPACE+value+ AppConstant.KM);
        }

        if (AppPreference.getInstance(mContext).getNotificationStatus()){
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }
    }

    public void initToolbar() {
        mToolbar = findViewById(R.id.toolbar_main_settings);
        setSupportActionBar(mToolbar);
    }

    public Toolbar getToolbar() {
        if (mToolbar == null) {
            mToolbar = findViewById(R.id.toolbar_main_settings);
            setSupportActionBar(mToolbar);
        }
        return mToolbar;
    }

    public void setToolBarTittle(String toolBarTitle) {
        TextView toolTitle = findViewById(R.id.toolbar_title);
        toolTitle.setText(toolBarTitle);
    }

    public void enableBackButton() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ActivityUtils.getInstance().invokeActivity(SettingActivity.this, MainActivity.class, true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
