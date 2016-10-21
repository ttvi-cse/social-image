package com.hcmut.social.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hcmut.social.activity.BaseActivity;
import com.hcmut.social.controller.controllerdata.RequestData;
import com.hcmut.social.controller.controllerdata.ResponseData;
import com.hcmut.social.datacenter.DataCallback;
import com.hcmut.social.datacenter.DataCenter;

import de.greenrobot.event.EventBus;

/**
 * Created by John on 10/6/2016.
 */

public abstract class BaseFragment extends Fragment implements DataCallback {

    private int[] mEventHandleList;
    protected View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addEventHandle();
    }

    protected boolean allowTrackingFromParent() {
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(getLayoutViewResId(), container, false);

        initView(rootView, inflater);
        setDataToView(rootView);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        hideSoftKeyboard();
    }

    @Override
    public void onDestroyView() {
        rootView = null;
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {

        if(EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);

        if(mEventHandleList != null) {
            DataCenter dc = DataCenter.getInstance();

            for (Integer type : mEventHandleList)
                dc.removeCallback(type, this);
        }
        super.onDestroy();
    }

    private void addEventHandle() {
        mEventHandleList = getListEventHandle();

        if(mEventHandleList != null) {
            DataCenter dc = DataCenter.getInstance();

            for (Integer type : mEventHandleList)
                dc.addCallback(type, this);
        }

    }

    protected abstract int[] getListEventHandle();
    protected abstract int getLayoutViewResId();
    protected abstract void initView(View rootView, LayoutInflater inflater);
    protected abstract void setDataToView(View rootView);

    /**
     * Thong Nguyen 19/03/2015
     *
     */
    public void showProgressDialog(){
        if(getActivity() instanceof BaseActivity){
            BaseActivity baseActivity= (BaseActivity) getActivity();
            baseActivity.showProgressDialog();
        }
    }

    /**
     * Thong Nguyen 20/3/2015
     */
    public void hideProgressDialog(){
        if(getActivity() instanceof BaseActivity){
            BaseActivity baseActivity= (BaseActivity) getActivity();
            baseActivity.hideProgressDialog();
        }
    }

    public void showErrorMessage(String error){
        if(getActivity() instanceof BaseActivity){
            BaseActivity baseActivity= (BaseActivity) getActivity();
            baseActivity.showErrorMessage(error);
        }
    }

    /**
     * Hides the soft keyboard
     */
    public void hideSoftKeyboard() {
        if(getActivity() instanceof BaseActivity){
            BaseActivity baseActivity= (BaseActivity) getActivity();
            baseActivity.hideSoftKeyboard();
        }
    }
}
