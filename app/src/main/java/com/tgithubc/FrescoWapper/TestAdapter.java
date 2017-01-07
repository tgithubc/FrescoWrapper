package com.tgithubc.FrescoWapper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.tgithubc.fresco_wapper.ImageLoagerWapper;
import com.tgithubc.fresco_wapper.config.ImageLoadConfig;
import com.tgithubc.fresco_wapper.util.BlurPostprocessor;


/**
 * Created by litiancheng :)
 */

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {

    private String[] mData;
    private Context mContext;
    private ImageLoadConfig mBigConfig, mSmallConfig;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public TestAdapter(Context context, String[] data) {
        this.mContext = context;
        this.mData = data;
        this.mBigConfig = new ImageLoadConfig.Builder(context).setPostprocessor(new BlurPostprocessor(20)).create();
        this.mSmallConfig = new ImageLoadConfig.Builder(context).circle(5, R.color.colorShadow).create();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.test_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        ImageLoagerWapper.getInstance().load(viewHolder.mBigPicView, mData[i], mBigConfig);
        ImageLoagerWapper.getInstance().load(viewHolder.mSmallPicView, mData[i], mSmallConfig);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public SimpleDraweeView mBigPicView, mSmallPicView;

        public ViewHolder(View v) {
            super(v);
            mBigPicView = (SimpleDraweeView) v.findViewById(R.id.big_pic);
            mSmallPicView = (SimpleDraweeView) v.findViewById(R.id.small_pic);
        }
    }
}
