package com.evergreen.locator.fragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.evergreen.locator.R;
import com.evergreen.locator.adapter.NewsOfferAdapter;
import com.evergreen.locator.api.HttpParams;
import com.evergreen.locator.api.RetrofitClient;
import com.evergreen.locator.data.AppConstant;
import com.evergreen.locator.listener.OnItemClickListener;
import com.evergreen.locator.model.News;
import com.evergreen.locator.model.NewsList;
import com.evergreen.locator.utility.ActivityUtils;
import com.prof.rssparser.Article;
import com.prof.rssparser.OnTaskCompleted;
import com.prof.rssparser.Parser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewsFragment extends Fragment {

    private RecyclerView recycleNewsList;
    private ArrayList<News> newsList;
    private NewsOfferAdapter newsOfferAdapter;
    private LinearLayoutManager linearLayoutManager;
    private Activity mActivity;
    private Context mContext;
    private LinearLayout loadingView, noDataView;
    private RelativeLayout newsBannerLayout;
    private ImageView imgNews;
    private TextView tvNewsTitle,tvNewDetail,tvToolbarTitle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = null;
        rootView = inflater.inflate(R.layout.fragment_news, container, false);
        intVariable();
        intView(rootView);
        initLoader(rootView);
        initEmptyView(rootView);
        intFunctionality();
        initListener();
        return rootView;
    }

    public void intView(View rootView) {

        newsBannerLayout = rootView.findViewById(R.id.rootLayout);

        recycleNewsList = rootView.findViewById(R.id.recycleView_news_list);
        recycleNewsList.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(mActivity);
        recycleNewsList.setLayoutManager(linearLayoutManager);



        newsOfferAdapter = new NewsOfferAdapter(mActivity, newsList);
        recycleNewsList.setAdapter(newsOfferAdapter);

        imgNews=rootView.findViewById(R.id.image_news_banner);
        tvNewsTitle=rootView.findViewById(R.id.text_news_title_banner);
        tvNewDetail=rootView.findViewById(R.id.text_news_detail_banner);

        tvToolbarTitle=rootView.findViewById(R.id.toolbar_title);
        tvToolbarTitle.setText(AppConstant.TOOLBAR_TITLE_NEWS);

    }

    public void intVariable() {
        mActivity = getActivity();
        mContext = getActivity().getApplicationContext();
        newsList = new ArrayList<>();
    }

    public void intFunctionality() {
//        loadAllNewsData();

        loadAllNewsRssData();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    public void loadAllNewsData() {

        RetrofitClient.getClient().getNewsList(HttpParams.SHEET_ID, HttpParams.SHEET_NEW_OFFER).enqueue(new Callback<NewsList>() {
            @Override
            public void onResponse(Call<NewsList> call, Response<NewsList> response) {

                newsList.addAll(response.body().getNews());
                newsBannerLayout.setVisibility(View.VISIBLE);
                if (!(newsList.isEmpty())) {

                    newsOfferAdapter.notifyDataSetChanged();
                    hideLoader();
                    setBannerData();

                }
            }

            @Override
            public void onFailure(Call<NewsList> call, Throwable t) {
                hideLoader();
                showEmptyView();
                newsBannerLayout.setVisibility(View.GONE);
            }

        });

    }

    public void loadAllNewsRssData(){
        String urlString = "http://rss.cnn.com/rss/edition_technology.rss";
        Parser parser = new Parser();
        parser.onFinish(new OnTaskCompleted() {

            //what to do when the parsing is done
            @Override
            public void onTaskCompleted(List<Article> list) {
                // The list contains all article's data. For example you can use it for your adapter.
                Log.i("NewsFragment", "onTaskCompleted: " + list.get(0));
            }

            //what to do in case of error
            @Override
            public void onError(Exception e) {
                // Handle the exception
                Log.e("NewsFragment", "onError: " + e.getMessage() );
            }
        });
        parser.execute(urlString);
    }

    private void initListener() {
        newsOfferAdapter.setItemClickListener(new OnItemClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onItemListener(View view, int position) {

                        sendDataToDetail(position);
                }

        });


        newsBannerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDataToDetail(0);
            }
        });
    }

    private void sendDataToDetail(int position) {
        News newsDetail = new News(newsList.get(position).getTitle(),
                newsList.get(position).getImgUrl(), newsList.get(position).getDetails());
        ActivityUtils.invokNewsDetail(mActivity, newsDetail);
    }

    public void initLoader(View rootView) {
        loadingView =rootView. findViewById(R.id.loadingView);
        noDataView = rootView.findViewById(R.id.noDataView);
    }

    public void hideLoader() {
        if (loadingView != null) {
            loadingView.setVisibility(View.GONE);
        }
        if (noDataView != null) {
            noDataView.setVisibility(View.GONE);
        }
    }

    public void initEmptyView(View rootview) {
        noDataView = rootview.findViewById(R.id.noDataView);
    }

    public void showEmptyView() {
        if (noDataView != null) {
            noDataView.setVisibility(View.VISIBLE);
        }
    }

    public void hideEmptyView() {
        if (noDataView != null) {
            noDataView.setVisibility(View.GONE);
        }
    }


    private void setBannerData(){
        tvNewsTitle.setText(newsList.get(0).getTitle());
        tvNewDetail.setText(newsList.get(0).getDetails());
        Glide.with(mActivity)
                .load(newsList.get(0).getImgUrl())
                .placeholder(R.color.gray_active_icon)
                .into(imgNews);

        newsList.remove(0);
        newsOfferAdapter.notifyDataSetChanged();

    }


}
