package com.hcmut.social.adapter;

import android.content.Context;
import android.widget.BaseAdapter;

/**
 * Created by John on 10/6/2016.
 */

public abstract class BaseSocialAdapter extends BaseAdapter {

    Context mContext;

    public BaseSocialAdapter(Context mContext) {
        this.mContext = mContext;
    }
}
