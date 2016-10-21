package com.hcmut.social.fragment;

import android.os.Bundle;

import com.hcmut.social.activity.MainActivity;

/**
 * Created by John on 10/7/2016.
 */

public abstract class MainBaseFragment extends BaseFragment {
    protected MainActivity mAct;
    public static final int LIMIT_ITEM = 30;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAct = (MainActivity) getActivity();
    }
}
