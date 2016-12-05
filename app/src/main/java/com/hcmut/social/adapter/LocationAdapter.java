package com.hcmut.social.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hcmut.social.R;
import com.hcmut.social.model.LocationModel;
import com.hcmut.social.model.LocationModel;
import com.hcmut.social.model.PostModel;
import com.hcmut.social.utils.UserUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by John on 10/7/2016.
 */

public class LocationAdapter extends BaseSocialAdapter {

    private List<LocationModel> mData;
    private OnLocationClickListener listener;

    public interface OnLocationClickListener {
        void onLocationClick(LocationModel location);
    }

    public LocationAdapter(Context mContext) {
        super(mContext);
    }

    public void setData(List<LocationModel> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    public void setListener(OnLocationClickListener listener) {
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
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder convertView;
        if (view == null) {
            convertView = new ViewHolder(mContext);
        } else {
            convertView = (ViewHolder) view;
        }
        LocationModel model = mData.get(position);
        convertView.setData(model, position);
        convertView.bindingData();
        return convertView;
    }

    class ViewHolder extends LinearLayout {

        TextView tvLocation;

        LocationModel model;
        int pos;

        public ViewHolder(Context context) {
            super(context);

            LayoutInflater li = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            li.inflate(R.layout.row_location, this, true);

            tvLocation = (TextView) findViewById(R.id.tv_location);
        }

        public void setData(LocationModel model, int pos) {
            this.model = model;
            this.pos = pos;
        }

        public void bindingData() {
            tvLocation.setText(model.name);
            tvLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onLocationClick(model);
                }
            });
        }
    }
}
