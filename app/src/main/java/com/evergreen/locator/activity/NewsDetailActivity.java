package com.evergreen.locator.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdView;
import com.evergreen.locator.R;
import com.evergreen.locator.data.AppConstant;
import com.evergreen.locator.model.News;
import com.evergreen.locator.utility.AdUtils;


public class NewsDetailActivity extends AppCompatActivity {

    private Context mContext;
    private Activity mActivity;
    private ImageView imgNews;
    private TextView tvNewsTitle,tvNewDetail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVariable();
        initView();
        getStoreDetailData();

    }

    private void initVariable(){
        mContext = getApplicationContext();
        mActivity = NewsDetailActivity.this;

    }

    private void initView() {
        setContentView(R.layout.activity_news_detail);
        imgNews=findViewById(R.id.image_view_news);
        tvNewsTitle=findViewById(R.id.txtview_news_title);
        tvNewDetail=findViewById(R.id.txtview_news_detail);

    }

    private void getStoreDetailData() {
        Bundle extraDetail = getIntent().getExtras();
        News newsDetail = (News) extraDetail.getSerializable(AppConstant.KEY_DETAIL_DATA);
        setNewsDetail(newsDetail);
    }


    public void setNewsDetail(News newsDetail) {
        tvNewsTitle.setText(newsDetail.getTitle());
        tvNewDetail.setText(newsDetail.getDetails());

        //Loading image from Glide library.
        Glide.with(mActivity)
                .load(newsDetail.getImgUrl())
                .placeholder(R.color.gray_active_icon)
                .into(imgNews);


    }

    @Override
    protected void onResume() {
        super.onResume();
        AdUtils.getInstance(mContext).showBannerAd((AdView) findViewById(R.id.adView));

    }
}
