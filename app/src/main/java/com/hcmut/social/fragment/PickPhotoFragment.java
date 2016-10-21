package com.hcmut.social.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.hcmut.social.R;
import com.hcmut.social.controller.controllerdata.RequestData;
import com.hcmut.social.controller.controllerdata.ResponseData;

/**
 * Created by John on 10/18/2016.
 */

public class PickPhotoFragment extends BaseFragment {

    public static PickPhotoFragment newInstance() {
        Bundle args = new Bundle();
        PickPhotoFragment fragment = new PickPhotoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int[] getListEventHandle() {
        return new int[0];
    }

    @Override
    protected int getLayoutViewResId() {
        return R.layout.fragment_pick_photo;
    }

    @Override
    protected void initView(View rootView, LayoutInflater inflater) {

    }

    @Override
    protected void setDataToView(View rootView) {

    }

    @Override
    public void onLoadSuccessful(RequestData requestData, ResponseData responseData) {

    }

    @Override
    public void onLoadFail(RequestData requestData, ResponseData responseData) {

    }
}
