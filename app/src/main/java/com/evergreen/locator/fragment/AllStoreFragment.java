package com.evergreen.locator.fragment;

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
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.evergreen.locator.R;
import com.evergreen.locator.adapter.AllStoreAdapter;
import com.evergreen.locator.data.AppConstant;
import com.evergreen.locator.listener.OnItemClickListener;
import com.evergreen.locator.model.Location;
import com.evergreen.locator.model.Store;
import com.evergreen.locator.utility.ActivityUtils;
import com.evergreen.locator.utility.AdUtils;

import java.util.ArrayList;

public class AllStoreFragment extends Fragment {
    private RecyclerView recycleStoreList;
    private ArrayList<Store> storeList, filterStoreList;
    private AllStoreAdapter allStoreAdapter;
    private Activity mActivity;
    private Context mContext;
    private LinearLayoutManager lytManagerWord;
    private LinearLayout loadingView, noDataView;
    private SearchView searchView;
    private TextView tvToolbarTitle;
    private ImageView imageView_search;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = null;
        rootView = inflater.inflate(R.layout.all_store_list, container, false);
        initVariable();
        initView(rootView);
        initLoader(rootView);
        initEmptyView(rootView);
        initFunctionality();
        initListener();

        return rootView;
    }

    private void initView(View rootView) {

        recycleStoreList = rootView.findViewById(R.id.recycleView_store_list);
        recycleStoreList.setHasFixedSize(true);
        lytManagerWord = new LinearLayoutManager(mActivity);
        recycleStoreList.setLayoutManager(lytManagerWord);
        allStoreAdapter = new AllStoreAdapter(mActivity, storeList);
        recycleStoreList.setAdapter(allStoreAdapter);
        //recycleStoreList.addItemDecoration(new DividerItemDecoration(mActivity, lytManagerWord.VERTICAL, 16));
        searchView = rootView.findViewById(R.id.search_view_store);
        imageView_search=rootView.findViewById(R.id.imageView_search);
        tvToolbarTitle=rootView.findViewById(R.id.toolbar_title);
        tvToolbarTitle.setText(AppConstant.TOOLBAR_TITLE_ALL_STORE);

    }

    private void initVariable() {
        mActivity = getActivity();
        mContext = getActivity().getApplicationContext();
        storeList = new ArrayList<>();
        filterStoreList=new ArrayList<>();

    }


    private void initListener() {
        allStoreAdapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemListener(View view, int position) {
                loadFilterData();
                switch (view.getId()) {
                    case R.id.icon_news_call:
                        ActivityUtils.getInstance().invokePhoneCall(mActivity, filterStoreList.get(position).getContactNumber());
                        break;
                    default:
                        sentDataToDetail(position);
                       // Toast.makeText(mActivity, "click on View", Toast.LENGTH_SHORT).show();
                        // sendDataToDetail(position);
                        break;
                }

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterStoreList = getFilterData(storeList, newText);
                allStoreAdapter.setFilter(filterStoreList);
                return false;
            }
        });


        imageView_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchView.getVisibility()==View.GONE){
                    searchView.setVisibility(View.VISIBLE);
                    tvToolbarTitle.setVisibility(View.GONE);
                    imageView_search.setVisibility(View.GONE);
                    searchView.setIconified(false);
                    searchView.requestFocus();
                }

            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                searchView.setVisibility(View.GONE);
                tvToolbarTitle.setVisibility(View.VISIBLE);
                imageView_search.setVisibility(View.VISIBLE);
                return false;
            }
        });
    }

    //filter StoreList data
    private ArrayList<Store> getFilterData(ArrayList<Store> models, CharSequence searchKey) {
        searchKey = searchKey.toString().toLowerCase();

        final ArrayList<Store> filteredModelList = new ArrayList<>();
        for (Store model : models) {
            final String word = model.getBranchName().toLowerCase();
            final String meaning = model.getLocation().getAddress().toLowerCase();

            if (word.contains(searchKey) || meaning.contains(searchKey)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    private void loadFilterData() {
        if (!filterStoreList.isEmpty()) {
            filterStoreList.clear();
        }
        filterStoreList.addAll(allStoreAdapter.getDataList());
    }

    private void initFunctionality() {
        storeList.addAll(AppConstant.ALL_STORE_LIST);
        if (!storeList.isEmpty()) {
            loadFilterData();
        }
        if (!storeList.isEmpty()) {
            allStoreAdapter.notifyDataSetChanged();
            hideLoader();
        } else {
            hideLoader();
            showEmptyView();
        }
    }

    //Integer storeId, String branchName, String imageUrl,String address, Double longitude, Double latitude, String contactNumber
    private void sentDataToDetail(int position) {
//        Store storeDetail =
//                new Store(filterStoreList.get(position).getStoreId(),
//                filterStoreList.get(position).getBranchName(),
//                filterStoreList.get(position).getImageUrl(),
//                filterStoreList.get(position).getLocation().getAddress(),
//                Double.valueOf(filterStoreList.get(position).getLocation().getLng()),
//                Double.valueOf(filterStoreList.get(position).getLocation().getLat()),
//                        filterStoreList.get(position).getContactNumber());

        Store storeDetail = new Store();
        storeDetail.setBranchName(filterStoreList.get(position).getBranchName());
        storeDetail.setImageUrl(filterStoreList.get(position).getImageUrl());

        Location locationModel = new Location();
        locationModel.setAddress(filterStoreList.get(position).getLocation().getAddress());
        locationModel.setLat(filterStoreList.get(position).getLocation().getLat());
        locationModel.setLng(filterStoreList.get(position).getLocation().getLng());

        Log.i("LATITUDE", "sentDataToDetail: " + filterStoreList.get(position).getLocation().getLat());
        storeDetail.setLocation(locationModel);

        ActivityUtils.invokStoreDetail(mActivity, storeDetail);
    }

    public void initLoader(View rootView) {
        loadingView = rootView.findViewById(R.id.loadingView);
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


    public void showAdThenActivity(final Class<?> activity) {
        if (AdUtils.getInstance(mContext).showFullScreenAd()) {
            AdUtils.getInstance(mContext).getInterstitialAd().setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                    ActivityUtils.getInstance().invokeActivity(mActivity, activity, false);
                }
            });
        } else {
            ActivityUtils.getInstance().invokeActivity(mActivity, activity, false);
        }
    }



}


