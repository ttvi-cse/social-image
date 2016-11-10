package com.hcmut.social.fragment;

import android.content.Intent;
import android.os.Bundle;
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
import com.hcmut.social.adapter.PostImageAdapter;
import com.hcmut.social.controller.controllerdata.ListPostRequestData;
import com.hcmut.social.controller.controllerdata.RequestData;
import com.hcmut.social.controller.controllerdata.ResponseData;
import com.hcmut.social.datacenter.DataCenter;
import com.hcmut.social.model.PostModel;

import java.util.List;

/**
 * Created by John on 10/6/2016.
 */

public class HomePageFragment extends MainBaseFragment {

    ProgressBar mProgressBar;
    GridView mContentGridView;
    ListView mLocationListView;

    private PostImageAdapter mPostImageAdapter;
    private List<PostModel> mPostData;

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
        return new int[] {RequestData.TYPE_LIST_POSTS};
    }

    @Override
    protected int getLayoutViewResId() {
        return R.layout.fragment_home_page;
    }

    @Override
    protected void initView(View rootView, LayoutInflater inflater) {
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);

        mContentGridView = (GridView) rootView.findViewById(R.id.content_grid);
        mContentGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();
            }
        });

        mLocationListView = (ListView) rootView.findViewById(R.id.location_list);
        mLocationListView.setAdapter(new LocationAdapter(getActivity()));

        mLocationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                ((MainActivity)getActivity()).showFragment(new FavoriteFragment(), 3);
            }
        });
    }

    @Override
    protected void setDataToView(View rootView) {
        if (mPostData == null) {
            loadFirstData();
        } else {
            if (mPostImageAdapter == null) {
                mPostImageAdapter = new PostImageAdapter(mAct);
                mPostImageAdapter.setData(mPostData);
                mPostImageAdapter.setListener(onImageItemClickListener);
            }

            mContentGridView.setAdapter(mPostImageAdapter);
            mProgressBar.setVisibility(View.GONE);
        }
    }

    PostImageAdapter.OnImageItemClickListener onImageItemClickListener = new PostImageAdapter.OnImageItemClickListener() {
        @Override
        public void onItemClick(PostModel post) {
            Intent i = new Intent(mAct, PostDetailActivity.class);
            i.putExtra(PostDetailActivity.EXTRA_POST_ID, post.id);
            startActivity(i);
        }
    };

    private void loadFirstData() {
        page = 1;
        loadData(page);
    }

    private void loadData(int lPage) {

        if(isLoading) return;

        isLoading = true;

        ListPostRequestData requestData = new ListPostRequestData(LoginManager.getInstance().getUserId());
        DataCenter.getInstance().doRequest(requestData);
    }


    private void newPostImageAdapter() {
        if(mPostImageAdapter == null) {
            mPostImageAdapter = new PostImageAdapter(mAct);// getArticleType() == ArticleModel.TYPE_LESSON);
            mContentGridView.setAdapter(mPostImageAdapter);
            mPostImageAdapter.setListener(onImageItemClickListener);
        }
    }

    @Override
    public void onLoadSuccessful(RequestData requestData, ResponseData responseData) {

        isLoading = false;
        mProgressBar.setVisibility(View.GONE);

        if(responseData.getError() != null) {
            onLoadFail(requestData, responseData);
            return;
        }

        if (requestData.getType() == RequestData.TYPE_LIST_POSTS) {
            ResponseData<List<PostModel>> resData = responseData;

            newPostImageAdapter();

            mPostData = resData.getData();
            mPostImageAdapter.setData(mPostData);
        }
    }

    @Override
    public void onLoadFail(RequestData requestData, ResponseData responseData) {

        isLoading = false;
        mProgressBar.setVisibility(View.GONE);

    }
}
