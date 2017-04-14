package com.qaitamarkhatri.myfirebasesample.nativelist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.formats.NativeContentAd;
import com.qaitamarkhatri.myfirebasesample.R;

import java.util.ArrayList;

/**
 * Created by QAIT\amarkhatri on 12/3/17.
 */

public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int LIST_SIZE = 30;
    private static final int ITEM_TYPE_NORMAL = 0;
    private static final int ITEM_TYPE_AD = 1;
    private Context context;
    public ArrayList<ListData> dataList;
    public boolean headerAdded;
    public boolean footerAdded;
    public View headerView;

    public RecyclerListAdapter(Context context, ArrayList<ListData> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getItemViewType(int position) {
        ListData listData = dataList.get(position);
        if (listData.isAd()) {
            return ITEM_TYPE_AD;
        }
        return ITEM_TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_TYPE_AD:
                return new AdViewHolder(LayoutInflater.from(context).inflate(R.layout.dfp_small_ad_layout, parent, false));
            default:
            case ITEM_TYPE_NORMAL:
                return new NormalItemViewHolder(LayoutInflater.from(context).inflate(R.layout.news_list_small_item_layout, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ListData listData = dataList.get(position);

        BaseViewHolder baseViewHolder= (BaseViewHolder) holder;
        if (listData.isAd()) {
            NativeAdUtil.setDataInDfpSmallAdView(baseViewHolder.itemView, (NativeContentAd)listData.getQANativeAdBean().getAdObject(),null);
            return;
        }
        baseViewHolder.title.setText(listData.getName());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}

class BaseViewHolder extends RecyclerView.ViewHolder {
    TextView title;
    public BaseViewHolder(View itemView) {
        super(itemView);
    }
}

class NormalItemViewHolder extends BaseViewHolder {
    public NormalItemViewHolder(View itemView) {
        super(itemView);
        title= (TextView) itemView.findViewById(R.id.textViewTitle);
    }
}

class AdViewHolder extends BaseViewHolder {

    public AdViewHolder(View itemView) {
        super(itemView);
        title= (TextView) itemView.findViewById(R.id.contentad_headline);
    }

}