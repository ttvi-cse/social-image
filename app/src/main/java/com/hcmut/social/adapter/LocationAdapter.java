package com.hcmut.social.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hcmut.social.R;
import com.hcmut.social.model.LocationModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 10/7/2016.
 */

public class LocationAdapter extends BaseSocialAdapter implements Filterable {

    private List<LocationModel> mOriginalData;
    private List<LocationModel> mFilterData;
    private OnLocationClickListener listener;

    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence filterText) {
                final FilterResults results = new FilterResults();

                if (filterText == null || filterText.length() == 0) {
                    synchronized (this) {
                        results.values = mOriginalData;
                        results.count = mOriginalData.size();
                    }
                } else {
                    ArrayList<LocationModel> filter = new ArrayList<>();
                    if (mFilterData != null && mFilterData.size() > 0) {
                        for (final LocationModel g : mFilterData) {
                            if (g.name.toLowerCase()
                                    .contains(filterText.toString()))
                                filter.add(g);
                        }
                    }
                    results.values = filter;
                    results.count = filter.size();
                }
                return results;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                mFilterData = (ArrayList<LocationModel>) results.values;
                if (results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
    }

    public interface OnLocationClickListener {
        void onLocationClick(LocationModel location);
    }

    public LocationAdapter(Context mContext) {
        super(mContext);
    }

    public void setData(List<LocationModel> mData) {
        this.mFilterData = mData;
        this.mOriginalData = mData;
        notifyDataSetChanged();
    }

    public void setListener(OnLocationClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getCount() {
        if (mFilterData == null)
            return 0;

        return mFilterData.size();
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
        LocationModel model = mFilterData.get(position);
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
