package com.hcmut.social.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.hcmut.social.R;
import com.hcmut.social.activity.PostDetailActivity;
import com.hcmut.social.adapter.LocationAdapter;
import com.hcmut.social.adapter.PostImageAdapter;
import com.hcmut.social.controller.controllerdata.ListLocationRequestData;
import com.hcmut.social.controller.controllerdata.ListPostLocationRequestData;
import com.hcmut.social.controller.controllerdata.RequestData;
import com.hcmut.social.controller.controllerdata.ResponseData;
import com.hcmut.social.datacenter.DataCenter;
import com.hcmut.social.model.LocationModel;
import com.hcmut.social.model.PostModel;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by John on 10/6/2016.
 */

public class SearchFragment extends MainBaseFragment{

    SearchView mSearchView;
    GridView mLocationDataGridView;
    ListView mLocationListView;
    TextView mlocationTextView;

    private PostImageAdapter mPostImageAdapter;
    private List<PostModel> mPostData;

    private LocationAdapter mLocationAdapter;
    private List<LocationModel> mLocationData;

    private boolean isLoading = false;

    public static SearchFragment newInstance() {
        Bundle args = new Bundle();
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            EventBus.getDefault().register(this);
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            EventBus.getDefault().unregister(this);
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void onEvent(LocationModel location) {
        loadPostLocationData(location);
    }

    @Override
    protected int[] getListEventHandle() {
        return new int[] {RequestData.TYPE_lIST_POST_LOCATION, RequestData.TYPE_LIST_LOCATION};
    }

    @Override
    protected int getLayoutViewResId() {
        return R.layout.fragment_search;
    }

    @Override
    protected void initView(View rootView, LayoutInflater inflater) {

        mlocationTextView = (TextView) rootView.findViewById(R.id.tv_location);
        mlocationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mlocationTextView.setVisibility(View.GONE);
                mSearchView.setVisibility(View.VISIBLE);
                mLocationListView.setVisibility(View.VISIBLE);
                mLocationDataGridView.setVisibility(View.GONE);
            }
        });
        mSearchView = (SearchView) rootView.findViewById(R.id.searchview);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (mLocationAdapter == null)
                    return false;

                mLocationAdapter.getFilter().filter(s);
                return true;
            }
        });

        mLocationListView = (ListView) rootView.findViewById(R.id.lv_locations);

        mLocationDataGridView = (GridView) rootView.findViewById(R.id.content_grid);
        mLocationDataGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void setDataToView(View rootView) {
        if (mPostData == null) {
//            loadFirstData();

        } else {
            if (mPostImageAdapter == null) {
                mPostImageAdapter = new PostImageAdapter(mAct);
                mPostImageAdapter.setData(mPostData);
                mPostImageAdapter.setListener(onImageItemClickListener);
            }

            mLocationDataGridView.setAdapter(mPostImageAdapter);
        }

        if (mLocationData == null) {
            loadLocationData();
        } else {
            if (mLocationAdapter == null) {
                mLocationAdapter = new LocationAdapter(mAct);
                mLocationAdapter.setData(mLocationData);
                mLocationAdapter.setListener(onLoationClickListener);
            }

            mLocationListView.setAdapter(mLocationAdapter);
//            mProgressBar.setVisibility(View.GONE);
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

    private void loadLocationData() {
        ListLocationRequestData requestData = new ListLocationRequestData();
        DataCenter.getInstance().doRequest(requestData);
    }

    private void loadPostLocationData(LocationModel location) {

        if(isLoading) return;

        isLoading = true;

        ListPostLocationRequestData requestData = new ListPostLocationRequestData(location.id);
        DataCenter.getInstance().doRequest(requestData);
    }

    private void newLocationAdapter() {
        if (mLocationAdapter == null) {
            mLocationAdapter = new LocationAdapter(mAct);
            mLocationListView.setAdapter(mLocationAdapter);
            mLocationAdapter.setListener(onLoationClickListener);
        }
    }

    LocationAdapter.OnLocationClickListener onLoationClickListener = new LocationAdapter.OnLocationClickListener() {
        @Override
        public void onLocationClick(LocationModel location) {
            mlocationTextView.setVisibility(View.VISIBLE);
            mSearchView.setVisibility(View.GONE);
            mlocationTextView.setText(location.name);
            loadPostLocationData(location);
        }
    };

    private void newPostImageAdapter() {
        if(mPostImageAdapter == null) {
            mPostImageAdapter = new PostImageAdapter(mAct);// getArticleType() == ArticleModel.TYPE_LESSON);
            mLocationDataGridView.setAdapter(mPostImageAdapter);
            mPostImageAdapter.setListener(onImageItemClickListener);
        }
    }

    @Override
    public void onLoadSuccessful(RequestData requestData, ResponseData responseData) {
        isLoading = false;

        if(responseData.getError() != null) {
            onLoadFail(requestData, responseData);
            return;
        }

        if (requestData.getType() == RequestData.TYPE_lIST_POST_LOCATION) {
            ResponseData<List<PostModel>> resData = responseData;

            newPostImageAdapter();

            mLocationListView.setVisibility(View.GONE);
            mLocationDataGridView.setVisibility(View.VISIBLE);

            mPostData = resData.getData();
            mPostImageAdapter.setData(mPostData);
        } else if (requestData.getType() == RequestData.TYPE_LIST_LOCATION) {
            ResponseData<List<LocationModel>> resData = responseData;

            newLocationAdapter();

            mLocationListView.setVisibility(View.VISIBLE);
            mLocationDataGridView.setVisibility(View.GONE);

            mLocationData = resData.getData();
            mLocationAdapter.setData(mLocationData);

        }
    }

    @Override
    public void onLoadFail(RequestData requestData, ResponseData responseData) {
        isLoading = false;

    }
}
