package com.hcmut.social.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.hcmut.social.R;
import com.hcmut.social.controller.controllerdata.RequestData;
import com.hcmut.social.controller.controllerdata.ResponseData;

/**
 * Created by John on 10/6/2016.
 */

public class FavoriteFragment extends MainBaseFragment{

    public static FavoriteFragment newInstance() {
        Bundle args = new Bundle();
        FavoriteFragment fragment = new FavoriteFragment();
        fragment.setArguments(args);
        return fragment;
    }

//    GridView mLocationDataGridView;

    @Override
    protected int[] getListEventHandle() {
        return new int[0];
    }

    @Override
    protected int getLayoutViewResId() {
        return R.layout.fragment_favorite;
    }

    @Override
    protected void initView(View rootView, LayoutInflater inflater) {
//        mLocationDataGridView = (GridView) rootView.findViewById(R.id.content_grid);
//
//        mLocationDataGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View v,
//                                    int position, long id) {
//                Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();
//            }
//        });
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
