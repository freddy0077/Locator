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
import com.evergreen.locator.model.News;

import java.util.ArrayList;

public class NewsOfferAdapter extends RecyclerView.Adapter<NewsOfferAdapter.CustomViewHolder> {

    private Context context;
    private ArrayList<News> newsList;
    private OnItemClickListener mListener;

    public NewsOfferAdapter(Context context, ArrayList<News> newsList) {
        this.context = context;
        this.newsList = newsList;
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {

        Context context;
        ArrayList<News> newsList;
        private TextView txtNewsTitle, txtNewsDetails;
        private ImageView imgNewsStore ;

        public CustomViewHolder(final View itemView, Context context, ArrayList<News> newsList) {
            super(itemView);
            this.context = context;
            this.newsList = newsList;
            txtNewsTitle = itemView.findViewById(R.id.text_news_title_banner);
            txtNewsDetails = itemView.findViewById(R.id.text_news_detail_banner);
            imgNewsStore =  itemView.findViewById(R.id.image_news_banner);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemListener(itemView,getLayoutPosition());
                }
            });

        }

    }

    @Override
    public NewsOfferAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_news_offer, null);
        NewsOfferAdapter.CustomViewHolder viewHolder = new NewsOfferAdapter.CustomViewHolder(view, context, newsList);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(NewsOfferAdapter.CustomViewHolder holder, final int position) {

        holder.txtNewsTitle.setText(newsList.get(position).getTitle());
        holder.txtNewsDetails.setText(newsList.get(position).getDetails());

        Glide.with(context)
                .load(newsList.get(position).getImgUrl())
                .placeholder(R.color.gray_active_icon)
                .into(holder.imgNewsStore);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public void setItemClickListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

}
