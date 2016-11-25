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
import com.hcmut.social.model.PostModel;
import com.hcmut.social.utils.UserUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by John on 10/7/2016.
 */

public class PostAdapter extends BaseSocialAdapter {

    private List<PostModel> mData;
    private DisplayImageOptions mOpts;

    private OnPostItemClickListener listener;

    public interface OnPostItemClickListener {
        void onItemClick(PostModel post);
    }

    public void setData(List<PostModel> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    public void setListener(OnPostItemClickListener listener) {
        this.listener = listener;
    }

    public PostAdapter(Context mContext) {
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
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder convertView;
        if (view == null) {
            convertView = new ViewHolder(mContext);
        } else {
            convertView = (ViewHolder) view;
        }
        PostModel model = mData.get(position);
        convertView.setData(model, position);
        convertView.bindingData();
        return convertView;
    }


    class ViewHolder extends LinearLayout {

        ImageView img_avatar;
        TextView tv_username;
        ImageView img_content;
        TextView tv_like_count;
        TextView tv_view_count;
        RatingBar rating_bar;

        PostModel model;
        int pos;

        public ViewHolder(Context context) {
            super(context);

            LayoutInflater li = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            li.inflate(R.layout.row_post, this, true);

            img_avatar = (ImageView) findViewById(R.id.avatar_imageview);
            tv_username = (TextView) findViewById(R.id.username_text);
            img_content = (ImageView) findViewById(R.id.content_imageview);
            tv_like_count = (TextView) findViewById(R.id.like_count_text);
            tv_view_count = (TextView) findViewById(R.id.view_count_text);
            rating_bar = (RatingBar) findViewById(R.id.rating_bar);
        }

        public void setData(PostModel model, int pos) {
            this.model = model;
            this.pos = pos;
        }

        public void bindingData() {
            /**
             * avatar, name
             */
            ImageLoader.getInstance().displayImage(UserUtil.getAvatarLink(model.createBy.id + ""), img_avatar);
            tv_username.setText(model.createBy.username);
            ImageLoader.getInstance().displayImage(model.thumb, img_content);

            /**
             * like, view and share
             */

            tv_like_count.setText(model.like_count+"");
            tv_view_count.setText(model.view_count+"");
            rating_bar.setRating(model.rating_average);

            tv_like_count.setClickable(false);
            rating_bar.setIsIndicator(true);

        }
    }
}
