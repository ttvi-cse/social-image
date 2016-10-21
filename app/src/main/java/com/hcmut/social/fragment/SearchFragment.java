package com.hcmut.social.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.hcmut.social.R;
import com.hcmut.social.controller.controllerdata.RequestData;
import com.hcmut.social.controller.controllerdata.ResponseData;

/**
 * Created by John on 10/6/2016.
 */

public class SearchFragment extends MainBaseFragment{

    public static SearchFragment newInstance() {
        Bundle args = new Bundle();
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int[] getListEventHandle() {
        return new int[0];
    }

    @Override
    protected int getLayoutViewResId() {
        return R.layout.fragment_search;
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
