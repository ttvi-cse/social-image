package com.hcmut.social.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hcmut.social.LoginManager;
import com.hcmut.social.R;
import com.hcmut.social.activity.PostActivity;
import com.hcmut.social.activity.PostDetailActivity;
import com.hcmut.social.adapter.PostAdapter;
import com.hcmut.social.adapter.PostImageAdapter;
import com.hcmut.social.controller.controllerdata.ListPostRequestData;
import com.hcmut.social.controller.controllerdata.RequestData;
import com.hcmut.social.controller.controllerdata.ResponseData;
import com.hcmut.social.datacenter.DataCenter;
import com.hcmut.social.model.PostModel;
import com.hcmut.social.utils.DialogUtil;

import java.util.List;

/**
 * Created by John on 10/6/2016.
 */

public class MyPageFragment extends MainBaseFragment {

    private static final int REQUEST_CODE_PICK_FILE = 101;
    private static final int REQUEST_CODE_IMAGE_CAPTURE = 102;

    ProgressBar mProgressBar;

    ImageView mAvatarImageView;
    TextView mUsernameTextView;
    TextView mNumPostTextView;
    Button mPostButton;
    ImageView mViewListButon;
    ImageView mViewGridButton;
    ImageView mViewProfileButton;

    SwipeRefreshLayout mSwipeRefreshLayout;
    ListView mContentListView;
    GridView mContentGridView;

    private List<PostModel> mPostData;
    private PostAdapter mPostAdapter;
    private PostImageAdapter mPostImageAdapter;
    private boolean isLoading = false;

    public static MyPageFragment newInstance() {
        Bundle args = new Bundle();
        MyPageFragment fragment = new MyPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int[] getListEventHandle() {
        return new int[] {RequestData.TYPE_LIST_POSTS, RequestData.TYPE_CREATE_POST};
    }

    @Override
    protected int getLayoutViewResId() {
        return R.layout.fragment_my_page;
    }

    @Override
    protected void initView(View rootView, LayoutInflater inflater) {

        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);

        mAvatarImageView = (ImageView) rootView.findViewById(R.id.avatar_imageview);
        mUsernameTextView = (TextView) rootView.findViewById(R.id.name_text);
        mNumPostTextView = (TextView) rootView.findViewById(R.id.num_posts_text);

        mViewListButon = (ImageView) rootView.findViewById(R.id.view_list_button);
        mViewListButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContentListView.setVisibility(View.VISIBLE);
                mContentGridView.setVisibility(View.GONE);
            }
        });

        mViewGridButton = (ImageView) rootView.findViewById(R.id.view_grid_button);
        mViewGridButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContentListView.setVisibility(View.GONE);
                mContentGridView.setVisibility(View.VISIBLE);
            }
        });

        mViewProfileButton = (ImageView) rootView.findViewById(R.id.view_profile_button);
        mViewProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mPostButton = (Button) rootView.findViewById(R.id.post_button);
        mPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagePickerDialog();
            }
        });

        mContentListView = (ListView) rootView.findViewById(R.id.content_listview);
        mContentGridView = (GridView) rootView.findViewById(R.id.content_gridview);

        mContentListView.setVisibility(View.VISIBLE);
        mContentGridView.setVisibility(View.GONE);

        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadRefreshData();
            }
        });
    }

    private void showImagePickerDialog() {
        DialogUtil.showChooseDialog(mAct, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == DialogInterface.BUTTON_POSITIVE) {
                            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            if (takePictureIntent.resolveActivity(mAct.getPackageManager()) != null) {
                                startActivityForResult(takePictureIntent, REQUEST_CODE_IMAGE_CAPTURE);
                            }
                        } else if (which == DialogInterface.BUTTON_NEGATIVE) {
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(intent, REQUEST_CODE_PICK_FILE);
                        }
                    }
                },
                new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                    }
                },
                getString(R.string.choise_file),
                getString(R.string.pick_a_file),
                getString(R.string.take_a_picture)
        );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICK_FILE && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
//            String path = FileUtils.getPath(getActivity(), uri);
            Intent i = new Intent(mAct, PostActivity.class);
            i.putExtra(PostActivity.EXTRA_IMAGE_PATH, uri.toString());
            startActivity(i);
        } else if (requestCode == REQUEST_CODE_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
//            String path = FileUtils.getPath(getActivity(), uri);
            Intent i = new Intent(mAct, PostActivity.class);
            i.putExtra(PostActivity.EXTRA_IMAGE_PATH, uri.toString());
            startActivity(i);
        }
    }

    @Override
    protected void setDataToView(View rootView) {
        if (mPostData == null) {
            loadFirstData();
        } else {
            if (mPostAdapter == null) {
                mPostAdapter = new PostAdapter(mAct);
                mPostAdapter.setData(mPostData);
//                mPostAdapter.setLi
            }
            if (mPostImageAdapter == null) {
                mPostImageAdapter = new PostImageAdapter(mAct);
                mPostImageAdapter.setData(mPostData);
                mPostImageAdapter.setListener(onImageItemClickListener);
            }



            mContentListView.setAdapter(mPostAdapter);
            mContentGridView.setAdapter(mPostImageAdapter);
            mProgressBar.setVisibility(View.GONE);
        }

    }

    PostImageAdapter.OnImageItemClickListener onImageItemClickListener = new PostImageAdapter.OnImageItemClickListener() {
        @Override
        public void onItemClick(PostModel post) {
            Intent i = new Intent(mAct, PostDetailActivity.class);
            i.putExtra(PostDetailActivity.EXTRA_POST_ID, String.valueOf(post.id));
            startActivity(i);
        }
    };

    private void loadFirstData() {
        mProgressBar.setVisibility(View.VISIBLE);
        loadData();
    }

    private void loadData() {
        if(isLoading) return;
        isLoading = true;

        ListPostRequestData requestData = new ListPostRequestData(
                LoginManager.getInstance().getUserId()
        );
        DataCenter.getInstance().doRequest(requestData);
    }

    private void loadRefreshData() {
        loadData();
    }

    private void newPostAdapter() {
        if (mPostAdapter == null) {
            mPostAdapter = new PostAdapter(mAct);
            mContentListView.setAdapter(mPostAdapter);
//            mPostAdapter.setListener()
        }
    }

    private void newPostImageAdapter() {
        if (mPostImageAdapter == null) {
            mPostImageAdapter = new PostImageAdapter(mAct);
            mContentGridView.setAdapter(mPostImageAdapter);
            mPostImageAdapter.setListener(onImageItemClickListener);
        }
    }


    @Override
    public void onLoadSuccessful(RequestData requestData, ResponseData responseData) {

        isLoading = false;
        mProgressBar.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(false);

        if(responseData.getError() != null) {
            onLoadFail(requestData, responseData);
            return;
        }

        if (requestData.getType() == RequestData.TYPE_LIST_POSTS) {
            ResponseData<List<PostModel>> resData = responseData;

            newPostAdapter();
            newPostImageAdapter();

            mPostData = resData.getData();
            mPostAdapter.setData(mPostData);
            mPostImageAdapter.setData(mPostData);
        } else if (requestData.getType() == RequestData.TYPE_CREATE_POST) {
            loadRefreshData();
        }
    }

    @Override
    public void onLoadFail(RequestData requestData, ResponseData responseData) {

        isLoading = false;
        mProgressBar.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(false);

        if (requestData.getType() == RequestData.TYPE_LIST_POSTS) {
//            finish();
        } else if (requestData.getType() == RequestData.TYPE_CREATE_POST) {
//            loadRefreshData();
        }
    }
}
