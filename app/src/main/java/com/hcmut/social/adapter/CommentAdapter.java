package com.hcmut.social.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hcmut.social.R;
import com.hcmut.social.model.CommentModel;
import com.hcmut.social.model.PostModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by John on 10/19/2016.
 */
public class CommentAdapter extends BaseSocialAdapter {

    private List<CommentModel> mData;
    private DisplayImageOptions mOpts;

    public void setData(List<CommentModel> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    public CommentAdapter(Context mContext) {
        super(mContext);

        mOpts = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
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
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder convertView;
        if (view == null) {
            convertView = new ViewHolder(mContext);
        } else {
            convertView = (ViewHolder) view;
        }
        CommentModel model = mData.get(position);
        convertView.setData(model, position);
        convertView.bindingData();
        return convertView;
    }

    class ViewHolder extends LinearLayout {

        ImageView img_avatar;
        TextView tv_content;
        TextView tv_time;


        CommentModel model;
        int pos;

        public ViewHolder(Context context) {
            super(context);

            LayoutInflater li = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            li.inflate(R.layout.row_comment, this, true);

            img_avatar = (ImageView) findViewById(R.id.avatar_imageview);
            tv_content = (TextView) findViewById(R.id.content_text);
            tv_time = (TextView) findViewById(R.id.time_text);
        }

        public void setData(CommentModel model, int pos) {
            this.model = model;
            this.pos = pos;
        }

        public void bindingData() {
            tv_content.setText(model.content+"");
            tv_time.setText(model.created_at+"");

        }
    }
}
