package com.evergreen.locator.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;
import com.evergreen.locator.R;
import com.evergreen.locator.data.AppConstant;
import com.evergreen.locator.utility.AdUtils;

public class NotificationDetailsActivity extends AppCompatActivity {

    private Context mContext;
    private Activity mActivity;
    private Toolbar mToolbar;
    private TextView titleView, messageView;
    private Button linkButton;
    private String title, message, postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = NotificationDetailsActivity.this;
        mContext = mActivity.getApplicationContext();
        initView();
        initVeritable();
        initFunctionality();
        initListeners();
    }

    private void initVeritable() {
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            title = extras.getString(AppConstant.BUNDLE_KEY_TITLE);
            message = extras.getString(AppConstant.BUNDLE_KEY_MESSAGE);

        }
    }

    private void initView() {
        setContentView(R.layout.activity_notification_details);

        titleView =  findViewById(R.id.title);
        messageView = findViewById(R.id.message);
        linkButton = findViewById(R.id.linkButton);

        initToolbar();
        setToolBarTittle(getString(R.string.notifications));
        enableBackButton();
    }


    private void initFunctionality() {

        titleView.setText(title);
        messageView.setText(message);

        if (postId != null && !postId.isEmpty()) {
            linkButton.setEnabled(true);
        } else {
            linkButton.setEnabled(false);
        }

        AdUtils.getInstance(mContext).showBannerAd((AdView) findViewById(R.id.adView));
    }

    private void initListeners() {

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                goToHome();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onBackPressed() {
        goToHome();
    }

    private void goToHome(){
            Intent intent = new Intent(NotificationDetailsActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
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
}
