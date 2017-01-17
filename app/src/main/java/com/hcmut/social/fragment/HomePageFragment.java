package com.hcmut.social.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hcmut.social.LoginManager;
import com.hcmut.social.R;
import com.hcmut.social.activity.PostDetailActivity;
import com.hcmut.social.adapter.LocationAdapter;
import com.hcmut.social.adapter.PostAdapter;
import com.hcmut.social.adapter.PostImageAdapter;
import com.hcmut.social.controller.controllerdata.ListAllPostRequestData;
import com.hcmut.social.controller.controllerdata.ListLocationRequestData;
import com.hcmut.social.controller.controllerdata.ListPostRequestData;
import com.hcmut.social.controller.controllerdata.RequestData;
import com.hcmut.social.controller.controllerdata.ResponseData;
import com.hcmut.social.datacenter.DataCenter;
import com.hcmut.social.model.LocationModel;
import com.hcmut.social.model.PostModel;
import com.hcmut.social.utils.DialogUtil;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by John on 10/6/2016.
 */

public class HomePageFragment extends MainBaseFragment {


    ProgressBar mProgressBar;

    SwipeRefreshLayout mSwipeRefreshLayout;
    ListView mContentListView;
    private List<PostModel> mPostData;
    private PostAdapter mPostAdapter;

    private int page = 1;
    private int mSelectedPos = -1;
    private boolean hasMore = false;
    private boolean isLoading = false;
    private String sort;
    private String order;
    private String tag;

    public static HomePageFragment newInstance() {
        Bundle args = new Bundle();
        HomePageFragment fragment = new HomePageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int[] getListEventHandle() {
        return new int[] {RequestData.TYPE_lIST_ALL_POST};
    }

    @Override
    protected int getLayoutViewResId() {
        return R.layout.fragment_home_page;
    }

    @Override
    protected void initView(View rootView, LayoutInflater inflater) {
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);
        mContentListView = (ListView) rootView.findViewById(R.id.content_listview);
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadRefreshData();
            }
        });
    }

    @Override
    protected void setDataToView(View rootView) {
        if (mPostData == null) {
            loadFirstData();
        } else {
            if (mPostAdapter == null) {
                mPostAdapter = new PostAdapter(mAct);
                mPostAdapter.setData(mPostData);
                mPostAdapter.setListener(onPostItemClickListener);
            }

            mContentListView.setAdapter(mPostAdapter);
            mProgressBar.setVisibility(View.GONE);
        }
    }

    private void loadLocationData() {
        ListLocationRequestData requestData = new ListLocationRequestData();
        DataCenter.getInstance().doRequest(requestData);
    }

    PostImageAdapter.OnImageItemClickListener onImageItemClickListener = new PostImageAdapter.OnImageItemClickListener() {
        @Override
        public void onItemClick(PostModel post) {
            Intent i = new Intent(mAct, PostDetailActivity.class);
            i.putExtra(PostDetailActivity.EXTRA_POST_ID, post.id);
            startActivity(i);
        }
    };

    LocationAdapter.OnLocationClickListener onLoationClickListener = new LocationAdapter.OnLocationClickListener() {
        @Override
        public void onLocationClick(LocationModel location) {
            EventBus.getDefault().post(location);
        }
    };

    private void loadFirstData() {
        mProgressBar.setVisibility(View.VISIBLE);
        loadData();
    }

    private void loadData() {
        if(isLoading) return;
        isLoading = true;

        ListAllPostRequestData requestData = new ListAllPostRequestData(LoginManager.getInstance().getUserId());
        DataCenter.getInstance().doRequest(requestData);
    }

    private void loadRefreshData() {
        loadData();
    }

    private void newPostAdapter() {
        if (mPostAdapter == null) {
            mPostAdapter = new PostAdapter(mAct);
            mContentListView.setAdapter(mPostAdapter);
            mPostAdapter.setListener(onPostItemClickListener);
        }
    }

    PostAdapter.OnPostItemClickListener onPostItemClickListener = new PostAdapter.OnPostItemClickListener() {
        @Override
        public void onItemClick(PostModel post) {
            Intent i = new Intent(mAct, PostDetailActivity.class);
            i.putExtra(PostDetailActivity.EXTRA_POST_ID, post.id);
            startActivity(i);
        }
    };

    @Override
    public void onLoadSuccessful(RequestData requestData, ResponseData responseData) {

        isLoading = false;
        mProgressBar.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(false);

        if(responseData.getError() != null) {
            onLoadFail(requestData, responseData);
            return;
        }

        if (requestData.getType() == RequestData.TYPE_lIST_ALL_POST) {
            ResponseData<List<PostModel>> resData = responseData;

            newPostAdapter();

            mPostData = resData.getData();
            mPostAdapter.setData(mPostData);
        }
    }

    @Override
    public void onLoadFail(RequestData requestData, ResponseData responseData) {

        isLoading = false;
        mProgressBar.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(false);

    }
}
