package com.hcmut.social.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.hcmut.social.model.PostModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by John on 10/19/2016.
 */

public class PostImageAdapter extends BaseSocialAdapter {

    private List<PostModel> mData;
    private DisplayImageOptions mOpts;
    private OnImageItemClickListener listener;

    public interface OnImageItemClickListener {
        void onItemClick(PostModel post);
    }

    public PostImageAdapter(Context mContext) {
        super(mContext);

        mOpts = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
    }

    public void setData(List<PostModel> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    public void setListener(OnImageItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getCount() {
        if (mData == null)
            return 0;

        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(240, 240));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(mData.get(position));
            }
        });
        ImageLoader.getInstance().displayImage(mData.get(position).thumb, imageView, mOpts);
        return imageView;
    }
}
