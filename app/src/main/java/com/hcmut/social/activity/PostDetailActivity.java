package com.hcmut.social.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.facebook.stetho.inspector.protocol.module.Network;
import com.hcmut.social.R;
import com.hcmut.social.adapter.CommentAdapter;
import com.hcmut.social.controller.controllerdata.GetPostDetailRequestData;
import com.hcmut.social.controller.controllerdata.ListCommentRequestData;
import com.hcmut.social.controller.controllerdata.RequestData;
import com.hcmut.social.controller.controllerdata.ResponseData;
import com.hcmut.social.datacenter.DataCenter;
import com.hcmut.social.model.CommentModel;
import com.hcmut.social.model.PostModel;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by John on 10/19/2016.
 */

public class PostDetailActivity extends BaseActivity {

    public static final String EXTRA_POST_ID = "e_postid";

    ProgressBar mProgressBar;

    ImageButton mBackButton;
    ImageView mAvatarImageView;
    TextView mUsernameTextView;
    ImageView mContentImageView;
    TextView mLikeCountTextView;
    TextView mViewCountTextView;
    RatingBar mRatingBar;

    ListView mCommentListView;

//    SwipeRefreshLayout mSwipeRefreshLayout;
    private List<CommentModel> mCommentData;
    private CommentAdapter mCommentAdapter;
    private PostModel mPostData;
    private String mPostID;
    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        mBackButton = (ImageButton) findViewById(R.id.back_button);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mAvatarImageView = (ImageView) findViewById(R.id.avatar_imageview);
        mUsernameTextView = (TextView) findViewById(R.id.username_text);

        mContentImageView = (ImageView) findViewById(R.id.content_imageview);


        mLikeCountTextView = (TextView) findViewById(R.id.like_count_text);
        mViewCountTextView = (TextView) findViewById(R.id.view_count_text);
        mRatingBar = (RatingBar) findViewById(R.id.rating_bar);

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mCommentListView = (ListView) findViewById(R.id.comment_listview);

        if (getIntent().getStringExtra(EXTRA_POST_ID) != null)  {
            mPostID = getIntent().getStringExtra(EXTRA_POST_ID);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        loadData();
    }

    private void loadData() {
        if (mPostID == null) {
            return;
        }

        if (mPostData == null) {
            GetPostDetailRequestData gpRequest = new GetPostDetailRequestData(mPostID);
            DataCenter.getInstance().doRequest(gpRequest);
        }

        if (mCommentData == null) {
            ListCommentRequestData lcRequest = new ListCommentRequestData(mPostID);
            DataCenter.getInstance().doRequest(lcRequest);
        }

    }

    private void newComentAdapter() {
        if (mCommentAdapter == null) {
            mCommentAdapter = new CommentAdapter(PostDetailActivity.this);
            mCommentListView.setAdapter(mCommentAdapter);
        }
    }

    @Override
    protected int[] getListEventHandle() {
        return new int[] {
                RequestData.TYPE_GET_POST_DETAIL,
                RequestData.TYPE_LIST_COMMENTS,
                RequestData.TYPE_CREATE_COMMENT,
                RequestData.TYPE_LIKE_POST,
                RequestData.TYPE_RATE_POST
        };
    }

    @Override
    public void onLoadSuccessful(RequestData requestData, ResponseData responseData) {
        isLoading = false;
        mProgressBar.setVisibility(View.GONE);
//        mSwipeRefreshLayout.setRefreshing(false);

        if(responseData.getError() != null) {
            onLoadFail(requestData, responseData);
            return;
        }

        if (requestData.getType() == RequestData.TYPE_GET_POST_DETAIL) {
            ResponseData<PostModel> resData = responseData;

            PostModel model = resData.getData();

//            ImageLoader.getInstance().displayImage(mAvatarImageView);
//            mUsernameTextView.setText();
            ImageLoader.getInstance().displayImage(model.img_url, mContentImageView);
            mLikeCountTextView.setText(model.like_count+"");
            mViewCountTextView.setText(model.view_count+"");
            mRatingBar.setRating(model.rate_count);

        } else if (requestData.getType() == RequestData.TYPE_LIST_COMMENTS) {
            ResponseData<List<CommentModel>> resData = responseData;

            List<CommentModel> models = resData.getData();

            newComentAdapter();

            mCommentData = models;
            mCommentAdapter.setData(mCommentData);

        } else if (requestData.getType() == RequestData.TYPE_CREATE_COMMENT) {

        } else if (requestData.getType() == RequestData.TYPE_LIKE_POST) {

        } else if (requestData.getType() == RequestData.TYPE_RATE_POST) {

        }
    }

    @Override
    public void onLoadFail(RequestData requestData, ResponseData responseData) {
        isLoading = false;
        mProgressBar.setVisibility(View.GONE);
//        mSwipeRefreshLayout.setRefreshing(false);

    }
}
