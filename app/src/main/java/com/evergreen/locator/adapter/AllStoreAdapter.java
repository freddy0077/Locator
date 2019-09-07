package com.evergreen.locator.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.evergreen.locator.R;
import com.evergreen.locator.listener.OnItemClickListener;
import com.evergreen.locator.model.Store;

import java.util.ArrayList;

public class AllStoreAdapter extends RecyclerView.Adapter<AllStoreAdapter.CustomViewHolder> {

    private Context context;
    private ArrayList<Store> storeList;
    private OnItemClickListener mListener;

    public AllStoreAdapter(Context context, ArrayList<Store> storeList) {
        this.context = context;
        this.storeList = storeList;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        Context context;
        ArrayList<Store> storeList;
        private TextView txtStrName,txtStrAddress;
        private ImageView imgStore,imgCall;

        public CustomViewHolder(final View itemView, Context context, ArrayList<Store> storeList) {
            super(itemView);
            this.context = context;
            this.storeList = storeList;
            txtStrName = itemView.findViewById(R.id.text_news_title_banner);
            txtStrAddress= itemView.findViewById(R.id.text_news_detail_banner);
            imgStore =  itemView.findViewById(R.id.image_news_banner);
            imgCall =  itemView.findViewById(R.id.icon_news_call);

            imgCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemListener(view,getLayoutPosition());
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemListener(itemView,getLayoutPosition());
                }
            });

        }

    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_store, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view, context, storeList);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(CustomViewHolder holder, final int position) {

        holder.txtStrName.setText(storeList.get(position).getBranchName());

        holder.txtStrAddress.setText(storeList.get(position).getLocation().getAddress());
        Glide.with(context)
                .load(storeList.get(position).getImageUrl())
                .placeholder(R.color.gray_active_icon)
                .into(holder.imgStore);
    }

    @Override
    public int getItemCount() {
        return storeList.size();
    }

    public ArrayList<Store> getDataList() {
        return storeList;
    }


    public void setFilter(ArrayList<Store> newDataList) {
        storeList =new ArrayList<>();
        storeList.addAll(newDataList);
        notifyDataSetChanged();
    }

    public void setItemClickListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

}
